package tool;

public enum HomeMenuOptions {
    REGISTRATION(1, "S'inscrire"),
    SIGNIN(2, "Se connecter");

    private int optionNumber;
    private String optionName;

    // constructor
    HomeMenuOptions(int optionNumber, String optionName) {
        this.optionNumber = optionNumber;
        this.optionName = optionName;
    }

    /* accessors */

    // getter for optionNumber
    public int getOptionNumber() {
        return this.optionNumber;
    }

    // getter for optionName
    public String getOptionName() {
        return this.optionName;
    }

    // setter for optionNumber
    private void setOptionNumber(int optionNumber) {
        this.optionNumber = optionNumber;
    }

    // setter for optionNumber
    private void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    /* to String */

    // return string describing the menu option following this pattern : [optionNumber] optionName
    public String toString() {
        return new String("[" + this.optionNumber + "] " + this.optionName);
    }
}
