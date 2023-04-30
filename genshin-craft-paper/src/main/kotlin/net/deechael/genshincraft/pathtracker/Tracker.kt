package net.deechael.genshincraft.pathtracker

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import xyz.xenondevs.particle.ParticleBuilder
import xyz.xenondevs.particle.ParticleEffect
import java.awt.Color
import kotlin.math.abs

// FIXME: not works
class Tracker(val world: World, val from: Vector, val to: Vector) {
    
    val paths: List<Vector>
    
    init {
        val list = mutableListOf<Vector>()
        var deltaX: Int
        var deltaY: Int
        var deltaZ: Int

        var startX = from.blockX
        var startY = from.blockY
        var startZ = from.blockZ

        var last = 0

        var times = 0

        while (startX != to.blockX && startY != to.blockY && startZ != to.blockZ) {
            if (times >= 200)
                break
            deltaX = if (startX <= to.blockX) {
                1
            } else {
                -1
            }
            deltaY = if (startY <= to.blockY) {
                1
            } else {
                -1
            }
            deltaZ = if (startZ <= to.blockZ) {
                1
            } else {
                -1
            }
            list.add(Vector(startX, startY, startZ))
            if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX + deltaX, startY, startZ))) {
                startX += deltaX
                last = 0
                continue
            } else if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX, startY, startZ + deltaZ))) {
                startZ += deltaZ
                last = 1
                continue
            } else if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX + deltaX, startY + deltaY, startZ))) {
                startX += deltaX
                startY += deltaY
                last = 2
                continue
            } else if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX, startY + deltaY, startZ + deltaZ))) {
                startY += deltaY
                startZ += deltaZ
                last = 2
                continue
            } else if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX - deltaX, startY, startZ)) && last != 0) {
                startX -= deltaX
                last = 0
                continue
            } else if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX, startY, startZ - deltaZ)) && last != 1) {
                startZ -= deltaZ
                last = 1
                continue
            } else if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX - deltaX, startY - deltaY, startZ)) && last != 2) {
                startX -= deltaX
                startY -= deltaY
                last = 2
                continue
            } else if (isWalkable(this.loc(startX, startY, startZ), this.loc(startX, startY - deltaY, startZ - deltaZ)) && last != 2) {
                startY -= deltaY
                startZ -= deltaZ
                last = 2
                continue
            }
            times += 1
        }
        this.paths = list.toList()
    }

    private fun loc(x: Int, y: Int, z: Int): Location {
        return Location(this.world, x.toDouble(), y.toDouble(), z.toDouble())
    }

    private fun isWalkable(from: Location, to: Location): Boolean {
        return if (to.blockY - from.blockY <= 0.1 && to.blockY - from.blockY >= -0.1) {
            to.block.type.isAir &&
                    to.add(0.0, 1.0, 0.0).block.type.isAir
        } else if (to.blockY - from.blockY < 0) {
            to.block.type.isAir &&
                    to.add(0.0, 1.0, 0.0).block.type.isAir &&
                    to.add(0.0, 2.0, 0.0).block.type.isAir
        } else {
            from.add(0.0, 2.0, 0.0).block.type.isAir &&
                    to.block.type.isAir &&
                    to.add(0.0, 1.0, 0.0).block.type.isAir
        }
    }
    
    fun show(player: Player) {
        val baseBuilder = ParticleBuilder(ParticleEffect.DUST_COLOR_TRANSITION)
                .setColor(Color.YELLOW)
                .setSpeed(0.0f)
                .setAmount(10)
        for (path in this.paths) {
            baseBuilder
                .setLocation(path.toLocation(this.world))
                .display(player)
        }
    }
    
}