package core.modules.mobile.models;

import core.modules.mobile.services.Mobile;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MobileAction {

    private AppiumDriver<MobileElement> driver;
    private MobileElement element;
    private List<MobileElement> elements;

    MobileAction(AppiumDriver<MobileElement> driver, MobileElement element) {
        this.driver = driver;
        this.element = element;
    }

    MobileAction(AppiumDriver<MobileElement> driver, List<MobileElement> elements) {
        this.driver = driver;
        this.elements = elements;
    }

    public MobileAssertion checkElemets(){
        return new MobileAssertion(elements);
    }

    public Mobile click(){

        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.click();

        return new Mobile(driver);
    }

    public MobileAction clickAnd(){

        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.click();

        return this;
    }

    public MobileAction useMobileElement(String xpath) throws Exception{

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath(xpath)));

        MobileElement element = driver.findElementByXPath(xpath);

        return new MobileAction(driver, element);
    }
}
