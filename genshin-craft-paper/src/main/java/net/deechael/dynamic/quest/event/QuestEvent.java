package net.deechael.dynamic.quest.event;

import net.deechael.dynamic.quest.api.quest.Quest;
import net.deechael.dynamic.quest.api.quest.QuestPlayer;
import org.bukkit.event.Event;

public abstract class QuestEvent extends Event {

    private final QuestPlayer questPlayer;
    private final Quest quest;

    public QuestEvent(QuestPlayer questPlayer, Quest quest) {
        this.questPlayer = questPlayer;
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }

    public QuestPlayer getQuestPlayer() {
        return questPlayer;
    }

}
