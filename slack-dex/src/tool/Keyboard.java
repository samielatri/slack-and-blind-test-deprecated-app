package src.tool;

import java.util.Scanner;

public class Keyboard {

    /**
     *
     */
    private Keyboard(){ }

    /**
     *
     * @param
     * @return
     */
    public static int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     *
     * @param printable
     * @return
     */
    public static int readInt(String printable) {
        System.out.println("Please enter " + printable + ": > ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     *
     * @param printable
     * @return
     */
    public static String readString(String printable) {
        System.out.println("Please enter your " + printable + ": > ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     *
     * @param
     * @return
     */
    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
