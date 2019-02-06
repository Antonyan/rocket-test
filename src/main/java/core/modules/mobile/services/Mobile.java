package core.modules.mobile.services;

import core.modules.mobile.models.MobileAssertion;
import core.modules.mobile.models.TestMobileElement;
import core.modules.mobile.models.TestMobileElements;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Mobile {

    private AppiumDriver<MobileElement> driver;

    public Mobile(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public MobileAssertion useMobileElementsForAssertion(String xpathOfElement) throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<MobileElement> elements = driver.findElementsByXPath(xpathOfElement);

        return new MobileAssertion(elements);
    }

    public TestMobileElement useElement(){
        return new TestMobileElement(driver);
    }

    public TestMobileElements useElements(){
        return new TestMobileElements(driver);
    }

    public Mobile pageElements(){
        System.out.println(driver.getPageSource());
        return this;
    }
}
