package net.deechael.dynamic.quest.api.reward;

import net.deechael.dynamic.quest.api.quest.QuestData;
import org.bukkit.entity.Player;

public interface Reward {

    /**
     * Call on the quest being completed, don't call it manually
     *
     * @param questData quest data
     * @param player    player
     */
    void complete(QuestData questData, Player player);

}
