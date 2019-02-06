package core.modules.library.models;

import org.testng.ITestResult;

public class Verbose {

    private Boolean debug;

    public Verbose(Boolean debug) {
        this.debug = debug;
    }

    public void testInfo (String stepName, String info) {
        if (debug) {
            new ColorfulConsole().print("Step name: " + stepName + ". ", ColorfulConsole.BLUE);
            new ColorfulConsole().println("Info: " + info, ColorfulConsole.CYAN);
        }
    }

    public void testReport (ITestResult result) {
        if (!debug) {
            return;
        }

        reportStatus(result);
        reportDuration(result);
    }

    private void reportDuration(ITestResult result) {
        new ColorfulConsole().print( "Duration: ", ColorfulConsole.BLUE);
        new ColorfulConsole().print( Long.toString(result.getEndMillis() - result.getStartMillis()), ColorfulConsole.CYAN);
        new ColorfulConsole().println( " ms.", ColorfulConsole.BLUE);
    }

    private void reportStatus(ITestResult result) {
        String status = "";
        int color = ColorfulConsole.RED;

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:

                status = "Passed";
                color = ColorfulConsole.BRIGHT_GREEN;

                break;

            case ITestResult.FAILURE:

                status = "Failed";
                color = ColorfulConsole.RED;

                break;

            case ITestResult.SKIP:

                status = "Skipped";
                color = ColorfulConsole.YELLOW;

        }

        new ColorfulConsole().print("Status for test ", ColorfulConsole.BLUE);
        new ColorfulConsole().print(result.getName(), ColorfulConsole.CYAN);
        new ColorfulConsole().print(": ", ColorfulConsole.BLUE);
        new ColorfulConsole().println(status,color);
    }
}
