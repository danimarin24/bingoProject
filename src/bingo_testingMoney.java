import java.util.Arrays;

public class bingo_testingMoney {

    public static final String at = "@";
    public static final String hyphen = "—";

    public static boolean finished = true;
    public static int cartonPrice = 50;
    public static int[] gameStats = new int[2];


    /**
     * Método principal
     * @param args
     */
    public static void main(String[] args) {
        // PRINT WELCOME MESSAGE
        getGameInfo();
        gameStats[1] = 200; // initial money bank to $200.
        do {
            if (Util.demanarChar("Do you want to play bingo?", 's', 'n') == 's') {
                System.out.println(); // initial break, to generate a separation in terminal.
                gameStats = game(gameStats[1]); // start game, with initial money bank, assigned before.
                finished = gameStats[0] == 0;
                if (gameStats[0] == 2) { // not enough money
                    System.out.println("Your game has just end. Because you don't have enough money to buy one card!");
                    getActualMoneyInfo(gameStats[1], cartonPrice);
                    System.out.printf("Insufficient founds, min to play: %d\n", cartonPrice);
                    finished = true;
                }
            } else { // if the user input 'n', the game ends
                System.out.println("BYE!! See u soon :D");
                finished = true;
            }
        } while (!finished);
    }

    /**
     * Método para jugar al juego BINGO
     * en este método comienzan todas las llamadas
     * a todos los métodos que hay en esta clase.
     * Este método utiliza internamente estos métodos
     *
     * @method {@link Util#demanarChar}
     * @method {@link Util#demanarIntegerMinMax}
     * @method {@link #generateAllCards}
     * @method {@link #printCards}
     * @method {@link #generateBomboArray}
     * @method {@link #checkLine}
     * @method {@link #searchAndReplaceValue}
     */
    private static int[] game(int actualMoney) {
        int cardsNumber, aux = 0;
        String[][][] cards, clonedCardsArr;
        int[][][] cardsColumns;
        boolean checkLine = false;
        int[] arrBomboNumbers = generateBomboArray(1, 90);
        int[] clonedArrBomboNumbers, gameInfo = new int[2];
        int totalMoneyWon, moneyWon, totalMoneyLess, moneyLessNumber = 5, totalMoney;
        int maxCards = actualMoney / cartonPrice;
        getGameMoneyInfo(actualMoney);

        cardsNumber = Util.demanarIntegerMinMax("Number of cards to be played: ", 1, maxCards);
        actualMoney = actualMoney - (cartonPrice * cardsNumber);
        System.out.printf("Now you have %s$%s%s%d%s, at stake you have %s$%s%s%d%s (%d cards)\n", ConsoleColors.GREEN_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.BLUE_BOLD,actualMoney, ConsoleColors.ANSI_RESET, ConsoleColors.GREEN_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.BLUE_BOLD,cartonPrice * cardsNumber, ConsoleColors.ANSI_RESET, cardsNumber);


        cards = new String[cardsNumber][3][9];
        cardsColumns = new int[cardsNumber][9][3];

        generateAllCards(cards, cardsColumns);
        clonedCardsArr = cloneAllCards(cards);

        // start game with cards generated
        do { // only play if the user input 's', if not the game ends.
            printCards(cards); // print cards every time

            System.out.printf("%sNew number: %d%s\n", ConsoleColors.GREEN_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, arrBomboNumbers[aux], ConsoleColors.ANSI_RESET);

            searchAndReplaceValue(arrBomboNumbers[aux], cards); // change number value with a hyphen every time
            if (!checkLine) {
                checkLine = checkLine(cards); // check line every time, until it is
            }

            if (checkBingo(cards, clonedCardsArr)) { // check bingo every time, until it is
                clonedArrBomboNumbers = clonedArrBomboNumbers(aux, arrBomboNumbers);
                System.out.print("This is the list of numbers that have come out of the draw:");
                printBomboNumbers(clonedArrBomboNumbers, cards);

                moneyWon = 200;
                totalMoneyLess = (aux - 55) < 0 ? 0 : (aux - 55) * moneyLessNumber;
                totalMoneyWon = moneyWon - totalMoneyLess;

                System.out.printf("\nYOU WON %s$%s%s%d%s BECAUSE YOU MADE BINGO ON THE %d BALL\n\n",  ConsoleColors.GREEN_BOLD,ConsoleColors.ANSI_RESET,ConsoleColors.BLUE_BOLD,totalMoneyWon,ConsoleColors.ANSI_RESET, aux);

                gameInfo[0] = 1;
                totalMoney = totalMoneyWon + actualMoney;
                gameInfo[1] = totalMoney;

                if (totalMoney < cartonPrice) {
                    gameInfo[0] = 2; // 2 is for indicate that this user can't play because he/she doesn't have enough money.
                }
                return gameInfo;
            }

            // incrementar posición del nuevo número del bombo;
            aux++;
        }while (Util.demanarChar("Next Number", 's', 'n') == 's');
        return gameInfo;
    }

    private static void printBomboNumbers(int[] clonedArrBomboNumbers, String[][][] arr) {
        for (int i = 0; i < clonedArrBomboNumbers.length; i++) {
            System.out.printf(i % 12 == 0 ? "\n %2d | " : "%2d | ", clonedArrBomboNumbers[i], clonedArrBomboNumbers[i]);
        }
        //System.out.println(Arrays.toString(clonedArrBomboNumbers));
    }

