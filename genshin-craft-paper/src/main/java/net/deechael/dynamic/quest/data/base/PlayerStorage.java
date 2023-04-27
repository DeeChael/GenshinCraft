package net.deechael.dynamic.quest.data.base;

import java.util.List;
import java.util.UUID;

public interface PlayerStorage {

    UUID uuid();

    List<String> allQuests();

    List<String> completedQuests();

    void uuid(UUID uuid);

    void allQuests(List<String> quests);

    void completedQuests(List<String> quests);

}
