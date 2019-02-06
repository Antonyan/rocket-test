package core.service;

import core.MobileContext;
import core.modules.mobile.models.MobileDriver;
import core.modules.mobile.services.Mobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;

@ContextConfiguration(classes = {MobileContext.class}, loader = AnnotationConfigContextLoader.class)
public class MobileTest extends AbstractTestNGSpringContextTests {

    private Mobile mobile;
    private MobileDriver mobileDriver;

    @AfterClass
    public void tearDown() throws Exception {
        mobileDriver.quit();
    }

    public Mobile getMobile() throws Exception{
        if (mobile != null){
            return mobile;
        }

        mobile = new Mobile(getMobileDriver().driver());
        return mobile;
    }

    @Autowired
    public MobileTest setMobileDriver(MobileDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        return this;
    }

    private MobileDriver getMobileDriver() {
        return mobileDriver;
    }
}
