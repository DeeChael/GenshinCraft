package net.deechael.genshincraft.storage;


import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.DataProperty;
import net.deechael.genshincraft.util.storage.annotation.mysql.*;
import net.deechael.genshincraft.util.storage.annotation.share.Comment;
import net.deechael.genshincraft.util.storage.mysql.MysqlType;

import java.util.UUID;

public class PlayerData implements DataHolder {

    @PrimaryKey
    @AutoIncrement
    @Immutable
    @NotNull
    @MysqlObject(type = MysqlType.INT)
    public DataProperty<Integer> uid;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<UUID> uuid;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> registerTime;

    @MysqlObject(type = MysqlType.INT)
    public DataProperty<Integer> adventureExp;

    @MysqlObject(type = MysqlType.INT)
    @Comment("Boolean")
    public DataProperty<Integer> isDroppedWorldLevel;

    @MysqlObject(type = MysqlType.INT)
    @Comment("0000, 0001, 0011, 0111, 1111")
    public DataProperty<Integer> adventureRankDungeons;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> lastOnlineTime;

    @MysqlObject(type = MysqlType.INT)
    @Comment("[0, 160]")
    public DataProperty<Integer> originalResin;

}
