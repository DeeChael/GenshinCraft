package net.deechael.genshincraft.storage;

import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.DataProperty;
import net.deechael.genshincraft.util.storage.annotation.mysql.*;
import net.deechael.genshincraft.util.storage.mysql.MysqlType;
import net.deechael.genshincraft.waypoint.WaypointType;

public class WaypointData implements DataHolder {

    @PrimaryKey
    @AutoIncrement
    @Immutable
    @NotNull
    @MysqlObject(type = MysqlType.INT)
    public DataProperty<Integer> uid;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> waypointId;

    @ReflectJavaEnum(WaypointType.class)
    @MysqlObject(type = MysqlType.ENUM)
    public DataProperty<WaypointType> type;

    @MysqlObject(type = MysqlType.TEXT)
    public DataProperty<String> world;

    @MysqlObject(type = MysqlType.DOUBLE)
    public DataProperty<Double> locX;

    @MysqlObject(type = MysqlType.DOUBLE)
    public DataProperty<Double> locY;

    @MysqlObject(type = MysqlType.DOUBLE)
    public DataProperty<Double> locZ;

    @MysqlObject(type = MysqlType.FLOAT)
    public DataProperty<Double> locYaw;

    @MysqlObject(type = MysqlType.FLOAT)
    public DataProperty<Double> locPitch;

}
