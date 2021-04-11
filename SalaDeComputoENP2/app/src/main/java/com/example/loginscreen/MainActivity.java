package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtNumTrab;
    TextView lblApellidos, lblNombre, lblFecha, lblHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtNumTrab = (EditText)findViewById(R.id.txtNumTrab);
        this.lblApellidos = (TextView)findViewById(R.id.lblApellidos);
        this.lblNombre = (TextView)findViewById(R.id.lblNombre);
        this.lblFecha = (TextView)findViewById(R.id.lblFecha);
        this.lblHora = (TextView)findViewById(R.id.lblHora);
    }

    public void formRegistro(View view){
        Intent registro = new Intent( this, Registro.class);
        startActivity(registro);
    }

    public void IniciarSesion(View view){
        if(txtNumTrab.length() == 0){
            Toast.makeText(this, "Ingresa tu Número de Trabajador", Toast.LENGTH_SHORT).show();
        }else if(txtNumTrab.getText().toString().equals("290702")){
            Intent pantalla = new Intent(MainActivity.this , BuscarProfesor.class);
            startActivity(pantalla);
        }else{
            String fecha = new Date().toString();
            String[] fechaHora = fecha.split(" ");
            lblFecha.setText(fechaHora[2] + "/" + fechaHora[1] + "/" + fechaHora[5]);
            lblHora.setText(fechaHora[3]);
            buscarProfesor("https://enp2saladecomputo.000webhostapp.com/IniciarSesion.php?numeroDeTrabajador="+txtNumTrab.getText().toString()+"");
        }
    }

    public void buscarProfesor(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtNumTrab.setText(jsonObject.getString("numeroDeTrabajador"));
                        lblApellidos.setText(jsonObject.getString("apellidos"));
                        lblNombre.setText(jsonObject.getString("nombre"));
                        registrarInicio("https://enp2saladecomputo.000webhostapp.com/registrarUso.php");
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Usuario no registrado, registrese por favor", Toast.LENGTH_SHORT).show();
                Intent registrarse = new Intent(MainActivity.this, Registro.class);
                startActivity(registrarse);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void registrarInicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                Intent RegistroExitoso = new Intent(MainActivity.this, RegistroExitoso.class);
                startActivity(RegistroExitoso);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("numeroDeTrabajador", txtNumTrab.getText().toString());
                parametros.put("apellidos", lblApellidos.getText().toString());
                parametros.put("nombre", lblNombre.getText().toString());
                parametros.put("fechaYHora", lblFecha.getText().toString() + " - " + lblHora.getText().toString());
                parametros.put("impresiones", "");
                parametros.put("observaciones", "");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}