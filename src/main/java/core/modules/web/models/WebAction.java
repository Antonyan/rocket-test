package core.modules.web.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WebAction {

    WebDriver driver;

    //TODO: use autowiring for driver
    public WebAction(WebDriver driver) {
        this.driver = driver;
    }

    //one element css
    public <T extends Enum> WebAssertion useCssElementForAssertion(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new WebAssertion(element);
    }

    public WebAssertion useCssElementForAssertion(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new WebAssertion(element);
    }

    public <T extends Enum> WebAction useCssElementForAssertionAnd(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return this;
    }

    public WebAction useCssElementForAssertionAnd(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return this;
    }

    //multiple elements css
    public <T extends Enum> WebAssertion useCssElementsForAssertion(T css) {
        List<WebElement> elements = driver.findElements(By.cssSelector(String.valueOf(css)));
        return new WebAssertion(elements);
    }

    public WebAssertion useCssElementsForAssertion(String css) {
        List<WebElement> elements = driver.findElement(By.cssSelector(css));
        return new WebAssertion(elements);
    }

    public <T extends Enum> WebAction useCssElementsForAssertionAnd(T css) {
        List<WebElement> elements = driver.findElements(By.cssSelector(String.valueOf(css)));
        return this;
    }

    public WebAction useCssElementsForAssertionAnd(String css) {
        List<WebElement> elements = driver.findElements(By.cssSelector(css));
        return this;
    }

    //one element xpath
    public <T extends Enum> WebAssertion useXpathElementForAssertion(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(element);
    }

    public WebAssertion useXpathElementForAssertion(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new WebAssertion(element);
    }

    public <T extends Enum> WebAction useXpathElementForAssertionAnd(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return this;
    }

    public WebAction useXpathElementForAssertionAnd(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return this;
    }

    //multiple elements xpath
    public <T extends Enum> WebAssertion useXpathElementsForAssertion(T xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(elements);
    }

    public WebAssertion useXpathElementsForAssertion(String xpath) {
        List<WebElement> elements = driver.findElement(By.xpath(xpath));
        return new WebAssertion(elements);
    }

    public <T extends Enum> WebAction useXpathElementsForAssertionAnd(T xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(String.valueOf(xpath)));
        return this;
    }

    public WebAction useXpathElementsForAssertionAnd(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return this;
    }

    public <T extends Enum> WebAssertion useCssElementForSelectDropdown(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new WebAssertion(element);
    }

    public WebAssertion useCssElementForSelectDropdown(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new WebAssertion(element);
    }

    public <T extends Enum> WebAssertion useCssElementsForSelectDropdown(T css) {
        List<WebElement> elements = driver.findElements(By.cssSelector(String.valueOf(css)));
        return new WebAssertion(elements);
    }

    public WebAssertion useCssElementsForSelectDropdown(String css) {
        List<WebElement> elements = driver.findElements(By.cssSelector(css));
        return new WebAssertion(elements);
    }

    public <T extends Enum> WebAction useCssElementForSelectDropdownAnd(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return this;
    }

    public WebAction useCssElementForSelectDropdownAnd(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return this;
    }

    public <T extends Enum> WebAssertion useXpathElementForSelectDropdown(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(element);
    }

    public WebAssertion useXpathElementForSelectDropdown(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new WebAssertion(element);
    }

    public <T extends Enum> WebAssertion useXpathElementsForSelectDropdown(T xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(elements);
    }

    public WebAssertion useXpathElementsForSelectDropdown(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return new WebAssertion(elements);
    }

    public <T extends Enum> WebAction useXpathElementForSelectDropdownAnd(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return this;
    }

    public WebAction useXpathElementForSelectDropdownAnd(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return this;
    }

    public <T extends Enum> WebAssertion useXpathElementsForSelectDropdownAnd(T xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(elements);
    }

    public WebAssertion useXpathElementsForSelectDropdownAnd(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return new WebAssertion(elements);
    }

    //TODO: increase the quantity of methods for different situations

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
