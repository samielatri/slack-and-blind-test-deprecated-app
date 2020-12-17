class ClavierInput {
    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return choice;
    }

    public int readString() {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        return choice;
    }
}