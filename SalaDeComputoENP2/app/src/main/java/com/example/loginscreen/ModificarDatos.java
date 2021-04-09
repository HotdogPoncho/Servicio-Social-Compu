package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class ModificarDatos extends AppCompatActivity {

    String numero,apellidos,nombre,fecha,hora,impresiones,observaciones;

    EditText txtNumTrab, txtApellidos, txtNombre, txtFecha, txtHora, txtImpresiones, txtObservaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos);

        txtNumTrab = (EditText)findViewById(R.id.txtNumTrab);
        txtApellidos = (EditText)findViewById(R.id.txtApellidos);
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtFecha = (EditText)findViewById(R.id.txtFecha);
        txtHora = (EditText)findViewById(R.id.txtHora);
        txtImpresiones = (EditText)findViewById(R.id.txtImpresiones);
        txtObservaciones = (EditText)findViewById(R.id.txtObservaciones);

        numero = getIntent().getStringExtra("numeroDeTrabajador");
        /*apellidos = getIntent().getStringExtra("apellidos");
        nombre = getIntent().getStringExtra("nombre");
        fecha = getIntent().getStringExtra("fecha");
        hora = getIntent().getStringExtra("hora");
        impresiones = getIntent().getStringExtra("impresiones");
        observaciones = getIntent().getStringExtra("observaciones");*/

        txtNumTrab.setText(numero);
        /*txtApellidos.setText(apellidos);
        txtNombre.setText(nombre);
        txtFecha.setText(fecha);
        txtHora.setText(hora);*/

    }

}