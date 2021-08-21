package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtNumTrab;
    TextView lblApellidos, lblNombre, lblFecha, lblHora;

    String[] datos = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtNumTrab = findViewById(R.id.txtNumTrab);
        this.lblApellidos = findViewById(R.id.lblApellidos);
        this.lblNombre = findViewById(R.id.lblNombre);
        this.lblFecha = findViewById(R.id.lblFecha);
        this.lblHora = findViewById(R.id.lblHora);
    }

    public void formRegistro(View view){
        Intent registro = new Intent( this, Registro.class);
        startActivity(registro);
    }

    @SuppressLint("SetTextI18n")
    public void IniciarSesion(View view){
        if(txtNumTrab.length() == 0){
            Toast.makeText(this, "Ingresa tu Número de Trabajador", Toast.LENGTH_SHORT).show();
        }else if(txtNumTrab.getText().toString().equals("819423") || txtNumTrab.getText().toString().equals("290702")){
            Intent pantalla = new Intent(MainActivity.this , InterfazAdministrador.class);
            startActivity(pantalla);
        }else{
            String fecha = new Date().toString();
            String[] fechaHora = fecha.split(" ");
            lblFecha.setText(fechaHora[2] + "/" + fechaHora[1] + "/" + fechaHora[5]);
            lblHora.setText(fechaHora[3]);
            buscarProfesor("https://enp2saladecomputo.000webhostapp.com/Profesores/IniciarSesion.php?numeroDeTrabajador="+txtNumTrab.getText().toString()+"");
        }
    }

    public void buscarProfesor(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            JSONObject jsonObject;
            for (int i = 0; i < response.length(); i++) {
                try {
                    jsonObject = response.getJSONObject(i);
                    datos[0] = jsonObject.getString("numeroDeTrabajador");
                    datos[1] = jsonObject.getString("apellidos");
                    datos[2] = jsonObject.getString("nombre");
                    datos[3] = jsonObject.getString("area");
                    datos[4] = jsonObject.getString("colegio");
                    registrarInicio("https://enp2saladecomputo.000webhostapp.com/Profesores/registrarUso.php");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "Usuario no registrado, registrese por favor", Toast.LENGTH_SHORT).show();
            Intent registrarse = new Intent(MainActivity.this, RegistroInvalido.class);
            startActivity(registrarse);
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void registrarInicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
            Intent RegistroExitoso = new Intent(MainActivity.this, RegistroExitoso.class);
            startActivity(RegistroExitoso);
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("numeroDeTrabajador", datos[0]);
                parametros.put("apellidos", datos[1]);
                parametros.put("nombre", datos[2]);
                parametros.put("area", datos[3]);
                parametros.put("colegio", datos[4]);
                parametros.put("fechaYHora", lblFecha.getText().toString() + " - " + lblHora.getText().toString());
                parametros.put("observaciones", "");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}