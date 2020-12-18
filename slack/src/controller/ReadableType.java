public enum ReadableType {
    INT(1);
    STRING(2);
    /* accessors */


    // constructor
    HomeMenuOptions(int optionNumber){
        this.optionNumber = optionNumber ;
    }

    // getter for optionNumber
    public int getOptionNumber(){
        return this.optionNumber ;
    }


    // setter for optionNumber
    private void setOptionValue(int optionNumber){
        this.optionNumber = optionNumber ;
    }

    // setter for optionNumber
    private void setOptionName(String optionName){
        this.optionName = optionName ;
    }

    /* to String */

    // return string describing the menu option following this pattern : [optionNumber] optionName
    public String toString(){
        return new String("[" + this.optionNumber + "] " + this.optionName);
    }
}