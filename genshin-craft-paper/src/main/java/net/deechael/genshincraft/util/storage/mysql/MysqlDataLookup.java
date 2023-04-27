package net.deechael.genshincraft.util.storage.mysql;

import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.DataLookup;
import net.deechael.genshincraft.util.storage.DataTable;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MysqlDataLookup<T extends DataHolder> implements DataLookup<T> {

    private final Class<T> clazz;
    private final DataTable<T> table;
    private final List<String> conditions = new ArrayList<>();
    private final Map<String, Map.Entry<String, String>> conditionsKv = new HashMap<>();

    public MysqlDataLookup(Class<T> clazz, DataTable<T> table) {
        this.clazz = clazz;
        this.table = table;
    }

    @Override
    public DataLookup<T> condition(@NotNull String value) {
        this.conditions.add(value);
        return this;
    }

    @Override
    public DataLookup<T> condition(@NotNull String key, @NotNull String operator, @NotNull String value) {
        this.conditionsKv.put(key, new AbstractMap.SimpleEntry<>(operator, value));
        return this;
    }

    @Override
    public @NotNull List<T> lookup() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM `").append(table.name()).append("`");
        if (conditions.size() > 0) {
            sqlBuilder.append(" WHERE (").append(conditions.get(0)).append(")");
            for (int i = 1; i < conditions.size(); i++) {
                sqlBuilder.append(" AND (").append(conditions.get(i)).append(")");
            }
            if (this.conditionsKv.size() > 0)
                sqlBuilder.append(" AND (");
        } else if (this.conditionsKv.size() > 0)
            sqlBuilder.append(" WHERE (");
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, Map.Entry<String, String>> entry : this.conditionsKv.entrySet()) {
            sqlBuilder.append(entry.getKey()).append(entry.getValue().getKey()).append("?").append(") AND (");
            values.add(entry.getValue().getValue());
        }
        if (this.conditionsKv.size() > 0)
            sqlBuilder = new StringBuilder(sqlBuilder.substring(0, sqlBuilder.length() - 6));
        sqlBuilder.append(";");
        try {
            List<T> list = new ArrayList<>();
            PreparedStatement statement = this.table.database().preparedStatement(sqlBuilder.toString());
            int index = 1;
            for (String value : values) {
                statement.setString(index, value);
                index++;
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(MysqlUtils.parseResult(this.clazz, resultSet));
            }
            resultSet.close();
            statement.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull DataTable<T> table() {
        return this.table;
    }

}
