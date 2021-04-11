package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginscreen.R;

import org.json.JSONArray;
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
        actualizarDatos("https://enp2saladecomputo.000webhostapp.com/Actualizar?fechaYHora=" + fechaYHora + "");
    }

    public void actualizarDatos(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("numeroDeTrabajador", txtNumero.getText().toString());
                parametros.put("apellidos", txtApellidos.getText().toString());
                parametros.put("nombre", txtNombre.getText().toString());
                parametros.put("fechaYHora", fechaYHora);
                parametros.put("impresiones", txtImpresiones.getText().toString());
                parametros.put("observaciones", txtObservaciones.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                        txtApellidos.setText(jsonObject.getString("apellidos"));
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