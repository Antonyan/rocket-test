package unit.db;

import core.modules.database.services.LightQueryBuilder;
import junit.framework.TestCase;
import org.testng.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class LightQueryBuilderTest extends TestCase {

    public void testBuildWhereCondition() {
        LightQueryBuilder lightQueryBuilder = new LightQueryBuilder();

        Map<String, String> data1 = new LinkedHashMap<>();
        data1.put("property1", "value1");
        data1.put("property2", "value2");
        data1.put("property3", "value3");

        String expectedWhereStatement1 = " WHERE property1=\"value1\" AND property2=\"value2\" AND property3=\"value3\"";

        Assert.assertEquals(lightQueryBuilder.buildWhereCondition(data1), expectedWhereStatement1);

        Map<String, String> data2 = new LinkedHashMap<>();
        data2.put("property1", "value1");
        data2.put("property2", "value2");
        data2.put("property3", "value3");
        data2.put("property4", null);

        String expectedWhereStatement2 = " WHERE property1=\"value1\" AND property2=\"value2\" AND property3=\"value3\" AND property4 IS NULL";

        Assert.assertEquals(lightQueryBuilder.buildWhereCondition(data2), expectedWhereStatement2);
    }
}
