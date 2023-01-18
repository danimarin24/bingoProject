import java.util.*;

public class bingo_testing {
    public static final String ANSI_RESET="\u001B[0m";
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN

    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN


    public static final String at = "@";
    public static final String hyphen = "—";

    public static boolean finished = false;


    // Metode principal
    public static void main(String[] args) {

        game();



    }

    private static void game() {
        int cardsNumber;
        String[][][] cards;
        int[][][] cardsColumns;
        boolean character;
        // PRINT WELCOME MESSAGE
        getGameInfo();
        do {
            if (Util.demanarChar("Do you want to play bingo?", 's', 'n') == 's') {
                cardsNumber = Util.demanarIntegerMinMax("Number of cards to be played: ", 1, 99);
                cards = new String[cardsNumber][3][9];
                cardsColumns = new int[cardsNumber][9][3];

                generateAllCards(cards, cardsColumns);



                getBomboNumbers(1, 90, cards, cardsColumns);

            } else {
                System.out.println("BYE!! See u soon :D");
                finished = false;
            }






        } while (finished);
    }

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

    private static void getBomboNumbers(int min, int max, String[][][] cards, int[][][] cardsColumns) {
        int aux = 0;
        int[] arrBomboNumbers = generateBomboArray(min, max);
        do {
            printCards(cards);

            System.out.printf("%sNew number: %d%s\n", GREEN_BACKGROUND_BRIGHT + BLACK_BOLD, arrBomboNumbers[aux], ANSI_RESET);


            searchAndReplaceValue(arrBomboNumbers[aux], cards);

            // incrementar posición del nuevo número;
            aux++;
        }while (Util.demanarChar("Next Number", 's', 'n') == 's');


    }

    /**
     * Método para buscar los números que van saliendo del bombo
     * y remplazarlos con un guion.
     * @param num
     * @param arr
     * @void Imprime si se tiene el valor buscado, y si lo ha reemplazado
     */
    private static void searchAndReplaceValue(int num, String[][][] arr) {
        boolean isIn = false;
        int position;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                isIn = Arrays.asList(arr[i][j]).contains(String.valueOf(num));
                if (isIn) {
                    System.out.println("You had this number, I mark it with a hyphen. " + YELLOW_BACKGROUND_BRIGHT + BLACK_BOLD + "[-]" + ANSI_RESET);
                    position = Arrays.asList(arr[i][j]).indexOf(String.valueOf(num));
                    arr[i][j][position] = hyphen;
                    break;
                }
            }
        }
    }

    /**
     * Método para generar un arreglo de las medidas que se pasan
     * en los parámetros que genera números aleatorios que no se
     * repiten entre ellos.
     * @param min
     * @param max
     * @return un arreglo unidimensional con números random.
     */
    private static int[] generateBomboArray(int min, int max) {
        int randomNum;
        boolean checked;
        int[] bomboNumbers = new int[max];
        for (int i = 0; i < max; i++) {
            do {
                randomNum = (int)(Math.random() * max) + min;
                checked = checkRandomNumberArr(randomNum, bomboNumbers);
            } while (checked);
            bomboNumbers[i] = randomNum;
        }
        return bomboNumbers;
    }

    private static void generateAllCards(String[][][] cards, int[][][] cardsColumns) {
        getRandomValues(cards, cardsColumns);
        sortCards(cards, cardsColumns);
        fillCardsSorted(cards, cardsColumns);
        fillCardsAt(cards);
    }

    private static void getRandomValues(String[][][] cards, int[][][] cardsColumns) {
        int aux, minRange, maxRange, range, randomNum;
        boolean checked;
        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                aux = 1;
                for (int z = 0; z < cards[x][y].length; z++) {
                    maxRange = (aux < 9) ? (aux * 10 - 1) : (aux * 10);
                    minRange = (aux == 1) ? 1 : (aux * 10 - 10);
                    range = maxRange - minRange + 1;
                    do {
                        randomNum = (int)(Math.random() * range) + minRange;
                        //System.out.printf("z = %d || min = %d || max = %d || rango = %d || random = %d \n", z, minRange, maxRange, range, randomNum);

                        checked = checkRandomNumberBiArr(randomNum, cardsColumns[x]);
                    } while (checked);
                    cardsColumns[x][z][y] = randomNum;

                    aux++;
                }
            }
        }
    }

    // RELLENAR EL ARRAY DE STRINGS
    private static void fillCardsSorted(String[][][] cards, int[][][] cardsColumns) {
        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                for (int z = 0; z < cards[x][y].length; z++) {
                    cards[x][y][z] = String.valueOf(cardsColumns[x][z][y]);
                }
            }
        }

    }

    // ORDENAR LOS CARTONES
    private static void sortCards(String[][][] cards, int[][][] cardsColumns) {
        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                for (int z = 0; z < cards[x][y].length; z++) {
                    Arrays.sort(cardsColumns[x][z]);
                }
            }
        }
    }

    // REVISAR NUMERO REPETIDO ARRAY BIDIMENSIONAL
    private static boolean checkRandomNumberBiArr(int num, int[][] arr) {
        boolean repeated = false;

        for (int[] ints : arr) {
            for (int anInt : ints) {
                if (num == anInt) {
                    repeated = true;
                    break;
                }
            }
        }

        return repeated;
    }

    // REVISAR NUMERO REPETIDO ARRAY UNIDIMENSIONAL
    private static boolean checkRandomNumberArr(int num, int[] arr) {
        boolean repeated = false;

        for (int j : arr) {
            if (num == j) {
                repeated = true;
                break;
            }
        }

        return repeated;
    }

    private static void fillCardsAt(String[][][] arr) {
        int counter = 0, atPosition;

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

    private static void printCards(String[][][] arr) {
        System.out.println();
        for (String[][] strings : arr) {
            for (String[] string : strings) {
                for (String s : string) {
                    if (s.equals(at)) {
                        printColor(WHITE_BACKGROUND_BRIGHT, BLACK_BOLD, ANSI_RESET, s);
                    } else if (s.equals(hyphen)) {
                        printColor(YELLOW_BACKGROUND_BRIGHT, BLACK_BOLD, ANSI_RESET, s);
                    } else {
                        printColor(RED_BACKGROUND_BRIGHT, BLACK_BOLD, ANSI_RESET, s);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static void printColor(String bgColor, String color, String reset, String text) {
        System.out.printf(bgColor + color + "%2s " + reset, text);
    }
}