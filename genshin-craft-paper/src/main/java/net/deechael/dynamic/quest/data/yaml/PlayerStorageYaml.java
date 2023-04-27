package net.deechael.dynamic.quest.data.yaml;

import net.deechael.dynamic.quest.data.base.PlayerStorage;
import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.annotation.yaml.YamlConfig;
import net.deechael.genshincraft.util.storage.annotation.yaml.YamlObject;

import java.util.List;
import java.util.UUID;

@YamlConfig
public class PlayerStorageYaml implements PlayerStorage, DataHolder {

    @YamlObject
    public UUID uuid;

    @YamlObject
    public List<String> allQuests;

    @YamlObject
    public List<String> completedQuests;

    @Override
    public UUID uuid() {
        return this.uuid;
    }

    @Override
    public List<String> allQuests() {
        return this.allQuests;
    }

    @Override
    public List<String> completedQuests() {
        return this.completedQuests;
    }

    @Override
    public void uuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void allQuests(List<String> quests) {
        this.allQuests = quests;
    }

    @Override
    public void completedQuests(List<String> quests) {
        this.completedQuests = quests;
    }

}
