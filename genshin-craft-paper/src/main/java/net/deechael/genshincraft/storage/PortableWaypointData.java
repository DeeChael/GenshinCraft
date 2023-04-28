package net.deechael.genshincraft.storage;

import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.DataProperty;
import net.deechael.genshincraft.util.storage.annotation.mysql.*;
import net.deechael.genshincraft.util.storage.mysql.MysqlType;

public class PortableWaypointData implements DataHolder {

    @PrimaryKey
    @AutoIncrement
    @Immutable
    @NotNull
    @MysqlObject(type = MysqlType.INT)
    public DataProperty<Integer> uid;

    @NotNull
    @MysqlObject(type = MysqlType.INT)
    public DataProperty<Integer> ownerId;

    @NotNull
    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> createdAt;

}
