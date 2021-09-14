package com.example.alumnos;

import android.widget.EditText;
import android.widget.ImageView;

public class CustomItems {

    String spinnerText;
    int spinnerItem;

    public CustomItems(String spinnerText, int spinnerItem){
        this.spinnerText = spinnerText;
        this.spinnerItem = spinnerItem;
    }

    public String getSpinnerText() {
        return spinnerText;
    }

    public int getSpinnerItem() {
        return spinnerItem;
    }
}
