package core.modules.mobile.models;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestMobileElements {

    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    public TestMobileElements(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }

    public MobileAction withXpath(String xpath) throws Exception{
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return new MobileAction(driver, driver.findElementsByXPath(xpath));
    }
}
