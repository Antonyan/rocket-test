package examples;

import core.service.ApiTest;
import org.testng.annotations.Test;

public class ExampleApiTest extends ApiTest {

    @Test(enabled = true,
            groups = { "Dump:Basic", "Interface:RestAPI" },
            description = "Check GET https://reqres.in/api/users")
    public void getUsers() {
        rest()
            .sendGET("users")
            .responseCodeIs(200)
            .jsonResponseContainsData(getDataWithStringValue().add("total", "12").getData());
    }

    @Test(enabled = true,
            groups = { "Dump:Basic", "Interface:RestAPI" },
            description = "Check POST https://reqres.in/api/signIn and GET https://reqres.in/api/users")
    public void authAndGet() {
        restWithOAuth()
            .sendGET("users")
            .responseCodeIs(200)
            .jsonResponseContainsData(getDataWithStringValue().add("total", "12").getData());
    }
}
