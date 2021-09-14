package com.example.alumnos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InterfazAdministrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_administrador);
    }

    public void modificarDatos(View view){
        Intent modificar = new Intent(InterfazAdministrador.this, BuscarAlumnos.class);
        startActivity(modificar);
    }

    public void estadisticas(View view){
        Intent estadistica = new Intent(InterfazAdministrador.this, Estadisticas.class);
        startActivity(estadistica);
    }
}