package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String area, colegio;

    EditText txtNumTrab, txtApellidos, txtNombre;

    Spinner lstColegio, lstArea;

    String[] Areas = {"Area I: Ciencias Fisico - Matematicas", "Area II: Biologicas y de la Salud", "Area III: Ciencias Sociales", "Area IV: Humanidades y Artes"};

    String[] Area1 = {"Fisica", "Informatica", "Matematicas"};

    String[] Area2 = {"Biologia", "Educacion Fisica", "Morfologia, Fisiologia y Salud", "Orientacion Educativa", "Psicologia e Higiene Mental", "Quimica"};

    String[] Area3 = {"Ciencias Sociales", "Geografia", "Historia"};

    String[] Area4 = {"Aleman", "Artes Plasticas", "Danza", "Dibujo y Modelado", "Filosofia", "Frances", "Ingles", "Italiano", "Letras Clasicas", "Literatura", "Musica", "Teatro"};

    int[] itemAreas = {R.drawable.ic_math, R.drawable.ic_area2, R.drawable.ic_ciencias_sociales, R.drawable.ic_area4};

    int[] itemArea1 = {R.drawable.ic_fisica, R.drawable.ic_informatica, R.drawable.ic_matematicas};

    int[] itemArea2 = {R.drawable.ic_bacterias, R.drawable.ic_deporte, R.drawable.ic_fisiologia, R.drawable.ic_exito, R.drawable.ic_psicologia, R.drawable.ic_quimica};

    int[] itemArea3 = {R.drawable.ic_ciencias_sociales, R.drawable.ic_geografia, R.drawable.ic_libro};

    int[] itemArea4 = {R.drawable.ic_aleman, R.drawable.ic_escultura, R.drawable.ic_danza, R.drawable.ic_bosquejo, R.drawable.ic_filosofia, R.drawable.ic_frances, R.drawable.ic_english, R.drawable.ic_italiano, R.drawable.ic_letras, R.drawable.ic_literatura, R.drawable.ic_musica, R.drawable.ic_teatro};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNumTrab = findViewById(R.id.txtNumTrab);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtNombre = findViewById(R.id.txtNombre);

        lstArea = findViewById(R.id.lstArea);

        ArrayList<CustomItems> customList = new ArrayList<>();

       for(int i = 0; i < Areas.length; i++){
           customList.add(new CustomItems(Areas[i], itemAreas[i]));
       }

        CustomAdapter customAdapter = new CustomAdapter(this, customList);

        lstArea.setAdapter(customAdapter);
        lstArea.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        CustomItems items = (CustomItems)adapterView.getSelectedItem();
        area = items.getSpinnerText();
        switch(area) {
            case "Area I: Ciencias Fisico - Matematicas":
                spinnerOpciones(Area1, itemArea1);
                break;
            case "Area II: Biologicas y de la Salud":
                spinnerOpciones(Area2, itemArea2);
                break;
            case "Area III: Ciencias Sociales":
                spinnerOpciones(Area3, itemArea3);
                break;
            default:
                spinnerOpciones(Area4, itemArea4);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void spinnerOpciones(String[] area, int[] items){
        lstColegio = findViewById(R.id.lstColegio);

        ArrayList<CustomItems> customItemsArrayList = new ArrayList<>();
        for(int i = 0; i < area.length; i++){
            customItemsArrayList.add(new CustomItems(area[i], items[i]));
        }
        CustomAdapter customAdapter = new CustomAdapter(this, customItemsArrayList);
        lstColegio.setAdapter(customAdapter);
        lstColegio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                CustomItems itemsColegio = (CustomItems)adapterView.getSelectedItem();
                colegio = itemsColegio.getSpinnerText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void GuardarDatos(View view){
        if(txtNumTrab.length() == 0){
            Toast.makeText(this, "Ingresa tu numero de trabajador", Toast.LENGTH_SHORT).show();
        }else if(txtApellidos.length() == 0){
            Toast.makeText(this, "Ingresa tus Apellidos", Toast.LENGTH_SHORT).show();
        }else if(txtNombre.length() == 0){
            Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_SHORT).show();
        }else{
            ejecutarServicio();
            Intent Inicio = new Intent(this, MainActivity.class);
            startActivity(Inicio);
        }
    }

    public void BorrarDatos(View view){
        txtNumTrab.setText("");
        txtApellidos.setText("");
        txtNombre.setText("");
    }


    private void ejecutarServicio(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://enp2saladecomputo.000webhostapp.com/Profesores/registrarProfesor.php", response -> Toast.makeText(getApplicationContext(), "Registro exitoso, intente iniciando sesión", Toast.LENGTH_SHORT).show(), error -> {

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("numeroDeTrabajador", txtNumTrab.getText().toString().toUpperCase());
                parametros.put("apellidos", txtApellidos.getText().toString().toUpperCase());
                parametros.put("nombre", txtNombre.getText().toString().toUpperCase());
                parametros.put("area", area.toUpperCase());
                parametros.put("colegio", colegio.toUpperCase());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}