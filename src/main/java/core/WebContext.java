package core;

import core.modules.library.models.Config;
import core.modules.web.models.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import java.util.ArrayList;

@Configuration
@Import(CoreContext.class)
@Lazy
public class WebContext {

    @Autowired
    private Config config;

    @Bean
    public Config webConfig() {
        return new Config("web.ini");
    }

    @Bean
    public ArrayList<WebDriver> webDrivers() {
        return new WebDriverFactory(webConfig()).webDrivers();
    }
}
