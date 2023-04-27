package net.deechael.dynamic.quest.api.requirement;

import net.deechael.dynamic.quest.api.quest.QuestPlayer;

public interface RequirementData {

    /**
     * Get the requirement of this data
     *
     * @return requirement
     */
    Requirement getRequirement();

    /**
     * Get the owner player of this data
     *
     * @return quest owner
     */
    QuestPlayer getPlayer();

    /**
     * Get if this player has completed this requirement
     *
     * @return status
     */
    boolean isCompleted();

}
