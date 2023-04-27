package net.deechael.dynamic.quest.quest;

import net.deechael.dynamic.quest.api.quest.Quest;
import net.deechael.dynamic.quest.api.quest.QuestData;
import net.deechael.dynamic.quest.api.quest.QuestPlayer;
import net.deechael.dynamic.quest.api.requirement.Requirement;

public class DynamicQuestData implements QuestData {
    @Override
    public Quest getQuest() {
        return null;
    }

    @Override
    public boolean hasReceived() {
        return false;
    }

    @Override
    public Requirement getRequirement() {
        return null;
    }

    @Override
    public QuestPlayer getPlayer() {
        return null;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

}
