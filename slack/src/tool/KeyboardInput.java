package tool;

import java.util.Scanner;

public class KeyboardInput {
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

    /**
     * @param email
     * @return true if the email is valid, false if not
     */
    public boolean isValidEmailAddress(String email){
        // debug print
        System.out.println("Verifying the email address...");
        // using regex pattern and matchers to verify the email address
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher matcher = pattern.matcher(email);

        // debug print
        System.out.println("End of verification of the email address.");

        return matcher.matches();
    }
}
