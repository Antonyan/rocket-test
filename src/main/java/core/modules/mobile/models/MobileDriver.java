package core.modules.mobile.models;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.prefs.Preferences;

public class MobileDriver {

    private Preferences config;
    private AppiumDriver<MobileElement> driver;

    public MobileDriver(Preferences config) {
        this.config = config;
    }

    public AppiumDriver<MobileElement> driver() throws Exception{

        if (driver != null){
            return driver;
        }

        File appDir = new File(config.get("appDir", ""));
        File app = new File(appDir, config.get("app", ""));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", config.get("platformVersion", ""));
        capabilities.setCapability("deviceName", config.get("deviceName", ""));
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("automationName", config.get("automationName", ""));
        driver = new IOSDriver<>(new URL(config.get("appiumUrl", "")), capabilities);

        return driver;
    }

    public void quit(){
        driver.quit();
    }
}
