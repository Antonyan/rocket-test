package core.modules.web.models;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomWebElement {

    private WebElement element;
    private WebDriver driver;
    private Web web;

    public CustomWebElement(WebElement element, Web web, WebDriver driver) {
        this.element = element;
        this.web = web;
        this.driver = driver;

    }

    public WebAction click() {
        element.click();
        return new WebAction(driver);
    }

    public WebAction PressEnter() {
        element.sendKeys(Keys.ENTER);
        return new WebAction(driver);
    }

    public Web focusOnAnd() {
        element.sendKeys("");
        return web;
    }

    public Web pressEnterAnd() {
        element.sendKeys(Keys.ENTER);
        return web;
    }

    public Web clickAnd() {
        element.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return web;
    }

    public Web clickMultipleTimesAnd(int clickNumber) {
        for (int i = 0; i < clickNumber; i++) {
            element.click();
        }
        return web;
    }

    public <T extends Enum> WebAction useElementByCss(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new WebAction(driver);
    }

    public WebAction useElementByCss(String css) {
        element = driver.findElement(By.cssSelector(css));
        return new WebAction(driver);
    }

    public <T extends Enum> CustomWebElement useElementByCssAnd(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement useElementByCssAnd(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> WebAction useElementByXpath(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new WebAction(driver);
    }

    public WebAction useElementByXpath(String xpath) {
        element = driver.findElement(By.xpath(xpath));
        return new WebAction(driver);
    }

    public <T extends Enum> CustomWebElement useElementByXpathAnd(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement useElementByXpathAnd(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new CustomWebElement(element, web, driver);
    }

    public WebAction selectOptionContainingText(String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
        return new WebAction(driver);
    }

    public Web selectOptionContainingTextAnd(String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
        return web;
    }

    public WebAction selectOptionByValue(String text) {
        Select select = new Select(element);
        select.selectByValue(text);
        return new WebAction(driver);
    }

    public WebAction selectRadio(String text) {
        element.getCssValue(text);
        return new WebAction(driver);
    }

    public <T extends Enum> CustomWebElement typeTextToInputFieldCss(T css, String value) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        element.sendKeys(value);
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement typeTextToInputFieldCss(String css, String value) {
        WebElement element = driver.findElement(By.cssSelector(css));
        element.sendKeys(value);
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> CustomWebElement typeTextToInputFieldXpath(T xpath, String value) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        element.sendKeys(value);
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement typeTextToInputFieldXpath(String xpath, String value) {
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(value);
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> CustomWebElement waitUntilTextAppearsCss(T css, String text, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(String.valueOf(css))));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement waitUntilTextAppearsCss(String css, String text, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> CustomWebElement waitUntilTextAppearsXpath(T css, String text, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(String.valueOf(css))));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement waitUntilTextAppearsXpath(String xpath, String text, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> CustomWebElement waitUntilAttributeAppearsCss(T css, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(String.valueOf(css))));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement waitUntilAttributeAppearsCss(String css, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = driver.findElement(By.cssSelector(css));
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> CustomWebElement waitUntilAttributeAppearsXpath(T xpath, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.valueOf(xpath))));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement waitUntilAttributeAppearsXpath(String xpath, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = driver.findElement(By.xpath(xpath));
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> CustomWebElement waitUntilElementIsPresentCss(T css, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(String.valueOf(css))));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement waitUntilElementIsPresentCss(String css, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> CustomWebElement waitUntilElementIsPresentXpath(T xpath, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.valueOf(xpath))));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement waitUntilElementIsPresentXpath(String xpath, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> WebAssertion useCssElementForAssertion(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new WebAssertion(element);
    }

    public WebAssertion useCssElementForAssertion(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new WebAssertion(element);
    }

    public <T extends Enum> CustomWebElement useCssElementForAssertionAnd(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new CustomWebElement(element, web, driver);
    }

    public CustomWebElement useCssElementForAssertionAnd(String css) {
        WebElement element = driver.findElement(By.cssSelector(css));
        return new CustomWebElement(element, web, driver);
    }

    public <T extends Enum> WebAssertion useXpathElementForAssertion(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(element);
    }

    public WebAssertion useXpathElementForAssertion(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new WebAssertion(element);
    }

    public <T extends Enum> WebAssertion useXpathElementForAssertionAnd(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(element);
    }

    public CustomWebElement useXpathElementForAssertionAnd(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        return new CustomWebElement(element, web, driver);
    }
}
