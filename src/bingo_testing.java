import java.util.*;

public class bingo_testing {
    public static final String ANSI_RESET="\u001B[0m";
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHIT


    public static final String at = "@";
    public static final String hyphen = "—";

    public static boolean finished = false;


    /**
     * Método principal
     * @param args
     */
    public static void main(String[] args) {
        // PRINT WELCOME MESSAGE
        getGameInfo();
        do {
            if (Util.demanarChar("Do you want to play bingo?", 's', 'n') == 's') {
                finished = game();
            } else { // if the user input 'n', the game ends
                System.out.println("BYE!! See u soon :D");
                finished = false;
            }
        } while (finished);
    }

    /**
     * Método para jugar al juego BINGO
     * en este método comienzan todas las llamadas
     * a todos los métodos que hay en esta clase.
     * Este método utiliza internamente estos métodos
     * @method {@link Util#demanarChar}
     * @method {@link Util#demanarIntegerMinMax}
     * @method {@link #generateAllCards}
     * @method {@link #printCards}
     * @method {@link #generateBomboArray}
     * @method {@link #checkLine}
     * @method {@link #searchAndReplaceValue}
     */
    private static boolean game() {
        int cardsNumber, aux = 0;
        String[][][] cards;
        int[][][] cardsColumns;
        boolean checkLine = false, checkBingo = false;
        int[] arrBomboNumbers = generateBomboArray(1, 90);

        cardsNumber = Util.demanarIntegerMinMax("Number of cards to be played: ", 1, 99);
        cards = new String[cardsNumber][3][9];
        cardsColumns = new int[cardsNumber][9][3];

        generateAllCards(cards, cardsColumns);

        // start game with cards generated
        do { // only play if the user input 's', if not the game ends.
            printCards(cards); // print cards every time

            System.out.printf("%sNew number: %d%s\n", GREEN_BACKGROUND_BRIGHT + BLACK_BOLD, arrBomboNumbers[aux], ANSI_RESET);

            searchAndReplaceValue(arrBomboNumbers[aux], cards); // change number value with a hyphen every time
            if (!checkLine) {
                checkLine = checkLine(cards); // check line every time, until it is
            }

            if (checkBingo(cards)) { // check bingo every time, until it is
                return true;
            }

            // incrementar posición del nuevo número del bombo;
            aux++;
        }while (Util.demanarChar("Next Number", 's', 'n') == 's');
        return false;
    }

    /**
     * Método para devolver la información del juego
     * al que el usuario está jugando.
     * En este caso el bingo, este método imprime un mensaje
     * por pantalla.
     */
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

