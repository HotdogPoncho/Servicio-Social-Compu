package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtNumTrab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNumTrab = (EditText)findViewById(R.id.txtNumTrab);
    }

    public void formRegistro(View view){
        Intent registro = new Intent(MainActivity.this, Registro.class);
        MainActivity.this.startActivity(registro);
    }
}