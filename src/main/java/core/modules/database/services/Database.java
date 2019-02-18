package core.modules.database.services;

import core.modules.database.models.DatabaseMetadata;
import core.modules.database.models.DbQueryMetadata;
import core.modules.library.models.Verbose;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class Database {

    private DatabaseAssert dbAssert;

    private JdbcTemplate jdbcTemplate;

    private LightQueryBuilder lightQueryBuilder;

    //TODO: can't be a service any more because of state
    private Stack<DbQueryMetadata> operationStack;

    private final DatabaseMetadata databaseMetadata;

    private Verbose verbose;

    public Database(
            DatabaseMetadata databaseMetadata,
            DatabaseAssert dbAssert,
            JdbcTemplate jdbcTemplate,
            LightQueryBuilder lightQueryBuilder,
            Verbose verbose
    ) {
        this.databaseMetadata = databaseMetadata;
        this.dbAssert = dbAssert;
        this.jdbcTemplate = jdbcTemplate;
        this.lightQueryBuilder = lightQueryBuilder;
        this.verbose = verbose;
        this.operationStack = new Stack<>();
    }

    public Database haveInTable(String table, Map<String, String> data){
        String sql = lightQueryBuilder.buildInsert(table, data);
        jdbcTemplate.update(sql);
        verbose.testInfo("Create precondition in DB", sql);
        operationStack.push(new DbQueryMetadata(table, data));
        return this;
    }

    public Integer findElementId(String table, Map<String, String> data){
        String sql = "SELECT id FROM " + table + lightQueryBuilder.buildWhereCondition(data);
        Integer id;

        try {
            id = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            id = null;
        }

        return id;
    }

    public Integer findElementId(String table, String identifierName, Map<String, String> data){
        String sql = "SELECT " +  identifierName + " FROM " + table + lightQueryBuilder.buildWhereCondition(data);
        Integer id;

        try {
            id = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            id = null;
        }

        return id;
    }

    public Database haveInTable(DbQueryMetadata dbQueryMetadata){
        String sql = lightQueryBuilder.buildInsert(dbQueryMetadata.getTableName(), dbQueryMetadata.getTableData());
        jdbcTemplate.update(sql);
        verbose.testInfo("Create precondition in DB", sql);
        operationStack.push(dbQueryMetadata);
        return this;
    }

    public Database cleanUp(){
        while (!operationStack.empty()) {
            DbQueryMetadata dbQueryMetadata = operationStack.pop();
            String sql = lightQueryBuilder.buildDelete(
                    dbQueryMetadata.getTableName(),
                    dbQueryMetadata.getTableData()
            );

            String testInfo = sql;

            try {
                jdbcTemplate.execute(sql);
            } catch (DataIntegrityViolationException | BadSqlGrammarException e){
                testInfo = e.getMessage();
            }
            verbose.testInfo("Delete precondition in DB", testInfo);
        }

        return this;
    }

    public Database cleanUpAllData(){

        for (Map<String, Object> row : databaseMetadata.dbTables()) {
            jdbcTemplate.execute(String.format("DELETE IGNORE FROM %s;", row.get("TABLE_NAME")));
        }

        verbose.testInfo("Delete precondition in DB", "All data were deleted.");

        return this;
    }

    public DatabaseAssert assertion() {
        return dbAssert;
    }

}
