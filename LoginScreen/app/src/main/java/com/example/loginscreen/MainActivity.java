package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    EditText txtNumTrab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNumTrab = (EditText)findViewById(R.id.txtNumTrab);
    }

    public void formRegistro(View view){
        Intent registro = new Intent( this, Registro.class);
        startActivity(registro);
    }

    public void IniciarSesion(View view){
        if(txtNumTrab.length() == 0){
            Toast.makeText(this, "Ingresa tu Número de Trabajador", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Inicio Registrado", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarProfesor(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        })
    }
}