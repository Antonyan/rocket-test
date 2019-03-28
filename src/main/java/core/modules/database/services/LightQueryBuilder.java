package core.modules.database.services;

import java.util.Map;

public class LightQueryBuilder {

    public String buildWhereCondition (Map<String, String> data) {

        StringBuilder where = new StringBuilder(" WHERE ");

        for (Map.Entry<String, String>  entry : data.entrySet()) {
            where.append(getCondition(entry.getKey(), entry.getValue())).append(" AND ");
        }

        return where.substring(0, where.length() - 5);
    }

    public String buildInsert (String table, Map<String, String> data) {
        return String.format("INSERT INTO %s (%s) values ('%s')", table,
                String.join(", ", data.keySet()), String.join("', '", data.values()));
    }

    public String buildDelete (String table, Map<String, String> data) {
        return "DELETE IGNORE FROM " + table + buildWhereCondition(data);
    }

    public String buildDeleteAllDataFromTable (String table) {
        return "DELETE IGNORE FROM " + table;
    }

    private String getCondition(String fieldName,  String value) {
        if (value == null) {
            return fieldName + " IS NULL";
        }

        return fieldName + "=\"" + value + "\"";
    }
}
