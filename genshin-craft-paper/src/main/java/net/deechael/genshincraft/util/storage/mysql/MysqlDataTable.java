package net.deechael.genshincraft.util.storage.mysql;

import net.deechael.genshincraft.util.storage.*;
import net.deechael.genshincraft.util.storage.annotation.mysql.Immutable;
import net.deechael.genshincraft.util.storage.annotation.mysql.MysqlObject;
import net.deechael.genshincraft.util.storage.annotation.mysql.PrimaryKey;
import net.deechael.genshincraft.util.storage.share.BaseDataProperty;
import net.deechael.genshincraft.util.storage.share.BaseImmutableDataProperty;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlDataTable<T extends DataHolder> implements DataTable<T> {

    private final Database database;
    private final Class<T> clazz;
    private final String tableName;
    private boolean hasPrimaryKey = false;

    public MysqlDataTable(Database database, Class<T> clazz, String tableName) {
        this.database = database;
        this.clazz = clazz;
        this.tableName = tableName;
    }

    @Override
    public T create() {
        try {
            T t = clazz.newInstance();
            for (Field field : this.clazz.getFields()) {
                MysqlObject mySqlObject = field.getAnnotation(MysqlObject.class);
                if (mySqlObject == null)
                    continue;
                if (field.getAnnotation(PrimaryKey.class) != null)
                    this.hasPrimaryKey = true;
                field.set(t, field.getAnnotation(Immutable.class) != null ? new BaseImmutableDataProperty<>(null) : new BaseDataProperty<>());
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T store(@NotNull T t, boolean replace) {
        StringBuilder sqlBuilder = new StringBuilder();
        if (this.hasPrimaryKey && replace) {
            sqlBuilder.append("REPLACE ");
        } else {
            sqlBuilder.append("INSERT ");
        }
        sqlBuilder.append("INTO `").append(this.tableName).append("` (");
        Map<String, String> kv = new HashMap<>();

        for (Field field : this.clazz.getFields()) {
            MysqlObject mySqlObject = field.getAnnotation(MysqlObject.class);
            if (mySqlObject == null)
                continue;
            if (field.getAnnotation(Immutable.class) != null)
                continue;
            try {
                DataProperty<?> dataProperty = (DataProperty<?>) field.get(t);
                if (dataProperty.get() == null)
                    continue;
                String columnName = "K_" + field.getName().toUpperCase();
                kv.put(columnName, dataProperty.get().toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        StringBuilder keysBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, String> key : kv.entrySet()) {
            keysBuilder.append("`").append(key.getKey()).append("`,");
            valuesBuilder.append("?,");
            values.add(key.getValue());
        }
        sqlBuilder.append(keysBuilder.substring(0, keysBuilder.length() - 1)).append(") VALUES (").append(valuesBuilder.substring(0, valuesBuilder.length() - 1)).append(");");
        PreparedStatement preparedStatement = this.database().preparedStatement(sqlBuilder.toString());
        try {
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i));
            }
            preparedStatement.executeUpdate();
            // ResultSet resultSet = preparedStatement.executeQuery();
            // T result = null;
            // if (resultSet.next())
            //     result = MysqlUtils.parseResult(this.clazz, resultSet);
            // resultSet.close();
            preparedStatement.close();
            // return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // FIXME
        DataLookup<T> lookup = this.lookup();
        for (Field field : this.clazz.getFields()) {
            MysqlObject mySqlObject = field.getAnnotation(MysqlObject.class);
            if (mySqlObject == null)
                continue;
            if (field.getAnnotation(Immutable.class) != null)
                continue;
            try {
                DataProperty<?> dataProperty = (DataProperty<?>) field.get(t);
                if (dataProperty.get() == null)
                    continue;
                lookup.condition("K_" + field.getName().toUpperCase(), dataProperty.get().toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        List<T> list = lookup.lookup();
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public DataDelete<T> delete() {
        return new MysqlDataDelete<>(this.clazz, this);
    }

    @Override
    public DataLookup<T> lookup() {
        return new MysqlDataLookup<>(this.clazz, this);
    }

    @Override
    public Database database() {
        return this.database;
    }

    @Override
    public String name() {
        return this.tableName;
    }

}
