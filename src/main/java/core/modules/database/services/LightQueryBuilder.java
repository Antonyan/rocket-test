package core.modules.database.services;

import java.util.List;
import java.util.Map;

public class LightQueryBuilder {

    public String buildWhereCondition (Map<String, String> data) {

        String where = " WHERE ";

        for (Map.Entry<String, String>  entry : data.entrySet()) {
            where = where + entry.getKey() + "=\"" + entry.getValue() + "\"" + " AND ";
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
}
