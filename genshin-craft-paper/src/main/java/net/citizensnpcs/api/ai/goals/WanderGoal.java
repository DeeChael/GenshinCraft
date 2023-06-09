package net.citizensnpcs.api.ai.goals;

import java.util.Random;
import java.util.function.Function;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;

import com.google.common.base.Supplier;

import ch.ethz.globis.phtree.PhTreeSolid;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.astar.pathfinder.MinecraftBlockExaminer;
import net.citizensnpcs.api.npc.NPC;

public class WanderGoal extends BehaviorGoalAdapter implements Listener {
    private int delay;
    private int delayedTicks;
    private final Function<NPC, Location> fallback;
    private boolean forceFinish;
    private int movingTicks;
    private final NPC npc;
    private boolean pathfind;
    private boolean paused;
    private Location target;
    private final Supplier<PhTreeSolid<Boolean>> tree;
    private int xrange;
    private int yrange;

    private WanderGoal(NPC npc, boolean pathfind, int xrange, int yrange, Supplier<PhTreeSolid<Boolean>> tree,
            Function<NPC, Location> fallback, int delay) {
        this.npc = npc;
        this.pathfind = pathfind;
        this.xrange = xrange;
        this.yrange = yrange;
        this.tree = tree;
        this.fallback = fallback;
        this.delay = delay;
    }

    private Location findRandomPosition() {
        Location found = MinecraftBlockExaminer.findRandomValidLocation(npc.getEntity().getLocation(NPC_LOCATION),
                pathfind ? xrange : 1, pathfind ? yrange : 1, new Function<Block, Boolean>() {
                    @Override
                    public Boolean apply(Block block) {
                        if ((MinecraftBlockExaminer.isLiquidOrInLiquid(block.getRelative(BlockFace.UP))
                                || MinecraftBlockExaminer.isLiquidOrInLiquid(block.getRelative(0, 2, 0)))
                                && npc.getNavigator().getDefaultParameters().avoidWater()) {
                            return false;
                        }
                        if (tree != null) {
                            long[] pt = { block.getX(), block.getY(), block.getZ() };
                            if (tree.get() != null && !tree.get().queryIntersect(pt, pt).hasNext()) {
                                return false;
                            }
                        }
                        return true;
                    }
                }, RANDOM);
        if (found == null && fallback != null) {
            return fallback.apply(npc);
        }
        return found;
    }

    public void pause() {
        this.paused = true;
        if (target != null) {
            if (pathfind) {
                npc.getNavigator().cancelNavigation();
            } else {
                npc.setMoveDestination(null);
            }
        }
    }

    @Override
    public void reset() {
        target = null;
        movingTicks = 0;
        delayedTicks = delay;
        forceFinish = false;
    }

    @Override
    public BehaviorStatus run() {
        if (paused)
            return BehaviorStatus.SUCCESS;
        if (pathfind) {
            if (!npc.getNavigator().isNavigating() || forceFinish) {
                return BehaviorStatus.SUCCESS;
            }
        } else {
            if (npc.getEntity().getLocation(NPC_LOCATION).distance(target) >= 0.1) {
                npc.setMoveDestination(target);
            } else {
                return BehaviorStatus.SUCCESS;
            }
            if (movingTicks-- <= 0) {
                npc.setMoveDestination(null);
                return BehaviorStatus.SUCCESS;
            }
        }
        return BehaviorStatus.RUNNING;
    }

    public void setDelay(int delayTicks) {
        this.delay = delayTicks;
        this.delayedTicks = delayTicks;
    }

    public void setPathfind(boolean pathfind) {
        this.pathfind = pathfind;
    }

    public void setXYRange(int xrange, int yrange) {
        this.xrange = xrange;
        this.yrange = yrange;
    }

    @Override
    public boolean shouldExecute() {
        if (!npc.isSpawned() || npc.getNavigator().isNavigating() || paused)
            return false;
        if (delayedTicks-- > 0) {
            return false;
        }
        Location dest = findRandomPosition();
        if (dest == null)
            return false;
        if (pathfind) {
            npc.getNavigator().setTarget(dest);
            npc.getNavigator().getLocalParameters().addSingleUseCallback((reason) -> forceFinish = true);
        } else {
            Random random = new Random();
            dest.setX(dest.getX() + random.nextDouble(0.5));
            dest.setZ(dest.getZ() + random.nextDouble(0.5));
            movingTicks = 20 + random.nextInt(20);
        }
        this.target = dest;
        return true;
    }

    public void unpause() {
        this.paused = false;
    }

    public static class Builder {
        private int delay = 10;
        private Function<NPC, Location> fallback;
        private final NPC npc;
        private boolean pathfind = true;
        private Supplier<PhTreeSolid<Boolean>> tree;
        private int xrange = 10;
        private int yrange = 2;

        private Builder(NPC npc) {
            this.npc = npc;
            this.tree = null;
            this.fallback = null;
        }

        public WanderGoal build() {
            return new WanderGoal(npc, pathfind, xrange, yrange, tree, fallback, delay);
        }

        public Builder delay(int delay) {
            this.delay = delay;
            return this;
        }

        public Builder fallback(Function<NPC, Location> fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder pathfind(boolean pathfind) {
            this.pathfind = pathfind;
            return this;
        }

        public Builder regionCentres(Supplier<Iterable<Location>> supplier) {
            this.tree = () -> {
                PhTreeSolid<Boolean> tree = PhTreeSolid.create(3);
                for (Location loc : supplier.get()) {
                    long[] lower = { loc.getBlockX() - xrange, loc.getBlockY() - yrange, loc.getBlockZ() - xrange };
                    long[] upper = { loc.getBlockX() + xrange, loc.getBlockY() + yrange, loc.getBlockZ() + xrange };
                    tree.put(lower, upper, true);
                }
                return tree;
            };
            return this;
        }

        public Builder tree(Supplier<PhTreeSolid<Boolean>> supplier) {
            this.tree = supplier;
            return this;
        }

        public Builder xrange(int xrange) {
            this.xrange = xrange;
            return this;
        }

        public Builder yrange(int yrange) {
            this.yrange = yrange;
            return this;
        }
    }

    public static Builder builder(NPC npc) {
        return new Builder(npc);
    }

    private static final Location NPC_LOCATION = new Location(null, 0, 0, 0);
    private static final Random RANDOM = new Random();
}