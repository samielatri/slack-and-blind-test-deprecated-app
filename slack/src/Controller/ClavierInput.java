
public class ClavierInput {

    public void printRead(String printable, int type){
        System.out.println("Please enter " + printable + ": > ");
        switch (type){
            case 1: // int
                readInt();
                break;
            case 2: // String
                readLine();
                break;
        }
    }
    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return choice;
    }

    public int readString() {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        return string;
    }
}