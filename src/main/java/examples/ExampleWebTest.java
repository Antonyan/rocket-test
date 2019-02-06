package examples;

import core.service.WebTest;
import org.testng.annotations.Test;

public class ExampleWebTest extends WebTest {

    @Test(enabled = true,
            groups = { "Dump:Basic", "Web test" },
            description = "Add product to cart",
            dataProvider = "drivers")
    public void addProductToCart (Integer driverNumber) {
        web(driverNumber)
            .openAnyUrl("https://best.aliexpress.com")
            .typeTextToInputFieldXpath("//*[@id=\"search-key\"]", "lego")
            .pressEnterAnd()
            .useElementByXpath("//*[@id=\"hs-below-list-items\"]/li[1]/div/div[1]/div/a/img")
            .clickAnd()
            .switchToNewTab()
            .useElementByLinkText("x")
            .clickAnd()
            .waitUntilElementIsInvisibleXpathAnd("/html/body/div[20]/div/div/div", 2000)
            .useElementByXpath("//*[@id=\"sku-1-200006151\"]/img")
            .clickAnd()
            .useElementByXpath("//*[@id=\"j-add-cart-btn\"]")
            .clickAnd()
            .useElementByLinkText("View Shopping Cart")
            .clickAnd()
            .useXpathElementForAssertion("//*[@id=\"32895894410-14:200006151\"]/td[1]")
            .shouldBeDisplayed(true);
    }
}
