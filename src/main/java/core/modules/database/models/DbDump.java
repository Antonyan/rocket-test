package core.modules.database.models;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class DbDump {

    private String dump;
    private String host;
    private String port;
    private String dbUser;
    private String dbName;
    private String dbPassword;

    public DbDump(Preferences config) {
        this(
                config.get("dump", ""),
                config.get("host", ""),
                config.get("port", ""),
                config.get("dbName", ""),
                config.get("dbUser", ""),
                config.get("dbPassword", "")
        );
    }

    private DbDump(
            String dump,
            String host,
            String port,
            String dbName,
            String dbUser,
            String dbPassword) {
        this.dump = dump;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public void rollOut() {
        try {
            List<String> params = commandShellParams();
            params.add("mysql -u" + dbUser + " -p" + dbPassword +
                    " -h" + host + " --port=" + port + " " + dbName + " < " + "db/" + dump);

            Process p = new ProcessBuilder(params).redirectErrorStream(true).start();
            p.waitFor();

        } catch (Exception exception) {
            System.out.println("Can\'t read dump: " + exception.getMessage());
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    /**
     * Install mysql client for Windows and add to PATH system env variable path to folder with mysql.exe
     * @return List<String>
     */
    private List<String> windowsCommandShellParams()
    {
        List<String> params = new ArrayList<>();
        params.add("cmd");
        params.add("/c");

        return params;
    }

    private List<String> unixCommandShellParams()
    {
        List<String> params = new ArrayList<>();
        params.add("sh");
        params.add("-c");

        return params;
    }

    private List<String> commandShellParams()
    {
        if (isWindows()) {
            return windowsCommandShellParams();
        }

        return unixCommandShellParams();
    }
}
