package net.deechael.dynamic.quest.api.quest;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface QuestPlayer {

    /**
     * Add quest to player (If player hasn't done this task;
     *
     * @param quest quest to be added
     * @return the player's data of the quest
     */
    QuestData addQuest(Quest quest);

    /**
     * Get the data of the quest
     *
     * @param quest quest
     * @return quest data
     */
    QuestData getQuest(Quest quest);

    /**
     * To check if this player has this quest or if this player has done this quest
     *
     * @param quest quest
     * @return status
     */
    boolean hasQuest(Quest quest);

    /**
     * Remove the player's quest
     *
     * @param quest quest to be removed
     * @return the player's data of the quest
     */
    QuestData removeQuest(Quest quest);

    /**
     * Get player's uuid
     *
     * @return player's uuid
     */
    UUID getUniqueId();

    /**
     * Get bukkit player object
     *
     * @return bukkit player
     */
    Player asBukkit();

}
