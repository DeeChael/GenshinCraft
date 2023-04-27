package net.deechael.dynamic.quest.reward;

import net.deechael.dynamic.quest.api.quest.QuestData;
import net.deechael.dynamic.quest.api.reward.ExperienceRewardBuilder;
import org.bukkit.entity.Player;

public class DynamicExperienceReward extends DynamicReward implements ExperienceRewardBuilder {

    private int xps;
    private int levels;

    @Override
    public ExperienceRewardBuilder xp(int xps) {
        this.xps = xps;
        return this;
    }

    @Override
    public ExperienceRewardBuilder level(int levels) {
        this.levels = levels;
        return this;
    }

    @Override
    public void complete(QuestData questData, Player player) {
        if (questData.isCompleted())
            throw new RuntimeException("The quest is not completed");
        player.giveExp(this.xps);
        player.giveExpLevels(this.levels);
    }

}
