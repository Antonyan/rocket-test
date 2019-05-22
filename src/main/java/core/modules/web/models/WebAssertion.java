package core.modules.web.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebAssertion {

    private WebElement elementForAssert;
    private List<WebElement> elementListForAssert;
    private String url;
    private String[] params;

    public WebAssertion(WebElement elementForAssert) {
        this.elementForAssert = elementForAssert;
    }

    public WebAssertion(List<WebElement> elementListForAssert) {
        this.elementListForAssert = elementListForAssert;
    }

    public WebAssertion(String url, String[] params) {
        try {
            this.url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.params = params;
    }

    public void shouldBeEnabled(boolean condition) throws Exception {
        throw new Exception("Method isn't support");
    }

    public void shouldContainText(String text) {
        Assert.assertEquals(text, elementForAssert.getText(), "Element " + elementForAssert.toString() + " doesn't have text" + text);

    }

    public void shouldBeDisplayed(boolean condition) {
        if (condition) {
            Assert.assertTrue(elementForAssert.isDisplayed(), "Element " + elementForAssert.toString() + " is not displayed");
            return;
        }
        Assert.assertFalse(elementForAssert.isDisplayed(), "Element " + elementForAssert.toString() + " is displayed");
    }

    public void shouldBeVisibleByCss(boolean condition, String cssSelector) {
        if (condition) {
            Assert.assertTrue(isElementPresent(By.cssSelector(cssSelector)), "Element is not visible");
            return;
        }
        Assert.assertFalse(isElementPresent(By.cssSelector(cssSelector)), "Element is visible");
    }

    public void shouldBeVisibleByXpath(boolean condition, String xpathSelector) {
        if (condition) {
            Assert.assertTrue(isElementPresent(By.xpath(xpathSelector)));
            return;
        }
        Assert.assertFalse(isElementPresent(By.xpath(xpathSelector)));
    }

    public boolean isElementPresent(By selector) {
        try {
            elementForAssert.findElement(selector);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void elementListShouldBeDisplayed(boolean condition) {
        SoftAssert softAssert = new SoftAssert();
        for (WebElement element : elementListForAssert) {
            if (condition) {
                softAssert.assertTrue(element.isDisplayed(), "Element " + element.toString() + " is not displayed");
                return;
            }
            softAssert.assertFalse(element.isDisplayed(), "Element " + element.toString() + " is displayed");
        }
        softAssert.assertAll();
    }

    public void shouldBeImage(boolean condition) throws Exception {
        throw new Exception("Method isn't support");
    }

    public void shouldBeSelected(boolean condition) {
        if (condition) {
            Assert.assertTrue(elementForAssert.isSelected(), "Element " + elementForAssert.toString() + " is not selected");
            return;
        }
        Assert.assertFalse(elementForAssert.isSelected(), "Element " + elementForAssert.toString() + " is selected");
    }

    public void elementListShouldContainText(String... expectedText) {
        checkTextListForEqual(expectedText);
    }

    public void fieldShouldContainText(String attributeName, Object value) {
        checkAttributeValueTextForEqual(attributeName, value);
    }

    public void urlShouldContainValues(String... expectedValues) {
        System.out.println(url);
        List<String> expectedValueList = convertToLowerCase(new ArrayList<>(Arrays.asList((String[]) expectedValues)));
        List<String> paramList = new ArrayList<>(Arrays.asList((String[]) params));
        List<String> actualValueList = getUrlValues(paramList);

        List<String> sourceList = new ArrayList<String>(expectedValueList);
        List<String> destinationList = new ArrayList<String>(actualValueList);
        sourceList.removeAll(actualValueList);
        destinationList.removeAll(expectedValueList);

        Assert.assertEquals(actualValueList.toString(), expectedValueList.toString(), "Values" + sourceList.toString() + " are not present in url");
    }

    //TODO: reduce the method by extracting code in other method
    private void checkTextListForEqual(Object value) {
        if (value instanceof String) {
            String actualValue = elementForAssert.getText().toLowerCase().trim();
            String expectedValue = ((String) value).toLowerCase().trim();
            Assert.assertEquals(actualValue, expectedValue, "Text " + actualValue + " is not present");
        } else if (value instanceof String[]) {
            ArrayList<String> actualValueList = new ArrayList<>();
            ArrayList<String> expectedValues = convertToLowerCase(new ArrayList<>(Arrays.asList((String[]) value)));

            for (WebElement element : elementListForAssert) {
                String label = element.getText().toLowerCase();
                actualValueList.add(label.toLowerCase());
            }

            List<String> sourceList = new ArrayList<String>(expectedValues);
            List<String> destinationList = new ArrayList<String>(actualValueList);
            sourceList.removeAll(actualValueList);
            destinationList.removeAll(expectedValues);

            System.out.println(actualValueList.toString());
            System.out.println(expectedValues.toString());

            Assert.assertEquals(actualValueList.toString(), expectedValues.toString(), "Text" + sourceList.toString() + " are not present");
        }
    }

    //TODO: reduce the method by extracting code in other method
    private void checkAttributeValueTextForEqual(String attributeName, Object value) {
        if (value instanceof String) {
            String actualValue = elementForAssert.getAttribute(attributeName).toLowerCase().trim();
            String expectedValue = ((String) value).toLowerCase();
            Assert.assertEquals(actualValue, expectedValue, "Text " + actualValue + " is not present");
        } else if (value instanceof String[]) {
            ArrayList<String> actualValueList = new ArrayList<>();
            ArrayList<String> expectedValues = convertToLowerCase(new ArrayList<>(Arrays.asList((String[]) value)));

            for (WebElement elemnent : elementListForAssert) {
                String label = elemnent.getAttribute(attributeName).toLowerCase();
                actualValueList.add(label.toLowerCase());
            }

            List<String> sourceList = new ArrayList<String>(expectedValues);
            List<String> destinationList = new ArrayList<String>(actualValueList);
            sourceList.removeAll(actualValueList);
            destinationList.removeAll(expectedValues);
            Assert.assertEquals(actualValueList.toString(), expectedValues.toString(), "Text" + sourceList.toString() + " are not present");
        }
    }

    private ArrayList<String> convertToLowerCase(ArrayList<String> list) {
        ArrayList<String> lowerCaseList = new ArrayList<>();
        for (String value : list) {
            lowerCaseList.add(value.toLowerCase());
        }
        return lowerCaseList;
    }

    private List<String> getUrlValues(List<String> paramList) {
        List<String> actualValuesList = new ArrayList<>();
        for (String parameter : paramList) {
            Pattern pattern = Pattern.compile("(?<=" + parameter + "=)(.*?)(?=&)");
            Matcher matcher = pattern.matcher(url);
            while (matcher.find()) {
                actualValuesList.add(matcher.group().toLowerCase());
            }
        }
        return actualValuesList;
    }
}
