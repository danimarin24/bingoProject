import java.util.*;

public class bingo_main {
    // Colors per estilitzar la terminal
    public static final String ANSI_RESET="\u001B[0m";
    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public static final String at = "@";
    public static final String space = " ";

    public static String[][] carton = new String[3][9];

    public static boolean finished = false;
    public static String errorMsg = "";


    private static void getGameInfo() {
        System.out.printf("""
                        %s⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜%s
                        %s╋╋╋╋╋╋╋╋╋╋╋╋╋%s %sBINGO%s %s╋╋╋╋╋╋╋╋╋╋╋╋╋%s
                        %s⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜⁜%s
                        """,
                CYAN_BOLD, ANSI_RESET,
                RED_BOLD, ANSI_RESET, YELLOW_BOLD, ANSI_RESET, RED_BOLD, ANSI_RESET,
                CYAN_BOLD, ANSI_RESET
        );
    }

    // Metode principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        getGameInfo();

        int randomNum;
        String stringRandomNum;
        int cardsNumber = 25;

        String[][][] cards = new String[cardsNumber][3][9];
        int[][] cardV2 = new int[cardsNumber][27];

        boolean checked = false;

        System.out.println("ejecutandose");

        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                for (int z = 0; z < cards[x][y].length; z++){
                    do {
                        randomNum = (int) (Math.random()*90+1);
                        stringRandomNum = convertIntToString(randomNum);
                        checked = checkRandomNumber(stringRandomNum, cards[x]);
                    } while (checked);
                    cards[x][y][z] = stringRandomNum;
                }
            }
        }
        fillCardsAt(cards);
        printCards(cards);
        System.out.println("ejecutandose");
    }

    private static void fillCardsAt(String[][][] arr) {
        int counter = 0, atPosition;
        String stringAtPosition;

        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[x].length; y++) {
                counter = 0;
                for (int z = 0; z < arr[x][y].length; z++){
                    if (counter < 4) {
                        atPosition = (int) (Math.random()*9);
                        if (!arr[x][y][atPosition].equals(at)) {
                            arr[x][y][atPosition] = at;
                            counter++;
                        }
                    }
                }
            }
        }
    }

    private static String convertIntToString(int num) {
        return "" + num;
    }

    private static void printCards(String[][][] arr) {
        System.out.println(WHITE_BACKGROUND_BRIGHT + BLACK_BOLD + "CARTONES" + ANSI_RESET);
        for (String[][] strings : arr) {
            System.out.print(RED_BACKGROUND_BRIGHT + BLACK_BOLD);
            for (String[] string : strings) {
                for (String s : string) {
                    System.out.printf("%3s", s);
                }
                System.out.println();
            }
            System.out.println(ANSI_RESET);
        }
    }

    private static boolean checkRandomNumber(String num, String[][] arr) {
        boolean repeated = false;

        for (String[] string : arr) {
            for (String s : string) {
                if (num.equals(s)) {
                    repeated = true;
                    break;
                }
            }
        }

        return repeated;
    }
}