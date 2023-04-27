package net.deechael.dynamic.quest.data;

import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.DataProperty;

import java.util.List;

public class QuestStorage implements DataHolder {

    public DataProperty<String> name;

    public DataProperty<Boolean> completed;

    public DataProperty<List<RequirementStorage>> requirements;

}
