package core.modules.database.services;

import core.modules.library.models.Verbose;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;

public class DatabaseAssert {

    private JdbcTemplate jdbcTemplate;

    private LightQueryBuilder lightQueryBuilder;

    private Verbose verbose;

    public DatabaseAssert(JdbcTemplate jdbcTemplate, LightQueryBuilder lightQueryBuilder, Verbose verbose) {
        this.jdbcTemplate = jdbcTemplate;
        this.lightQueryBuilder = lightQueryBuilder;
        this.verbose = verbose;
    }

    public DatabaseAssert seeInTable(String table, HashMap data){
        if (!isDataInDatabase(table, data)){
            Assert.fail("Elements are not present in " + table.toUpperCase() + " table");
        }
        return this;
    }

    public DatabaseAssert cantSeeInTable(String table, HashMap data){
        if (isDataInDatabase(table, data)){
            Assert.fail("Elements are present in " + table.toUpperCase() + " table");
        }
        return this;
    }

    public boolean isDataInDatabase(String table, HashMap data) {
        boolean isPresent = false;

        String sql = "SELECT count(*) FROM " + table + lightQueryBuilder.buildWhereCondition(data);
        String count = "0";

        verbose.testInfo("Find condition in table", sql);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            count = row.get("count(*)").toString();
        }

        if(Integer.parseInt(count) > 0)
            isPresent = true;

        return isPresent;
    }
}
