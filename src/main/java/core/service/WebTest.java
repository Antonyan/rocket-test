package core.service;


import core.WebContext;
import core.modules.library.models.Config;
import core.modules.web.models.Web;
import core.modules.web.models.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;

@ContextConfiguration(classes = {WebContext.class}, loader = AnnotationConfigContextLoader.class)
public class WebTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private Config webConfig;

    @Autowired
    private ArrayList<WebDriver> webDrivers;

    private Integer driverNumber;
    private String originalHandle;


    @DataProvider(name = "drivers")
    public static Object[][] createData() {
        Integer driversCount = new WebDriverFactory(new Config("config.ini")).driversCount();
        Object[][] object = new Object[driversCount][1];
        for (int i = 0; i < driversCount; i++) {
            object[i][0] = i;
        }

        return object;
    }

    public Web web(Integer driverNumber) {
        this.driverNumber = driverNumber;
        return new Web(webConfig.getPreference().node("Web").get("baseUrl", ""), webDrivers.get(driverNumber));
    }

    public Web web() {
        this.driverNumber = 0;

        return new Web(webConfig.getPreference().node("Web").get("baseUrl", ""), webDrivers.get(0));
    }

    @BeforeMethod
    public void setUp() {
        for (WebDriver webDriver : webDrivers) {
            if (webDriver != null) {
                originalHandle = webDriver.getWindowHandle();
            }
        }
    }

    @AfterMethod
    public void closeBrowserTab() {
        for (WebDriver webDriver : webDrivers) {
            if (webDriver != null) {
                for (String handle : webDriver.getWindowHandles()) {
                    if (!handle.equals(originalHandle)) {
                        webDriver.switchTo().window(handle);
                        webDriver.close();
                    }
                }
            }
            webDriver.switchTo().window(originalHandle);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void closeBrowserInstance() {
        for (WebDriver webDriver : webDrivers) {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }
}
