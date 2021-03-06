package com.example.alumnos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button iniciarSesion, registrarse;

    EditText numeroDeCuenta;

    String[] datos = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

    }

    public void inicializar(){
        iniciarSesion = findViewById(R.id.cmdIniciarSesion);
        registrarse = findViewById(R.id.cmdRegistrarse);
        numeroDeCuenta = findViewById(R.id.txtNumeroDeCuenta);

        registrarse.setOnClickListener(v -> intents(1));

        iniciarSesion.setOnClickListener(v -> {
            if(numeroDeCuenta.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Ingresa un número de cuenta", Toast.LENGTH_SHORT).show();
                numeroDeCuenta.requestFocus();
            }else if(numeroDeCuenta.getText().toString().equals("819423") || numeroDeCuenta.getText().toString().equals("290702")) {
                Intent pantalla = new Intent(MainActivity.this, InterfazAdministrador.class);
                startActivity(pantalla);
            }else{
                buscarAlumno(numeroDeCuenta.getText().toString());
            }
        });

    }

    public void intents(int Opcion){
        switch (Opcion){
            case 1:
                Intent registroAlumno = new Intent(MainActivity.this, RegistroAlumnos.class);
                startActivity(registroAlumno);
                break;
            case 2:
                Intent registroInvalido = new Intent(MainActivity.this, RegistroInvalido.class);
                startActivity(registroInvalido);
                break;
        }

    }

    public void buscarAlumno(String numero){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://enp2saladecomputo.000webhostapp.com/Alumnos/IniciarSesion.php?numeroDeCuenta=" + numero, response -> {
            JSONObject jsonObject;
            for(int i = 0; i < response.length(); i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    datos[0] = jsonObject.getString("numeroDeCuenta");
                    datos[1] = jsonObject.getString("apellidos");
                    datos[2] = jsonObject.getString("nombre");
                    datos[3] = jsonObject.getString("grupo");
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            registrarUso();
        }, error -> {
            Toast.makeText(getApplicationContext(), "Usuario no registrado, registrese por favor", Toast.LENGTH_SHORT).show();
            intents(2);
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void registrarUso(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://enp2saladecomputo.000webhostapp.com/Alumnos/RegistrarUso.php", response -> {
            Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
            Intent RegistroExitoso = new Intent(MainActivity.this, RegistroExitoso.class);
            startActivity(RegistroExitoso);
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("numeroDeCuenta", datos[0]);
                parametros.put("apellidos", datos[1]);
                parametros.put("nombre", datos[2]);
                parametros.put("grupo", datos[3]);
                parametros.put("fechaYHora", fecha());
                parametros.put("conceptoDeImpresion", "");
                parametros.put("observaciones", "");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String fecha(){
        String fecha = new Date().toString();
        String[] fechaHora = fecha.split(" ");

        return fechaHora[2] + "/" + fechaHora[1] + "/" + fechaHora[5] + " - " + fechaHora[3];
    }

}