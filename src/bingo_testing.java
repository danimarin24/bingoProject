import java.util.Arrays;
import java.util.Scanner;

public class bingo_testing {
    public static final String at = "@";

    // Metode principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        int randomNum;
        String stringRandomNum;
        int cardsNumber = 3;

        String[][][] cards = new String[cardsNumber][3][9];
        int[][] cardV2 = new int[cardsNumber][27];

        boolean checked = false;

        System.out.println("ejecutandose");
        int aux;
        for (int x = 0; x < cards.length; x++) {
            for (int y = 0; y < cards[x].length; y++) {
                aux = 1;
                for (int z = 0; z < cards[x][y].length; z++) {
                    System.out.println(z);
                    int max = 10;
                    int min = 1;
                    int range = max - min + 1;
                    int rand = (int)(Math.random() * range) + min;
                    System.out.println(rand);
                    System.out.println((int) (Math.random()*9+(aux*10)));
                    aux++;

                    /*
                    do {
                        randomNum = (int) (Math.random()*90+1);
                        stringRandomNum = convertIntToString(randomNum);
                        checked = checkRandomNumber(stringRandomNum, cards[x]);
                    } while (checked);
                    cards[x][y][z] = stringRandomNum;

                    System.out.printf("%3s", cards[x][y][z]);
                     */
                }
            }
        }



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
        System.out.println("CARTONES");
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