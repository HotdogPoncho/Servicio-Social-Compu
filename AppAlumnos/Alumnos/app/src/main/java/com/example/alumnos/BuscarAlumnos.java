package com.example.alumnos;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuscarAlumnos extends AppCompatActivity {

    TextView txtApellidos;

    RadioButton optAlumnosRegistrados, optVisitas;

    Button cmdBuscar, cmdModificar;

    ListView lstVisitas;

    String[] datos = new String[0];
    String[] nombres = new String[0];
    String[] datoBusqueda = new String[0];

    String busqueda = "";

    boolean control = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_alumnos);

        inicializar();

    }

    public void inicializar(){
        busquedaControl("https://enp2saladecomputo.000webhostapp.com/Alumnos/BusquedaInicial.php");
        txtApellidos = findViewById(R.id.txtBuscar);
        optAlumnosRegistrados = findViewById(R.id.optAlumnos);
        optVisitas = findViewById(R.id.optVisitas);
        cmdBuscar = findViewById(R.id.cmdBuscar);
        cmdModificar = findViewById(R.id.cmdModificar);
        lstVisitas = findViewById(R.id.lstAlumnos);

        cmdBuscar.setOnClickListener(v -> {
            if(optAlumnosRegistrados.isChecked()){
                if(txtApellidos.length() == 0){
                    busquedaRegistros("https://enp2saladecomputo.000webhostapp.com/Alumnos/BusquedaTodosAlumnosRegistrados.php");
                }else{
                    busquedaRegistros("https://enp2saladecomputo.000webhostapp.com/Alumnos/BusquedaAlumnosRegistrados.php?apellidos=" + txtApellidos.getText().toString());
                }
                control = false;
            }else{
                if(txtApellidos.length() == 0){
                    busquedaControl("https://enp2saladecomputo.000webhostapp.com/Alumnos/BusquedaInicial.php");
                } else {
                    busquedaControl("https://enp2saladecomputo.000webhostapp.com/Alumnos/BusquedaAlumnoUso.php?apellidos=" + txtApellidos.getText().toString());
                }
                control = true;
            }
        });

       cmdModificar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ModificarDatosUso.class);
            if(!control) {
                intent = new Intent(getApplicationContext(), ModificarDatosAlumno.class);
            }
            intent.putExtra("busqueda", busqueda);
            startActivity(intent);
        });

        lstVisitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                busqueda = datoBusqueda[position];
            }
        });
    }

    public void busquedaControl(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            JSONObject jsonObject;
            nombres = new String[response.length()];
            datos = new String[response.length()];
            datoBusqueda = new String[response.length()];
            for(int i = 0; i < response.length(); i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    nombres[i] = jsonObject.getString("nombre") + " " + jsonObject.getString("apellidos") + " - " + jsonObject.getString("grupo") + "";
                    datos[i] = jsonObject.getString("fechaYHora");
                    datoBusqueda[i] = jsonObject.getString("fechaYHora");
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            lista();
            control = true;
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void busquedaRegistros(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            JSONObject jsonObject;
            nombres = new String[response.length()];
            datos = new String[response.length()];
            datoBusqueda = new String[response.length()];
            for(int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    nombres[i] = jsonObject.getString("apellidos") + " " + jsonObject.getString("nombre");
                    datos[i] = jsonObject.getString("grupo");
                    datoBusqueda[i] = jsonObject.getString("numeroDeCuenta");
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            lista();
            control = false;
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void lista(){
        CustomAdapterListView customAdapterListView = new CustomAdapterListView(this, nombres, datos);
        lstVisitas.setAdapter(customAdapterListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inicializar();
    }

}