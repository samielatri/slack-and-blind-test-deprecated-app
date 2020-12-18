package controller;

public enum ReadableType {
    INT(1),
    STRING(2);
    /* accessors */
    private int typeValue;

    // constructor
     ReadableType(int typeValue){
        this.typeValue = typeValue;
    }

    // getter for optionNumber
    public int getTypeValue(){
        return this.typeValue;
    }


    // setter for optionNumber
    private void setTypeValue(int optionNumber){
        this.typeValue = optionNumber ;
    }



    /* to String */

    // return string describing the menu option following this pattern : [optionNumber] optionName
    public String toString(){
        return new String("[" + this.typeValue + "] ");
    }
}