package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class ModificarDatos extends AppCompatActivity {

    String fechaYHora;

    EditText txtNumero, txtApellidos, txtNombre, txtfechaYHora, txtImpresiones, txtObservaciones;

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

        fechaYHora = getIntent().getStringExtra("fechaYHora");

        buscarVisita("https://enp2saladecomputo.000webhostapp.com/BuscarDatos.php?fechaYHora=" + fechaYHora + "");
    }

    public void cerrar(View view){
        finishAffinity();
    }

    public void actualizar(View view){
        actualizarDatos("https://enp2saladecomputo.000webhostapp.com/Actualizar.php?fechaYHora=" + fechaYHora + "");
    }

    public void actualizarDatos(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> Toast.makeText(getApplicationContext(), "Actualización exitosa", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("impresiones", txtImpresiones.getText().toString());
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

}