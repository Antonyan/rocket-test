package core.modules.web.models;

import org.openqa.selenium.WebDriver;

public class BrowserProvider {

    public static WebDriver createDriver(String browser) {
        return WebBrowser.initialize(browser);
    }
}
