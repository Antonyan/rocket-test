package core.modules.web.models;

import core.modules.library.models.Config;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    private Integer implicitlyWaitInSeconds;
    private String[] driversList;

    public WebDriverFactory(Config config)
    {
        implicitlyWaitInSeconds = config.getPreference().node("Web").getInt("implicitlyWaitInSeconds", 0);
        driversList = config.getPreference().node("Web").get("webBrowser", "").split(",");
    }

    public ArrayList<WebDriver> webDrivers() {
        ArrayList<org.openqa.selenium.WebDriver> drivers = new ArrayList<>();

        for (String driverName : driversList){
            org.openqa.selenium.WebDriver driver = BrowserProvider.createDriver(driverName);
            driver.manage().timeouts().implicitlyWait(implicitlyWaitInSeconds, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            drivers.add(driver);
        }

        return drivers;
    }

    public Integer driversCount() {
        return driversList.length;
    }
}
