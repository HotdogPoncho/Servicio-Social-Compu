package com.example.alumnos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegistroInvalido extends AppCompatActivity {

    Button cmdRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_invalido);

        cmdRegistrarse = findViewById(R.id.cmdRegistro);

        cmdRegistrarse.setOnClickListener(v -> intent());

    }

    public void intent(){
        Intent intent = new Intent(RegistroInvalido.this, RegistroAlumnos.class);
        startActivity(intent);
    }
}