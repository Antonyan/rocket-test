package core.modules.database.models;

import java.util.Map;

public class DbQueryMetadata {

    private final String tableName;

    private final Map<String, String> tableData;

    public DbQueryMetadata(String tableName, Map<String, String> tableData) {
        this.tableName = tableName;
        this.tableData = tableData;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getTableData() {
        return tableData;
    }
}
