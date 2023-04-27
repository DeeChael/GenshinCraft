package net.deechael.dynamic.quest.data.mysql;

import net.deechael.dynamic.quest.data.base.PlayerStorage;
import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.DataProperty;
import net.deechael.genshincraft.util.storage.annotation.mysql.MysqlObject;
import net.deechael.genshincraft.util.storage.mysql.MysqlType;

import java.util.*;

public class PlayerStorageMysql implements PlayerStorage, DataHolder {

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<UUID> uuid;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> allQuests;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> completedQuests;

    @Override
    public UUID uuid() {
        return this.uuid.get();
    }

    @Override
    public List<String> allQuests() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(this.allQuests.get()).split(",")));
    }

    @Override
    public List<String> completedQuests() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(this.completedQuests.get()).split(",")));
    }

    @Override
    public void uuid(UUID uuid) {
        this.uuid.set(uuid);
    }

    @Override
    public void allQuests(List<String> quests) {
        this.allQuests.set(String.join(",", quests.toArray(new String[0])));
    }

    @Override
    public void completedQuests(List<String> quests) {
        this.completedQuests.set(String.join(",", quests.toArray(new String[0])));
    }

}
