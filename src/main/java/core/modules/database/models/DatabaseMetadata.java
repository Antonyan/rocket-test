package core.modules.database.models;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class DatabaseMetadata {

    private String dbName;

    private JdbcTemplate jdbcTemplate;

    public DatabaseMetadata(String dbName, JdbcTemplate jdbcTemplate) {
        this.dbName = dbName;
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Map<String, Object>> dbTables;

    public List<Map<String, Object>> dbTables() {
        if (dbTables != null){
            return dbTables;
        }

        String sqlGetAllTables = String.format(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=\"%s\";",
                this.dbName
        );

        dbTables = jdbcTemplate.queryForList(sqlGetAllTables);

        return dbTables;
    }
}
