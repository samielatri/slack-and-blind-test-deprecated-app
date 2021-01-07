package tool;

public class DataManipulator {

    /**
     *
     */
    private DataManipulator(){}

    /**
     * @param email
     * @return true if the email is valid, false if not
     */
    public static boolean isValidEmailAddress(String email){
        // using regex pattern and matchers to verify the email address
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
