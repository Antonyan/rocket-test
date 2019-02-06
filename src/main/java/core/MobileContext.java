package core;

import core.modules.library.models.Config;
import core.modules.mobile.models.MobileDriver;
import core.modules.mobile.services.Mobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Configuration
@Import(CoreContext.class)
@Lazy
public class MobileContext {

    @Autowired
    private Config config;

    @Bean
    public Config mobileConfig() {
        return new Config("mobile.ini");
    }

    @Bean
    public MobileDriver mobileDriver(){
        return new MobileDriver(mobileConfig().getPreference().node("iOS"));
    }

    @Bean
    public Mobile mobile() throws Exception {
        return new Mobile(mobileDriver().driver());
    }
}
