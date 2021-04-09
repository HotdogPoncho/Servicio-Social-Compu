package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscarProfesor extends AppCompatActivity {

    String[] datos = new String[7];

    String[] profesores = new String[0];

    String indice,numeroDeTrabajador,apellidos,nombre,fechaYHora,impresiones,observaciones;

    ListView lstvwProfesores;

    RadioButton optNumero, optNombre, optApellidos;

    EditText txtBuscar;

    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_profesor);

        optNumero = (RadioButton)findViewById(R.id.optNumero);
        optNombre = (RadioButton)findViewById(R.id.optNombre);
        optApellidos = (RadioButton)findViewById(R.id.optApellidos);

        txtBuscar = (EditText)findViewById(R.id.txtBuscar);

        sp = (Spinner)findViewById(R.id.spinner);


        lstvwProfesores = (ListView)findViewById(R.id.lstvwProfesores);
        lstvwProfesores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buscarDatos("https://enp2saladecomputo.000webhostapp.com/BuscarDatos.php?índice="+indice);
            }
        });

    }

    public void verVisitas(View view){
        if (txtBuscar.length() > 0) {
            if (optNumero.isChecked() == true) {
                buscarProfesor("https://enp2saladecomputo.000webhostapp.com/BusquedaNumero.php?numeroDeTrabajador=" + txtBuscar.getText().toString() + "");
                visitas();
            } else if (optNombre.isChecked() == true) {
                buscarProfesor("https://enp2saladecomputo.000webhostapp.com/BusquedaNombre.php?nombre=" + txtBuscar.getText().toString() + "");
                visitas();
            } else {
                buscarProfesor("https://enp2saladecomputo.000webhostapp.com/BusquedaApellido.php?apellidos=" + txtBuscar.getText().toString() + "");
                visitas();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Ingresa algo para buscar", Toast.LENGTH_SHORT).show();
        }
    }


    public void buscarProfesor(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                profesores = new String[response.length()];
                for(int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        numeroDeTrabajador = jsonObject.getString("numeroDeTrabajador");
                        fechaYHora = jsonObject.getString("fechaYHora");
                        profesores[i] = fechaYHora;
                    }catch(JSONException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void visitas(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, profesores);
        lstvwProfesores.setAdapter(arrayAdapter);
    }


    public void buscarDatos(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for(int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        apellidos = jsonObject.getString("apellidos");
                        nombre = jsonObject.getString("nombre");
                        impresiones = jsonObject.getString("impresiones");
                        observaciones = jsonObject.getString("observaciones");
                        indice = jsonObject.getString("índice");
                    }catch(JSONException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void mandarDatos(View view){
        Toast.makeText(getApplicationContext(), "Se mandaron con exito", Toast.LENGTH_SHORT).show();
        Intent modificar = new Intent(BuscarProfesor.this, ModificarDatos.class);
        modificar.putExtra("numeroDeTrabajador", numeroDeTrabajador + "" );
        modificar.putExtra("apellidos",  apellidos + "");
        modificar.putExtra("nombre", nombre + "");
        modificar.putExtra("fechaYHora", fechaYHora + "");
        modificar.putExtra("impresiones", impresiones + "");
        modificar.putExtra("observaciones", observaciones + "");
        startActivity(modificar);
    }

}