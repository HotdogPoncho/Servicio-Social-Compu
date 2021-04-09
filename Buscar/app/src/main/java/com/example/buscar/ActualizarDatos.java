package com.example.buscar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActualizarDatos extends AppCompatActivity {

    String fechaYHora;

    EditText txtNumero, txtApelllidos, txtNombre, txtfechaYHora, txtImpresiones, txtObservaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);
        txtNumero = findViewById(R.id.txtNumeroDeTrabajador);
        txtApelllidos = findViewById(R.id.txtApellidos);
        txtNombre = findViewById(R.id.txtNombre);
        txtfechaYHora = findViewById(R.id.txtfechaYHora);
        txtImpresiones = findViewById(R.id.txtImpresiones);
        txtObservaciones = findViewById(R.id.txtObservaciones);
        fechaYHora = getIntent().getStringExtra("fechaYHora");
        Toast.makeText(this, fechaYHora, Toast.LENGTH_SHORT).show();

        buscarVisita("https://enp2saladecomputo.000webhostapp.com/BuscarDatos.php?fechaYHora=" + fechaYHora + "");
    }

    public void cerrar(View view){
        finishAffinity();
    }

    public void buscarVisita(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for(int i = 0; i < response.length(); i++){
                    try{
                        jsonObject = response.getJSONObject(i);
                        txtNumero.setText(jsonObject.getString("numeroDeTrabajador"));
                        txtApelllidos.setText(jsonObject.getString("apellidos"));
                        txtNombre.setText(jsonObject.getString("nombre"));
                        txtfechaYHora.setText(jsonObject.getString("fechaYHora"));
                        if(!jsonObject.getString("impresiones").equals("")){
                            txtImpresiones.setText(jsonObject.getString("impresiones"));
                        }
                        if(!jsonObject.getString("observaciones").equals("")){
                            txtImpresiones.setText("observaciones");
                        }

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

}