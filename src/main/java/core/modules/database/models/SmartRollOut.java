package core.modules.database.models;

public class SmartRollOut {

    private boolean rolledOut = false;

    private DbDump dbDump;

    public SmartRollOut(DbDump dbDump) {
        this.dbDump = dbDump;
    }

    public void rollOut() {

        if (rolledOut) {
            return;
        }

        dbDump.rollOut();
        rolledOut = true;
    }
}
