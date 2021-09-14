package com.example.alumnos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class ModificarDatosUso extends AppCompatActivity {

    EditText txtNumeroDeCuenta, txtApellidos, txtNombre, txtFechaYHora, txtImpresiones, txtObservaciones;

    Button cmdActualizar, cmdEliminar, cmdRegresar;

    Spinner cboConceptos;

    String fechaYHora;

    String concepto = "";

    String[] conceptos = {"Calificaciones Primer Parcial", "Calificaciones Segundo Parcial", "Calificaciones Tercer Parcial"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_uso);
        inicializar();
    }

    public void inicializar(){

        txtNumeroDeCuenta = findViewById(R.id.txtNumeroDeCuentaU);
        txtApellidos = findViewById(R.id.txtApellidosU);
        txtNombre = findViewById(R.id.txtNombreU);
        txtFechaYHora = findViewById(R.id.txtfechaYHoraU);
        txtImpresiones = findViewById(R.id.txtImpresionesU);
        txtObservaciones = findViewById(R.id.txtObservacionesU);
        cmdActualizar = findViewById(R.id.cmdActualizarU);
        cmdEliminar = findViewById(R.id.cmdEliminarU);
        cmdRegresar = findViewById(R.id.cmdModificarU);
        cboConceptos = findViewById(R.id.cboConceptosU);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, conceptos);
        cboConceptos.setAdapter(arrayAdapter);

        fechaYHora = getIntent().getStringExtra("busqueda");

        buscarDatos();

        cboConceptos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                concepto = conceptos[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cmdActualizar.setOnClickListener(v -> actualizarDatos());

        cmdEliminar.setOnClickListener(v -> eliminarDatos("https://enp2saladecomputo.000webhostapp.com/Alumnos/EliminarRegistroUso.php?fechaYHora=" + fechaYHora + ""));

        cmdRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(ModificarDatosUso.this, BuscarAlumnos.class);
            startActivity(intent);
        });

    }

    public void buscarDatos(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://enp2saladecomputo.000webhostapp.com/Alumnos/BusquedaAlumnoUsoFecha.php?fechaYHora=" + fechaYHora, response -> {
            JSONObject jsonObject;
            for(int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    txtNumeroDeCuenta.setText(jsonObject.getString("numeroDeCuenta"));
                    txtApellidos.setText(jsonObject.getString("apellidos"));
                    txtNombre.setText(jsonObject.getString("nombre"));
                    txtFechaYHora.setText(jsonObject.getString("fechaYHora"));
                    int impresion = Integer.parseInt(jsonObject.getString("impresiones"));
                    if(impresion != 0){
                        txtImpresiones.setText(jsonObject.getString("impresiones"));
                    }
                    if(jsonObject.getString("observaciones").length() != 0){
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

    private void actualizarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://enp2saladecomputo.000webhostapp.com/Alumnos/ActualizarDatosUso.php?fechaYHora=" + fechaYHora + "", response -> Toast.makeText(getApplicationContext(), "Actualización exitosa.", Toast.LENGTH_SHORT).show(), error -> {
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("impresiones", txtImpresiones.getText().toString());
                parametros.put("conceptoDeImpresion", concepto);
                parametros.put("observaciones", txtObservaciones.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void eliminarDatos(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            Toast.makeText(getApplicationContext(), "Eliminacion exitosa", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ModificarDatosUso.this, BuscarAlumnos.class);
            startActivity(intent);
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parametros = new HashMap<>();
                parametros.put("fechaYHora", fechaYHora);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}