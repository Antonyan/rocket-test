package core;

import core.modules.database.models.DatabaseMetadata;
import core.modules.database.models.SmartRollOut;
import core.modules.library.models.Config;
import core.modules.database.services.Database;
import core.modules.database.services.DatabaseAssert;
import core.modules.database.models.DbConnection;
import core.modules.database.models.DbDump;
import core.modules.database.services.LightQueryBuilder;
import core.modules.library.models.Verbose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.prefs.Preferences;

@Configuration
@ComponentScan(basePackages = {"core.modules.database"})
@Import(CoreContext.class)
@Lazy
public class DatabaseContext {

    @Autowired
    private Config config;

    @Bean
    public Config dbConfig() {
        return new Config("db.ini");
    }

    @Bean
    public Preferences generalConfig() {
        return config.getPreference().node("General");
    }

    @Bean
    public Verbose verbose() {
        return new Verbose(config.getPreference().node("General").getBoolean("debug", false));
    }

    @Bean
    public DbDump dbDump() {
        return new DbDump(dbConfig().getPreference().node("Db"));
    }

    @Bean
    public SmartRollOut smartRollOut() {
        return new SmartRollOut(dbDump());
    }

    @Bean
    public DriverManagerDataSource driverManager () {
        return new DbConnection(dbConfig().getPreference().node("Db"), dbDump()).driverManager();
    }

    @Bean
    @Scope("prototype")
    public JdbcTemplate jdbcTemplate () {
        return new JdbcTemplate(driverManager());
    }

    @Bean
    public LightQueryBuilder lightQueryBuilder () {
        return new LightQueryBuilder();
    }

    @Bean
    public DatabaseAssert databaseAssert () {
        return new DatabaseAssert(jdbcTemplate(), lightQueryBuilder(), verbose());
    }

    @Bean
    public DatabaseMetadata databaseMetadata () {
        return new DatabaseMetadata(
                dbConfig().getPreference().node("Db").get("dbName", ""),
                jdbcTemplate());
    }

    @Bean
    public Database databaseDriver () {
        return new Database(
                databaseMetadata(),
                databaseAssert(),
                jdbcTemplate(),
                lightQueryBuilder(),
                verbose()
        );
    }
}
