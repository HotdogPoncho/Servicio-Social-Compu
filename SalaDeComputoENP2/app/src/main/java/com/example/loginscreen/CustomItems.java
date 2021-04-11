package com.example.loginscreen;

public class CustomItems {

    private String spinnerText;
    private int spinnerItem;


    public CustomItems(String spinnerText, int spinnerItem) {
        this.spinnerText = spinnerText;
        this.spinnerItem = spinnerItem;
    }

    public String getSpinnerText(){
        return  spinnerText;
    }

    public int getSpinnerItem(){
        return spinnerItem;
    }

}
