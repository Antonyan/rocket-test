package core.modules.database.services;

import core.modules.library.models.SshClient;

import java.util.prefs.Preferences;

public class DbPrecondition {

    private SshClient sshClient;
    private String dump;
    private Boolean cleanup;
    private Boolean populate;
    private Boolean firstLaunch = true;

    public DbPrecondition(SshClient sshClient, Preferences config) {
        this(sshClient, config.get("dump", ""),
                config.getBoolean("cleanup", false), config.getBoolean("populate", false));
    }

    public DbPrecondition(SshClient sshClient, String dump, Boolean cleanup, Boolean populate) {
        this.sshClient = sshClient;
        this.dump = dump;
        this.cleanup = cleanup;
        this.populate = populate;
    }

    public void prepareDb() {
        if (!populate) {
            return;
        }

        if (firstLaunch) {
            sshClient.transferFile(dump, "dump-test.sql");
        }

        if (firstLaunch || cleanup) {
            sshClient.execRemoteCommand("cat dump-test.sql | mysql -uroot wap_magento");
        }

        firstLaunch = false;
    }
}
