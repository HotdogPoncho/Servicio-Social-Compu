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

public class RegistroAlumnos extends AppCompatActivity {

    EditText txtNumeroDeCuenta, txtApellidos, txtNombre;

    Spinner cboGrado, cboGrupo;

    Button cmdRegistrarse, cmdLimpiar;

    String[] grado = {"Cuarto", "Quinto", "Sexto"};

    int[] cuarto = {401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470};

    int[] quinto = {501,502,503,504,505,506,507,508,509,510,511,512,513,514,515,516,517,518,519,551,552,553,554,555,556,557,558,559,560,561,562,563,564};

    int[] sexto = {601,602,603,604,605,606,607,608,609,610,611,612,614,615,617,618,619,620,621,651,652,653,654,655,656,657,658,659,660,661,662,663,664};

    String grupoSeleccionado, gradito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alumnos);

        inicializar();

    }

    public void inicializar(){

        txtNumeroDeCuenta = findViewById(R.id.txtNumCuentaM);
        txtApellidos = findViewById(R.id.txtApellidosM);
        txtNombre = findViewById(R.id.txtNombreM);

        cmdRegistrarse = findViewById(R.id.cmdActualizarDatos);
        cmdLimpiar = findViewById(R.id.cmdRegresar);

        cboGrado = findViewById(R.id.cboGradoM);
        cboGrupo = findViewById(R.id.cboGrupoM);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, grado);
        cboGrado.setAdapter(arrayAdapter);

        cboGrado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradito = grado[position];
                switch (position){
                    case 0:
                        grupos(cuarto);
                        break;
                    case 1:
                        grupos(quinto);
                        break;
                    case 2:
                        grupos(sexto);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cmdRegistrarse.setOnClickListener(v -> {
            if(txtNumeroDeCuenta.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Ingresa tu numero de cuenta", Toast.LENGTH_SHORT).show();
                txtNumeroDeCuenta.requestFocus();
            }else if(txtApellidos.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Ingresa tus apellidos", Toast.LENGTH_SHORT).show();
                txtApellidos.requestFocus();
            }else if(txtNombre.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Ingresa tu nombre", Toast.LENGTH_SHORT).show();
                txtNombre.requestFocus();
            }else {
                duplicados();
                intent();
            }
        });

        cmdLimpiar.setOnClickListener(v -> {
            txtNumeroDeCuenta.setText("");
            txtApellidos.setText("");
            txtNombre.setText("");
            txtNumeroDeCuenta.requestFocus();
        });


    }

    public void grupos(int[] grupo){
        String[] grupito = new String[grupo.length];
        int i = 0;
        for(int grupote : grupo){
            grupito[i] = grupote+"";
            i++;
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, grupito);
        cboGrupo.setAdapter(arrayAdapter);

        cboGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grupoSeleccionado = grupo[position]+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void registrarAlumno(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://enp2saladecomputo.000webhostapp.com/Alumnos/RegistrarAlumno.php", response -> Toast.makeText(getApplicationContext(), "Registro Exitoso, Intente Iniciando Sesi??n", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("numeroDeCuenta", txtNumeroDeCuenta.getText().toString());
                parametros.put("apellidos", txtApellidos.getText().toString().toUpperCase());
                parametros.put("nombre", txtNombre.getText().toString().toUpperCase());
                parametros.put("grupo", grupoSeleccionado);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void duplicados(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://enp2saladecomputo.000webhostapp.com/Alumnos/DuplicadoAlumnos.php?numeroDeCuenta=" + txtNumeroDeCuenta.getText().toString(), response -> {
            JSONObject jsonObject;
            for (int i = 0; i < response.length(); i++) {
                try {
                    jsonObject = response.getJSONObject(i);
                    Toast.makeText(getApplicationContext(), "Hola " + jsonObject.getString("nombre") + " intenta iniciando sesi??n con tu numero de cuenta", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> registrarAlumno());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void intent(){
        Intent intent = new Intent(RegistroAlumnos.this, MainActivity.class);
        startActivity(intent);
    }

}