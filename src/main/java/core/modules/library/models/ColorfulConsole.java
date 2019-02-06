package core.modules.library.models;

public class ColorfulConsole {

    public static final int RED = 31;
    public static final int BLACK = 30;
    public static final int GREEN = 32;
    public static final int YELLOW = 33;
    public static final int BLUE = 34;
    public static final int MAGENTA = 35;
    public static final int CYAN = 36;
    public static final int WHITE = 37;
    public static final int BRIGHT_BLACK = 90;
    public static final int BRIGHT_RED = 91;
    public static final int BRIGHT_GREEN = 92;
    public static final int BRIGHT_YELLOW = 93;

    public void print(String string, int color) {
        System.out.print("\033["+ Integer.toString(color) +"m" + string + "\033[0m");
    }

    public void println(String string, int color) {
        System.out.println("\033["+ Integer.toString(color) +"m" + string + "\033[0m");
    }
}
