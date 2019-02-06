package core;

import core.modules.mobile.models.MobileDriver;
import core.modules.library.models.Config;
import core.modules.database.services.DbPrecondition;
import core.modules.library.models.SshClient;
import core.modules.mobile.services.Mobile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class CoreContext {
    @Bean
    public Config config() {
        return new Config("config.ini");
    }

    @Bean
    public SshClient amazonSshClient() {
        return new SshClient(config().getPreference().node("amazonSsh"));
    }

    @Bean
    public DbPrecondition dbPrecondition() {
        return new DbPrecondition(amazonSshClient(), config().getPreference().node("dbPrecondition"));
    }

    @Bean
    public MobileDriver mobileDriver(){
        return new MobileDriver(config().getPreference().node("iOS"));
    }

    @Bean
    public Mobile mobile() throws Exception {
        return new Mobile(mobileDriver().driver());
    }
}
