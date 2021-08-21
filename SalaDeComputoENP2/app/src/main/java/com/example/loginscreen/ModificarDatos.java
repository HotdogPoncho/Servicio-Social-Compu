package com.example.loginscreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModificarDatos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String fechaYHora;

    EditText txtNumero, txtApellidos, txtNombre, txtfechaYHora, txtImpresiones, txtObservaciones;

    Spinner cboConceptos;

    String[] conceptos = {"1er Avance Programático", "2do Avance Programático", "3er Avance Programático", "Informe Anual", "Proyecto Anual","Calificaciones 1er Trimestre", "Calificaciones 2do Trimestre", "Calificaciones 3er Trimestre", "Calificaciones Finales", "Asuntos Personales", "Rectificaciones de Calificaiones"};

    String concepto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos);
        txtNumero = findViewById(R.id.txtNumeroDeTrabajador);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtNombre = findViewById(R.id.txtNombre);
        txtfechaYHora = findViewById(R.id.txtfechaYHora);
        txtImpresiones = findViewById(R.id.txtImpresiones);
        txtObservaciones = findViewById(R.id.txtObservaciones);

        cboConceptos = findViewById(R.id.cboConceptos);

        fechaYHora = getIntent().getStringExtra("fechaYHora");

        ArrayList<CustomItems> customItemsArrayList = new ArrayList<>();

        for(String concepto : conceptos){
            customItemsArrayList.add(new CustomItems(concepto, R.drawable.ic_concepto));
        }

        CustomAdapter customAdapter = new CustomAdapter(this, customItemsArrayList);

        cboConceptos.setAdapter(customAdapter);
        cboConceptos.setOnItemSelectedListener(this);

        buscarVisita("https://enp2saladecomputo.000webhostapp.com/Profesores/BuscarDatos.php?fechaYHora=" + fechaYHora + "");
    }

    public void cerrar(View view){
        finishAffinity();
    }

    public void actualizar(View view){
        actualizarDatos("https://enp2saladecomputo.000webhostapp.com/Profesores/Actualizar.php?fechaYHora=" + fechaYHora + "");
    }

    public void eliminar(View view){
        eliminarDatos("https://enp2saladecomputo.000webhostapp.com/Profesores/EliminarRegistro.php?fechaYHora=" + fechaYHora +"");
    }

    public void actualizarDatos(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> Toast.makeText(getApplicationContext(), "Actualización exitosa", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("impresiones", txtImpresiones.getText().toString());
                parametros.put("conceptoDeImpresion", concepto);
                parametros.put("observaciones", txtObservaciones.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void buscarVisita(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            JSONObject jsonObject;
            for(int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    txtNumero.setText(jsonObject.getString("numeroDeTrabajador"));
                    txtApellidos.setText(jsonObject.getString("apellidos"));
                    txtNombre.setText(jsonObject.getString("nombre"));
                    txtfechaYHora.setText(jsonObject.getString("fechaYHora"));

                    if(!jsonObject.getString("impresiones").equals("")){
                        txtImpresiones.setText(jsonObject.getString("impresiones"));
                    }
                    if(!jsonObject.getString("observaciones").equals("")){
                        txtObservaciones.setText(jsonObject.getString("observaciones"));
                    }

                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void eliminarDatos(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            Toast.makeText(getApplicationContext(), "Eliminacion exitosa", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ModificarDatos.this, BuscarProfesor.class);
            startActivity(intent);
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("fechaYHora", txtfechaYHora.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        concepto = conceptos[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}