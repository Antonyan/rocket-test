package core.modules.library.models;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Config {
    private Preferences preference;

    public Config(String configName) {
        Ini ini = null;
        String iniPath = System.getProperty("user.dir")
                + File.separator + "config" + File.separator + configName;
        try {
            ini = new Ini(new File(iniPath));
        } catch (IOException e) {
            System.out.println("Can't download config file path: " + iniPath);
        }
        setPreference(new IniPreferences(ini));
    }

    public Preferences getPreference() {
        return preference;
    }

    private Config setPreference(Preferences preference) {
        this.preference = preference;
        return this;
    }
}
