public class bingo_main {
    // Colors per estilitzar la terminal
    final String ANSI_BLACK="\033[30m";
    public static final String ANSI_RED="\033[31m";
    public static final String ANSI_GREEN="\033[32m";
    public static final String ANSI_YELLOW="\033[33m";
    public static final String ANSI_BLUE="\033[34m";
    public static final String ANSI_PURPLE="\033[35m";
    public static final String ANSI_CYAN="\033[36m";
    public static final String ANSI_WHITE="\033[37m";
    public static final String ANSI_RESET="\u001B[0m";

    public static final String at = "@";
    public static final String space = " ";

    public static String[][] canva;
    public static String[][] carton = new String[3][9];

    public static boolean finished = false;
    public static String errorMsg = "";


    private static void getGameInfo() {
        System.out.printf("""
                        %s⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜%s
                        %s╋╋╋╋╋╋╋╋╋╋╋╋╋%s %sBINGO%s %s╋╋╋╋╋╋╋╋╋╋╋╋╋%s
                        %s⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜%s
                        """,
                ANSI_CYAN, ANSI_RESET,
                ANSI_RED, ANSI_RESET, ANSI_YELLOW, ANSI_RESET, ANSI_RED, ANSI_RESET,
                ANSI_CYAN, ANSI_RESET
        );
    }

    // Metode principal
    public static void main(String[] args) {
        getGameInfo();

    }
}