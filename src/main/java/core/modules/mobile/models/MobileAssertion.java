package core.modules.mobile.models;

import io.appium.java_client.MobileElement;
import org.testng.Assert;

import java.util.List;

public class MobileAssertion {

    private MobileElement elementForAssert;
    private List<MobileElement> elementsForAssert;

    public MobileAssertion(MobileElement elementForAssert) {
        this.elementForAssert = elementForAssert;
    }

    public MobileAssertion(List<MobileElement> elementsForAssert) {
        this.elementsForAssert = elementsForAssert;
    }

    public void listShouldContains(String text) {

        for (MobileElement textField : elementsForAssert){
            System.out.println(textField.getText());
            if (textField.getText().equals(text)){
                Assert.assertEquals(true, true, "Element " + text + " was found in list");
                return;
            }
        }

        Assert.assertEquals(true, false, "Can't find element " + text + " in the list");
    }
}
