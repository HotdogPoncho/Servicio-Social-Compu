package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText txtNumTrab, txtApellidos, txtNombre;

    Spinner lstColegio, lstArea;

    String[] Areas = {"Área I: Ciencias Físico - Matemáticas", "Área II: Biológicas y de la Salud", "Área III: Ciencias Sociales", "Área IV: Humanidades y Artes"};

    String[] Area1 = {"Física", "Informática", "Matemáticas"};

    String[] Area2 = {"Biología", "Educación Física", "Morfología, Fisiología y Salud", "Orientación Educativa", "Psicologia e Higiene Mental", "Química"};

    String[] Area3 = {"Ciencias Sociales", "Geografía", "Historia"};

    String[] Area4 = {"Alemán", "Artes Plásticas", "Danza", "Dibujo y Modelado", "Filosofía", "Francés", "Inglés", "Italiano", "Letras Clásicas", "Literatura", "Música", "Teatro"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNumTrab = (EditText) findViewById(R.id.txtNumTrab);
        txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        txtNombre = (EditText) findViewById(R.id.txtNombre);

        lstArea = findViewById(R.id.lstArea);

        ArrayAdapter<String> lstAreas = new ArrayAdapter<>(this, R.layout.spinner_item_textcolor, Areas);
        lstAreas.setDropDownViewResource(R.layout.spinner_item_dropcolor);
        lstArea.setAdapter(lstAreas);
        lstArea.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String seleccion = adapterView.getSelectedItem().toString();
        if(seleccion.equals("Área I: Ciencias Físico - Matemáticas")){
            spinnerOpciones(Area1);
        }else if(seleccion.equals("Área II: Biológicas y de la Salud")){
            spinnerOpciones(Area2);
        }else if(seleccion.equals("Área III: Ciencias Sociales")){
            spinnerOpciones(Area3);
        }else{
            spinnerOpciones(Area4);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void spinnerOpciones(String[] area){
        lstColegio = (Spinner)findViewById(R.id.lstColegio);
        ArrayAdapter<String> lstColegios = new ArrayAdapter<String>(this, R.layout.spinner_item_textcolor, area);
        lstColegios.setDropDownViewResource(R.layout.spinner_item_dropcolor);
        lstColegio.setAdapter(lstColegios);
    }

    public void GuardarDatos(View view){
        if(txtNumTrab.length() == 0){
            Toast.makeText(this, "Ingresa tu numero de trabajador", Toast.LENGTH_SHORT).show();
        }else if(txtApellidos.length() == 0){
            Toast.makeText(this, "Ingresa tus Apellidos", Toast.LENGTH_SHORT).show();
        }else if(txtNombre.length() == 0){
            Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_SHORT).show();
        }else{
            ejecutarServicio("https://enp2saladecomputo.000webhostapp.com/registrarProfesor.php");
            Intent Inicio = new Intent(this, MainActivity.class);
            startActivity(Inicio);
        }

    }

    public void BorrarDatos(View view){
        txtNumTrab.setText("");
        txtApellidos.setText("");
        txtNombre.setText("");
    }

    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro exitoso, intente iniciando sesión", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("numeroDeTrabajador", txtNumTrab.getText().toString());
                parametros.put("apellidos", txtApellidos.getText().toString());
                parametros.put("nombre", txtNombre.getText().toString());
                parametros.put("area", lstArea.getSelectedItem().toString());
                parametros.put("colegio", lstColegio.getSelectedItem().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}