package examples;

import core.service.MobileTest;
import org.testng.annotations.Test;

public class ExampleMobileTest extends MobileTest {
    @Test(enabled = true,
            groups = { "Dump:Basic", "Mobile test" },
            description = "Click on power button and check text field")
    public void startRocket() throws Exception{
        getMobile()
                .useElement().withXpath("//XCUIElementTypeButton[@name=\"powerButton\"]")
                .click()
                .useMobileElementsForAssertion("//XCUIElementTypeStaticText")
                .listShouldContains("ROCKET");
    }
}
