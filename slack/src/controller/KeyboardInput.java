package controller;

public class ClavierInput {

    public void printRead(String printable, int type){
        System.out.println("Please enter " + printable + ": > ");
        switch (type) {
            case 1: // int
                readInt();
                break;
            case 2: // String
                readString();
                break;
        }
    }

    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        int intInput = scanner.nextInt();
        return intInput;
    }

    public String readString() {
        Scanner scanner = new Scanner(System.in);
        String stringInput = scanner.nextLine();
        return stringInput;
    }
}