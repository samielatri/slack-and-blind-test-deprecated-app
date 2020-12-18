package controller;

import java.util.Scanner;

public class KeybordInput {

    public int readInt(String printable) {
        System.out.println("Please enter " + printable + ": > ");
        Scanner scanner = new Scanner(System.in);
        int intInput = scanner.nextInt();
        return intInput;
    }

    public String readString(String printable) {
        System.out.println("Please enter " + printable + ": > ");
        Scanner scanner = new Scanner(System.in);
        String stringInput = scanner.nextLine();
        return stringInput;
    }

}