    private static void getGameMoneyInfo(int money) {
        System.out.printf("""
                        %s╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋%s You have %s$%s%s%d%s money.
                        %s╋╋%s%s╋╋╋ %s%sBANCO%s %s╋╋╋%s%s╋╋%s %s╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋%s
                        %s╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋%s Each card cost %s$%s%s%d%s.
                        """,
                ConsoleColors.CYAN_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.GREEN_BOLD,ConsoleColors.ANSI_RESET,ConsoleColors.BLUE_BOLD,money,ConsoleColors.ANSI_RESET,
                ConsoleColors.CYAN_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.RED_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.YELLOW_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.RED_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.CYAN_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.CYAN_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.CYAN_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.GREEN_BOLD,ConsoleColors.ANSI_RESET,ConsoleColors.RED_BOLD,cartonPrice,ConsoleColors.ANSI_RESET
        );
        getBingoInfo();
    }

    private static void getActualMoneyInfo(int money, int cartonPrice) {
        System.out.printf("You have %s$%s%s%d%s money. || Each card cost %s$%s%s%d%s\n", ConsoleColors.GREEN_BOLD,ConsoleColors.ANSI_RESET,ConsoleColors.BLUE_BOLD,money,ConsoleColors.ANSI_RESET, ConsoleColors.GREEN_BOLD,ConsoleColors.ANSI_RESET,ConsoleColors.RED_BOLD,cartonPrice,ConsoleColors.ANSI_RESET);

    }

    private static void getBingoInfo() {
        System.out.printf("""
                        %s```%s
                           %sIf you don't BINGO before 55 numbers have been displayed,%s
                           %syou lose %s%s $ %s%s 5 %s%s of the prize for each number displayed %s
                           %safter that. %s%sTHE PRIZE IS %s%s $ %s%s 200 %s%s IF YOU WON. %s
                        %s```%s
                        """,
                ConsoleColors.RED_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.GREEN_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.RED_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.PURPLE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.GREEN_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.BLUE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.PURPLE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.RED_BOLD, ConsoleColors.ANSI_RESET
        );
    }

    private static int[] clonedArrBomboNumbers(int aux, int[] arrBomboNumbers) {
        int[] clonedArrBomboNumbers = new int[aux+1];
        System.arraycopy(arrBomboNumbers, 0, clonedArrBomboNumbers, 0, clonedArrBomboNumbers.length);
        return clonedArrBomboNumbers;
    }

    private static String[][][] cloneAllCards(String[][][] arr) {
        String[][][] clonedArr = new String[arr.length][arr[0].length][arr[0][0].length];
        for (int x = 0; x < clonedArr.length; x++) {
            for (int y = 0; y < clonedArr[x].length; y++) {
                System.arraycopy(arr[x][y], 0, clonedArr[x][y], 0, clonedArr[x][y].length);
            }
        }
        return clonedArr;
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
                ConsoleColors.CYAN_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.RED_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.YELLOW_BOLD, ConsoleColors.ANSI_RESET, ConsoleColors.RED_BOLD, ConsoleColors.ANSI_RESET,
                ConsoleColors.CYAN_BOLD, ConsoleColors.ANSI_RESET
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
                            System.out.printf("%sThere is a LINE on card %s%s\n", ConsoleColors.BLUE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, x + 1, ConsoleColors.ANSI_RESET);
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
    private static boolean checkBingo(String[][][] arr, String[][][] clonedArr) {
        int aux, auxBingo = 0;
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
                            System.out.printf("%sThere is a BINGO on card %s%s\n", ConsoleColors.BLUE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD, x + 1, ConsoleColors.ANSI_RESET);
                            printCard(clonedArr[x]);
                            auxBingo++;
                        }
                    }
                }
            }
        }
        return auxBingo > 0;
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
        if (aux >= 1) System.out.println("You had this number in one of your cards,\nI mark it with a hyphen. " + ConsoleColors.YELLOW_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD + "[-]" + ConsoleColors.ANSI_RESET);

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
        int counter, atPosition;

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

        for (int x = 0; x < arr.length; x++) {
            System.out.printf("%4s ", (x+1) + ".");
            for (int y = 0; y < arr[x].length; y++) {
                for (int z = 0; z < arr[x][y].length; z++){
                    if (y == 1 && z == 0) System.out.printf("%4s ", ' ');
                    if (y == 2 && z == 0) System.out.printf("%4s ", ' ');
                    if (arr[x][y][z].equals(at)) {
                        printColor(ConsoleColors.WHITE_BACKGROUND_BRIGHT, ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET, arr[x][y][z]);
                    } else if (arr[x][y][z].equals(hyphen)) {
                        printColor(ConsoleColors.YELLOW_BACKGROUND_BRIGHT, ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET, arr[x][y][z]);
                    } else {
                        printColor(ConsoleColors.RED_BACKGROUND_BRIGHT, ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET, arr[x][y][z]);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Método para imprimir los cartones ganadores de bingo,
     * en vez de recibir un array tridimensional, lo recibe
     * bidimensional, ya que solo va a mostrar el resultado de un cartón
     * por bingo ganador, en el caso de que haya más de uno.
     * Dentro del método se llama al método {@linkplain  #printColor},
     * para estilizar los cartones.
     * @param arr
     */
    private static void printCard(String[][] arr) {
        System.out.println();

        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[x].length; y++) {
                if (arr[x][y].equals(at)) {
                    printColor(ConsoleColors.WHITE_BACKGROUND_BRIGHT, ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET, arr[x][y]);
                } else if (arr[x][y].equals(hyphen)) {
                    printColor(ConsoleColors.YELLOW_BACKGROUND_BRIGHT, ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET, arr[x][y]);
                } else {
                    printColor(ConsoleColors.RED_BACKGROUND_BRIGHT, ConsoleColors.BLACK_BOLD, ConsoleColors.ANSI_RESET, arr[x][y]);
                }
            }
            System.out.println();
        }
        System.out.println();
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