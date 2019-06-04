package core.modules.web.models;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class Web {

    private WebDriver driver;
    private JavascriptExecutor js;
    private String baseAdminUrl;

    //TODO: use autowiring for driver
    public Web(String baseAdminUrl, WebDriver browserDriver) {
        this.baseAdminUrl = baseAdminUrl;
        this.driver = browserDriver;
    }

    public Web open(String url) {
        driver.get(baseAdminUrl + url);
        return this;
    }

    public Web open() {
        driver.navigate().refresh();
        driver.get(baseAdminUrl);
        return this;
    }

    public Web openAnyUrl(String url) {
        driver.get(url);
        return this;
    }

    //TODO: code duplication. Follow DRY principle
    public <T extends Enum> WebElement findElementByXpath(T xpath) {
        try {
            return driver.findElement(By.xpath(String.valueOf(xpath)));
        } catch (Exception e) {
            Assert.fail("Element with xpath + " + xpath + "not found");
            return null;
        }
    }

    public WebElement findElementByXpath(String xpath) {
        try {
            return driver.findElement(By.xpath(xpath));
        } catch (Exception e) {
            Assert.fail("Element with xpath + " + xpath + "not found");
            return null;
        }
    }

    public <T extends Enum> WebElement findElementByCss(T css) {
        try {
            return driver.findElement(By.cssSelector(String.valueOf(css)));
        } catch (Exception e) {
            Assert.fail("Element with xpath + " + css + "not found");
            return null;
        }
    }

    public WebElement findElementByCss(String css) {
        try {
            return driver.findElement(By.cssSelector(css));
        } catch (Exception e) {
            Assert.fail("Element with css + " + css + "not found");
            return null;
        }
    }

    public CustomWebElement useElementByLinkText(String text) {
        WebElement element = driver.findElement(By.linkText(text));
        return new CustomWebElement(element, this, driver);
    }

    public Web switchToNewTab() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        return this;
    }

    public <T extends Enum> WebAssertion useCssElementForAssertion(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new WebAssertion(element);
    }

    public WebAssertion useCssElementForAssertion(String css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new WebAssertion(element);
    }

    public <T extends Enum> Web useCssElementForAssertionAnd(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return this;
    }

    public Web useCssElementForAssertionAnd(String css) {
        WebElement element = findElementByCss(css);
        return this;
    }

    public <T extends Enum> Web useCssJavascriptInnerTextForAssertionAnd(T cssSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return document.querySelector("+ String.valueOf(cssSelector) +").innerText ");
        return this;
    }

    public Web useCssJavascriptInnerTextForAssertionAnd(String cssSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return document.querySelector("+ cssSelector +").innerText ");
        return this;
    }

   // public <T extends Enum> WebAssertion useCssJavascriptInnerTextForAssertion(T cssSelector) {
   //     JavascriptExecutor js = (JavascriptExecutor) driver;
   //     js.executeScript("return document.querySelector("+ cssSelector +").innerText ");
   //     return new WebAssertion(js);
   // }

    public WebAssertion useCssJavascriptInnerTextForAssertion(String cssSelector) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(cssSelector)));
        return new WebAssertion(element);
    }


    public <T extends Enum> WebAssertion useXpathElementForAssertion(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(element);
    }

    public WebAssertion useXpathElementForAssertion(String xpath) {
        WebElement element = findElementByXpath(xpath);
        return new WebAssertion(element);
    }

    public <T extends Enum> Web useXpathElementForAssertionAnd(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return this;
    }

    public Web useXpathElementForAssertionAnd(String xpath) {
        WebElement element = findElementByXpath(xpath);
        return this;
    }

    public <T extends Enum> WebAssertion useXpathElementsForAssertion(T xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(String.valueOf(xpath)));
        return new WebAssertion(elements);
    }

    public WebAssertion useXpathElementsForAssertion(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return new WebAssertion(elements);
    }

    public <T extends Enum> Web useXpathElementsForAssertionAnd(T xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(String.valueOf(xpath)));
        return this;
    }

    public Web useXpathElementsForAssertionAnd(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(String.valueOf(xpath)));
        return this;
    }

    public <T extends Enum> CustomWebElement useElementByCss(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return new CustomWebElement(element, this, driver);
    }

    public CustomWebElement useElementByCss(String css) {
        WebElement element = findElementByCss(css);
        return new CustomWebElement(element, this, driver);
    }

    public <T extends Enum> Web useElementByCssAnd(T css) {
        WebElement element = driver.findElement(By.cssSelector(String.valueOf(css)));
        return this;
    }

    public Web useElementByCssAnd(String css) {
        WebElement element = findElementByCss(css);
        return this;
    }

    public <T extends Enum> Web useElementsByCssAnd(T css) {
        List<WebElement> elements = driver.findElements(By.cssSelector(String.valueOf(css)));
        return this;
    }

    public Web useElementsByCssAnd(String css) {
        List<WebElement> elements = driver.findElements(By.cssSelector(String.valueOf(css)));
        return this;
    }

    public <T extends Enum> CustomWebElement useElementByXpath(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return new CustomWebElement(element, this, driver);
    }

    public CustomWebElement useElementByXpath(String xpath) {
        WebElement element = findElementByXpath(xpath);
        return new CustomWebElement(element, this, driver);
    }

    public <T extends Enum> Web useElementByXpathAnd(T xpath) {
        WebElement element = driver.findElement(By.xpath(String.valueOf(xpath)));
        return this;
    }

    //TODO: rest of the structure using Enums page object pattern

    public Web useElementByXpathAnd(String xpath) {
        WebElement element = findElementByXpath(xpath);
        return this;
    }

    public CustomWebElement typeTextToInputFieldCss(String css, String value) {
        WebElement element = findElementByCss(css);
        element.sendKeys(value);
        return new CustomWebElement(element, this, driver);
    }

    public Web typeTextToInputFieldCssAnd(String css, String value) {
        WebElement element = findElementByCss(css);
        element.sendKeys(value);
        return this;
    }

    public CustomWebElement typeTextToInputFieldXpath(String xpath, String value) {
        WebElement element = findElementByXpath(xpath);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(value);
        return new CustomWebElement(element, this, driver);
    }

    public Web typeTextToInputFieldXpathAnd(String xpath, String value) {
        WebElement element = findElementByXpath(xpath);
        element.sendKeys(value);
        return this;
    }

    public CustomWebElement pressEnterUsingCss(String css) {
        WebElement element = findElementByCss(css);
        return new CustomWebElement(element, this, driver);
    }

    public Web pressEnterUsingCssAnd(String css) {
        WebElement element = findElementByCss(css);
        return this;
    }

    public CustomWebElement pressEnterUsingXpath(String xpath) {
        WebElement element = findElementByXpath(xpath);
        return new CustomWebElement(element, this, driver);
    }

    public Web pressEnterUsingXpathAnd(String xpath) {
        WebElement element = findElementByXpath(xpath);
        element.sendKeys(Keys.ENTER);
        return this;
    }

    public CustomWebElement useCssElementAndTextForDropdownList(String css) {
        WebElement element = findElementByCss(css);
        return new CustomWebElement(element, this, driver);
    }

    public Web useCssElementAndTextForDropdownListAnd(String css) {
        WebElement element = findElementByCss(css);
        return this;
    }

    public WebAssertion useXpathElementAndTextForDropdownList(String xpath) {
        WebElement element = findElementByXpath(xpath);
        return new WebAssertion(element);
    }

    public Web useXpathElementAndTextForDropdownListAnd(String xpath) {
        WebElement element = findElementByXpath(xpath);
        return this;
    }

    public WebAssertion waitUntilTextAppearsCss(String css, String text, int milisecondsToWait) {
        WebElement element = findElementByCss(css);
        return new WebAssertion(element);
    }

    public Web waitUntilTextAppearsCssAnd(String css, String text, int milisecondsToWait) {
        WebElement element = findElementByCss(css);
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.textToBePresentInElement(element, text));
        return this;
    }

    public WebAssertion waitUntilTextAppearsXpath(String xpath, String text, int milisecondsToWait) {
        WebElement element = findElementByXpath(xpath);
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.textToBePresentInElement(element, text));
        return new WebAssertion(element);
    }

    public Web waitUntilTextAppearsXpathAnd(String xpath, String text, int milisecondsToWait) {
        WebElement element = findElementByXpath(xpath);
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.textToBePresentInElement(element, text));
        return this;
    }

    public WebAssertion waitUntilAttributeAppearsCss(String css, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = findElementByCss(css);
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        return new WebAssertion(element);
    }

    public Web waitUntilAttributeAppearsCssAnd(String css, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = findElementByCss(css);
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        return this;
    }

    public WebAssertion waitUntilAttributeAppearsXpath(String xpath, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = findElementByXpath(xpath);
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        return new WebAssertion(element);
    }

    public Web waitUntilAttributeAppearsXpathAnd(String xpath, String attributeName, String attributeValue, int milisecondsToWait) {
        WebElement element = findElementByXpath(xpath);
        new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.attributeContains(element, attributeName, attributeValue));
        return this;
    }

    public WebAssertion waitUntilElementIsPresentCss(String css, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
        return new WebAssertion(element);
    }

    public Web waitUntilElementIsPresentCssAnd(String css, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
        return this;
    }

    public WebAssertion waitUntilElementIsPresentXpath(String xpath, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return new WebAssertion(element);
    }

    public Web waitUntilElementIsPresentXpathAnd(String xpath, int milisecondsToWait) {
        WebElement element = new WebDriverWait(driver, milisecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return this;
    }

    public Web waitUntilElementIsInvisibleXpathAnd(String xpath, int milisecondsToWait) {
        WebDriverWait wait = new WebDriverWait(driver, milisecondsToWait);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        return this;
    }

    public WebAssertion useElementByNameAndAttributeForAssertion(String name, String attribute, String expectedText) {
        WebElement element = driver.findElement(By.name(name));
        String value = element.getAttribute(attribute).toString();
        Assert.assertEquals(value, expectedText);
        return new WebAssertion(element);
    }

    public WebAssertion useCssSelectorAndOpacityAttrForAssertion(String css, String expectedOpacity) {
        WebElement element = findElementByCss(css);
        driver.findElement(By.cssSelector(css)).isEnabled();
        if (element.getCssValue("opacity").toString().equals(expectedOpacity)) {
            Assert.assertEquals(element.getCssValue("opacity").toString(), expectedOpacity, "Input Field should be enabled");
        } else {
            Assert.assertEquals(element.getCssValue("opacity").toString(), expectedOpacity, "Input Field should be disabled");
        }
        return new WebAssertion(element);
    }

    public CustomWebElement useJSExecutor(String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = (WebElement) js.executeScript(script);
        return new CustomWebElement(element, this, driver);
    }

    public WebAssertion useClassNameElementsForAssertion(String className) {
        List<WebElement> elements = driver.findElements(By.className(className));
        return new WebAssertion(elements);
    }


    public void useXpathListForSoftAssertion(String... xpathList) {
        SoftAssert softAssert = new SoftAssert();
        for (String xpath : xpathList) {
            try {
                driver.findElement(By.xpath(xpath));
            } catch (Exception e) {
                softAssert.fail("Element with xpath + " + xpath + "not found");
            }
        }
        softAssert.assertAll();
    }

    public Web useJavaScriptAnd(String xpath, String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (xpath != null) {
            WebElement element = driver.findElement(By.xpath(xpath));
            js.executeScript(script, element);
        } else {
            js.executeScript(script);
        }
        return this;
    }

    public WebAssertion useJSExecutorForAssertion(String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> elements = (List<WebElement>) js.executeScript(script);
        return new WebAssertion(elements);
    }

    public WebAssertion useXpathList(String... xpathList) {
        List<WebElement> elementList = new ArrayList<>();
        for (String xpath : xpathList) {
            WebElement element = findElementByXpath(xpath);
            elementList.add(element);
        }
        return new WebAssertion(elementList);
    }

    public WebAssertion useUrlForAssertion(String... params) {
        String url = driver.getCurrentUrl();
        return new WebAssertion(url, params);
    }

}
