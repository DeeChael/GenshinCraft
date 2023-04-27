package net.deechael.dynamic.quest.api.quest;

import net.deechael.dynamic.quest.api.requirement.RequirementData;

public interface QuestData extends RequirementData {

    /**
     * Get the quest of this data
     *
     * @return quest
     */
    Quest getQuest();

    /**
     * Get if this player has received the rewards
     *
     * @return status
     */
    boolean hasReceived();

}
