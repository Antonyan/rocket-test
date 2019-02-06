package core.modules.database.models;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.prefs.Preferences;

public class DbConnection {

    private DbDump dbDump;
    private String host;
    private String port;
    private String dbUser;
    private String dbName;
    private String dbPassword;
    private Boolean populate;
    private Boolean firstLaunch = true;

    public DbConnection(Preferences config, DbDump dbDump) {
        this(
                dbDump,
                config.getBoolean("populate", false),
                config.get("host", ""),
                config.get("port", ""),
                config.get("dbName", ""),
                config.get("dbUser", ""),
                config.get("dbPassword", "")
                );
    }

    private DbConnection(
            DbDump dbDump,
            Boolean populate,
            String host,
            String port,
            String dbName,
            String dbUser,
            String dbPassword) {
        this.dbDump = dbDump;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.populate = populate;
    }

    public DriverManagerDataSource driverManager() {

        DriverManagerDataSource driver = new DriverManagerDataSource(
                "jdbc:mysql://" + host + ":" + port + "/" + dbName, dbUser, dbPassword);

        if (populate && firstLaunch) {
            dbDump.rollOut();
            firstLaunch = false;
        }

        return driver;
    }
}
