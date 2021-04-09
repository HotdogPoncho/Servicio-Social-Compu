package com.example.buscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RadioButton optNumero, optNombre, optApellidos;

    EditText txtBuscar;

    ListView lstVisitas;

    String[] visitas = new String[0];

    String fechaYHora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optNumero = findViewById(R.id.optNumero);
        optNombre = findViewById(R.id.optNombre);
        optApellidos = findViewById(R.id.optApellidos);

        txtBuscar = findViewById(R.id.txtBuscar);

        lstVisitas = findViewById(R.id.lstvwProfesores);

        lstVisitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fechaYHora = visitas[position];
            }
        });

    }

    public void modificar(View view){
        Intent modificarDatos = new Intent(MainActivity.this, ActualizarDatos.class);
        modificarDatos.putExtra("fechaYHora", fechaYHora);
        startActivity(modificarDatos);
    }

    public void buscar(View view){
        if(txtBuscar.length() > 0) {
            if (optNumero.isChecked()) {
                opcionBusqueda(1);
            } else if (optNombre.isChecked()) {
                opcionBusqueda(2);
            } else {
                opcionBusqueda(3);
            }
        }else{
            Toast.makeText(this, "Ingrese algo para buscar", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarProfe(String URL, int opcion){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                visitas = new String[response.length()];
                for(int i = 0; i < response.length(); i++){
                    try{
                        jsonObject = response.getJSONObject(i);
                        fechaYHora = jsonObject.getString("fechaYHora");
                        visitas[i] = fechaYHora;
                    }catch(JSONException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                opcionBusqueda(opcion);
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
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, visitas);
        lstVisitas.setAdapter(arrayAdapter);
    }

    public void opcionBusqueda(int opcion){
        switch(opcion){
            case 1:
                buscarProfe("https://enp2saladecomputo.000webhostapp.com/BusquedaNumero.php?numeroDeTrabajador=" + txtBuscar.getText().toString() + "", 1);
                break;

            case 2:
                buscarProfe("https://enp2saladecomputo.000webhostapp.com/BusquedaNombre.php?nombre=" + txtBuscar.getText().toString() + "", 2);
                break;

            case 3:
                buscarProfe("https://enp2saladecomputo.000webhostapp.com/BusquedaApellido.php?apellidos=" + txtBuscar.getText().toString() + "", 3);
                break;

            default:
                Toast.makeText(this, "No se encontró ninguna opción", Toast.LENGTH_SHORT).show();
                break;
        }
        visitas();
    }

}