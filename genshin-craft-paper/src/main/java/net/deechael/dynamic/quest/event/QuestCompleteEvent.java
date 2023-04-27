package net.deechael.dynamic.quest.event;

import net.deechael.dynamic.quest.api.quest.Quest;
import net.deechael.dynamic.quest.api.quest.QuestPlayer;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class QuestCompleteEvent extends QuestEvent {

    private final static HandlerList handlerList = new HandlerList();

    public QuestCompleteEvent(QuestPlayer questPlayer, Quest quest) {
        super(questPlayer, quest);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
