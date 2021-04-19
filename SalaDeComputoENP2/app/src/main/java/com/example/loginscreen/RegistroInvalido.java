package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistroInvalido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_invalido);
    }

    public void Registrarse(View view){
        Intent registrarse = new Intent(RegistroInvalido.this, Registro.class);
        startActivity(registrarse);
    }
}