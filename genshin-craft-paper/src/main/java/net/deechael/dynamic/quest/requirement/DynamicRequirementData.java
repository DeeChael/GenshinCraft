package net.deechael.dynamic.quest.requirement;

import net.deechael.dynamic.quest.api.quest.QuestPlayer;
import net.deechael.dynamic.quest.api.requirement.Requirement;
import net.deechael.dynamic.quest.api.requirement.RequirementData;

public class DynamicRequirementData implements RequirementData {

    private final Requirement requirement;
    private final QuestPlayer questPlayer;

    private final boolean completed = false;

    public DynamicRequirementData(Requirement requirement, QuestPlayer questPlayer) {
        this.requirement = requirement;
        this.questPlayer = questPlayer;
    }

    @Override
    public Requirement getRequirement() {
        return this.requirement;
    }

    @Override
    public QuestPlayer getPlayer() {
        return this.questPlayer;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

}
