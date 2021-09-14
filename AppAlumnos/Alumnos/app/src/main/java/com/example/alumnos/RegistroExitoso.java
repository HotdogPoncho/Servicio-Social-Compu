package com.example.alumnos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class RegistroExitoso extends AppCompatActivity {

    Button cmdSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_exitoso);

        cmdSalir = findViewById(R.id.cmdRegresar);

        cmdSalir.setOnClickListener(v -> finishAffinity());

    }
}