    /**
     * Método para cantar línea cuando hay 5 números que han salido
     * en algún cartón, este método solo se llama una vez por partida
     * para que solo salga una línea.
     * @param arr
     * @return
     */
    private static boolean checkLine(String[][][] arr) {
        int aux;
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[x].length; y++) {
                aux = 0;
                for (int z = 0; z < arr[x][y].length; z++) {
                    if (aux < 5) {
                        if (arr[x][y][z].equals(hyphen)) {
                            aux++;
                        }
                        if (aux == 5) {
                            System.out.printf("%sThere is a LINE on card %s%s\n", BLUE_BACKGROUND_BRIGHT + BLACK_BOLD, x + 1, ANSI_RESET);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método para cantar bingo cuando hay 15 números que han salido
     * en algún cartón, este método solo se llama una vez por partida
     * para que solo salga un bingo.
     * @param arr
     * @return
     */
    private static boolean checkBingo(String[][][] arr) {
        int aux;
        for (int x = 0; x < arr.length; x++) {
            aux = 0;
            for (int y = 0; y < arr[x].length; y++) {
                for (int z = 0; z < arr[x][y].length; z++) {
                    if (aux < 15) {
                        if (arr[x][y][z].equals(hyphen)) {
                            aux++;
                        }
                        if (aux == 15) {
                            System.out.println();
                            System.out.printf("%sThere is a BINGO on card %s%s\n", BLUE_BACKGROUND_BRIGHT + BLACK_BOLD, x + 1, ANSI_RESET);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método para buscar los números que van saliendo del bombo
     * y remplazarlos con un guion.
     * @param num
     * @param arr
     * @void Imprime si se tiene el valor buscado, y si lo ha reemplazado
     */
    private static void searchAndReplaceValue(int num, String[][][] arr) {
        int position, aux = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (Arrays.asList(arr[i][j]).contains(String.valueOf(num))) {
                    aux++;
                    position = Arrays.asList(arr[i][j]).indexOf(String.valueOf(num));
                    arr[i][j][position] = hyphen;
                    break;
                }
            }
        }
        if (aux >= 1) System.out.println("You had this number in one of your cards,\nI mark it with a hyphen. " + YELLOW_BACKGROUND_BRIGHT + BLACK_BOLD + "[-]" + ANSI_RESET);

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

    /**
     * Método que genera todos los cartones a través de diferentes
     * métodos como {@link #getRandomValues}, {@link #sortCards},
     * {@link #fillCardsSorted} y {@link #fillCardsAt}
     * @param cards
     * @param cardsColumns
     */
    private static void generateAllCards(String[][][] cards, int[][][] cardsColumns) {
        getRandomValues(cards, cardsColumns);
        sortCards(cards, cardsColumns);
        fillCardsSorted(cards, cardsColumns);
        fillCardsAt(cards);
    }

    /**
     * Método para generar números Random y que no se repitan
     * a través del método {@link #checkRandomNumberBiArr},
     * este método va metiendo todos los números no repetidos
     * en un arreglo de [numero de cartones] x 9 x 3
     * @param cards
     * @param cardsColumns
     */
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

    /**
     * Método para sustituir el array de integers por un array
     * de strings, además el valor que pertenece a la columna x,
     * pasa a pertenecer a la columna y.
     * @param cards
     * @param cardsColumns
     */
    private static void fillCardsSorted(String[][][] cards, int[][][] cardsColumns) {
        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                for (int z = 0; z < cards[x][y].length; z++) {
                    cards[x][y][z] = String.valueOf(cardsColumns[x][z][y]);
                }
            }
        }

    }

    /**
     * Método para ordenar todos los cartones por columnas,
     * para así tener ordenados los cartones de menor a mayor
     * y de arriba a abajo, de izquierda a derecha.
     * @param cards
     * @param cardsColumns
     */
    private static void sortCards(String[][][] cards, int[][][] cardsColumns) {
        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                for (int z = 0; z < cards[x][y].length; z++) {
                    Arrays.sort(cardsColumns[x][z]);
                }
            }
        }
    }

    /**
     * Método utilizado para revisar que un número no se encuentra repetido
     * en un array bi dimensional (de dos dimensiones).
     * Si se encuentra repetido devolverá true, si no false.
     * @param num
     * @param arr
     * @return boolean
     */
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

    /**
     * Método utilizado para revisar que un número no se encuentra repetido
     * en un array uni dimensional (de una dimension).
     * Si se encuentra repetido devolverá true, si no false.
     * @param num
     * @param arr
     * @return boolean
     */
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

    /**
     * Método para sustituir números al azar del cartón
     * por un @, y así darle 4 huecos por fila
     * al cartón, y que sea igual al del juego original.
     * @param arr
     */
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

    /**
     * Método para imprimir los cartones,
     * dentro del método se llama al método {@linkplain  #printColor},
     * para estilizar los cartones.
     * @param arr
     */
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

    /**
     * Método para imprimir por pantalla con colores de fondo
     * en los parámetros se recibe el color de fondo
     * el color del texto, el color de reset
     * y el texto que estará estilizado.
     * @param bgColor
     * @param color
     * @param reset
     * @param text
     */
    private static void printColor(String bgColor, String color, String reset, String text) {
        System.out.printf(bgColor + color + "%2s " + reset, text);
    }
}