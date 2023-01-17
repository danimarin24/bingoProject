import java.util.Scanner;

public class Util {
    public static int demanarInteger(String frase){
        int num;
        Scanner in = new Scanner(System.in);
        System.out.print(frase);
        while(!in.hasNextInt()){
            in.next();
            System.out.print("Error! Introdueix un valor enter: ");
        }
        num=in.nextInt();
        return num;
    }
    public static int demanarIntegerMinMax(String frase, int min, int max){
        int num;
        Scanner in = new Scanner(System.in);
        System.out.print(frase);
        do {
            while(!in.hasNextInt()){
                in.next();
                System.out.print("Error! Introdueix un valor enter: ");
            }num=in.nextInt();
            if (num<min || num>max) {
                System.out.printf("Error! Introdueix un valor entre %d i %d: ",min,max);
            }
        }while (num<min || num>max);
        return num;
    }
    public static double demanarDouble(String frase){
        double num;
        Scanner in = new Scanner(System.in);
        System.out.print(frase);
        while(!in.hasNextDouble()){
            in.next();
            System.out.print("Error! Introdueix un valor decimal: ");
        }
        num=in.nextInt();
        return num;
    }
    public static char demanarChar(char c1, char c2){
        Scanner in = new Scanner(System.in);
        System.out.print("Següent número (s/n)?: ");
        char caracter = in.next().charAt(0);
        caracter=Character.toLowerCase(caracter);
        while(caracter !=c1 && caracter != c2){
            System.out.printf("Error, introdueix %c/%c: ",c1,c2);
            caracter= in.next().charAt(0);
            caracter=Character.toLowerCase(caracter);
        }
        return caracter;
    }
}