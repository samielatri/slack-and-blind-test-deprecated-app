package test.tool;

import tool.DataManipulator;

/**
 *
 */
public class IsValidPasswordTest {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {

        String password1 = "Java2test@";

        boolean validPassword = DataManipulator.isValidPassword(password1);
        System.out.println("Java2test@ is valid password:" + validPassword);

        String password2 = "helloword#123";

        boolean validPassword1 = DataManipulator.isValidPassword(password2);
        // No upper case
        System.out.println("helloword#123 is valid password:" + validPassword1);

    }

}
