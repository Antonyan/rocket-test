package unit.db;

import core.modules.library.models.Config;
import core.modules.database.models.DbConnection;
import core.modules.database.models.DbDump;
import junit.framework.*;

public class DbConnectionTest extends TestCase {

    public void testDriverManager() {
        Config config = new Config("config.ini");

        DbConnection dbConnection = new DbConnection(config.getPreference().node("Db"), new DbDump(config.getPreference().node("Db")));
        dbConnection.driverManager();
    }

}
