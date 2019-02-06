package core.modules.web.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebAction {

    WebDriver driver;

    //TODO: use autowiring for driver
    public WebAction(WebDriver driver) {
        this.driver = driver;
    }

    public WebAssertion useCssElementForAssertion(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new WebAssertion(element);
    }

    public WebAction useCssElementForAssertionAnd(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return this;
    }

    public WebAssertion useXpathElementForAssertion(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new WebAssertion(element);
    }

    public WebAction useXpathElementForAssertionAnd(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return this;
    }

    public WebAssertion useCssElementForSelectDropdown(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new WebAssertion(element);
    }

    public WebAction useCssElementForSelectDropdownAnd(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return this;
    }


    public WebAssertion useXpathElementForSelectDropdown(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new WebAssertion(element);
    }

    public WebAction useXpathElementForSelectDropdownAnd(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return this;
    }

    public WebAssertion useElementByCss(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new WebAssertion(element);
    }

    public WebAction useElementByCssAnd(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return this;
    }

    public WebAssertion useElementByXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new WebAssertion(element);
    }

    public WebAction useElementByXpathAnd(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return this;
    }
}
