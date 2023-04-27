package net.deechael.genshincraft.util.storage.annotation.mysql;

import net.deechael.genshincraft.util.storage.mysql.MysqlType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field annotated with this annotation will be a column in a mysql table
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MysqlObject {

    /**
     * @return Data type of the column
     */
    MysqlType type();

}
