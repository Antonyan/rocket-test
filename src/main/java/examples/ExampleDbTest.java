package examples;

import core.service.ApiTest;
import org.testng.annotations.Test;

public class ExampleDbTest extends ApiTest {
    @Test(enabled = true,
            groups = { "Dump:Basic", "Interface:Db" },
            description = "Create user in database")
    public void createUser() {

        String name = random();

        db().haveInTable("users", getDataWithStringValue().add("name", name).getData());
        db().assertion().seeInTable("users", getDataWithStringValue().add("name", name).getData());
    }
}
