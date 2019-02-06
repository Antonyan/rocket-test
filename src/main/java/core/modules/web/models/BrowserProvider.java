package core.modules.web.models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserProvider {

    public static WebDriver createDriver(String browser, DesiredCapabilities capabilities) {
        return WebBrowser.initialize(browser, capabilities);
    }

    public static WebDriver createDriver(String browser) {
        return createDriver(browser, new DesiredCapabilities());
    }

}
