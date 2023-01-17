import java.util.Arrays;
import java.util.Scanner;


public class bingo_testing {
    public static final String at = "@";


    // Metode principal
    public static void main(String[] args) {

        int cardsNumber = Util.demanarIntegerMinMax("Numero de cartons: ", 1, 99);;

        String[][][] cards = new String[cardsNumber][3][9];
        int[][][] cardsColumns = new int[cardsNumber][9][3];

        System.out.println("ejecutandose");
        getRandomValues(cards, cardsColumns);
        System.out.println("ejecutandose");
        sortCards(cards, cardsColumns);
        System.out.println("ejecutandose");
        fillCardsSorted(cards, cardsColumns);
        System.out.println("ejecutandose");
        fillCardsAt(cards);
        System.out.println("ejecutandose");
        printCards(cards);
        System.out.println("ejecutandose");
    }

    private static void getRandomValues(String[][][] cards, int[][][] cardsColumns) {
        int aux, minRange, maxRange, range, randomNum;
        boolean checked = false;
        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                aux = 1;
                for (int z = 0; z < cards[x][y].length; z++) {
                    maxRange = (aux <= 8) ? (aux * 10 - 1) : (aux * 10);
                    minRange = (aux == 1) ? 1 : (aux * 10 - 10);
                    range = maxRange - minRange + 1;
                    do {
                        randomNum = (int)(Math.random() * range) + minRange;
                        //System.out.printf("z = %d || min = %d || max = %d || rango = %d || random = %d \n", z, minRange, maxRange, range, randomNum);

                        checked = checkRandomNumberInt(randomNum, cardsColumns[x]);
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

    private static boolean checkRandomNumberInt(int num, int[][] arr) {
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
        System.out.println("CARTONES");
        // IMPRIMIR LOS CARTONES
        for (String[][] strings : arr) {
            for (String[] string : strings) {
                for (String s : string) {
                    System.out.printf("%2s ", s);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}