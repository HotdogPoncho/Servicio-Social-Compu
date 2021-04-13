package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuscarProfesor extends AppCompatActivity {

    ScrollView scrollView;

    RadioButton optNumero, optNombre, optApellidos;

    EditText txtBuscar;

    ListView lstVisitas;

    String[] visitas = new String[0];

    String fechaYHora, fecha;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_profesor);

        optNumero = findViewById(R.id.optNumero);
        optNombre = findViewById(R.id.optNombre);
        optApellidos = findViewById(R.id.optApellidos);

        txtBuscar = findViewById(R.id.txtBuscar);

        lstVisitas = findViewById(R.id.lstvwProfesores);

        scrollView = findViewById(R.id.ScrollView);

        scrollView.setOnTouchListener((v, event) -> {
            findViewById(R.id.ScrollView).getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        });

        lstVisitas.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        lstVisitas.setOnItemClickListener((parent, view, position, id) -> {
            fecha = visitas[position];
        });

    }

    public void modificar(View view){
        Intent modificarDatos = new Intent(BuscarProfesor.this, ModificarDatos.class);
        modificarDatos.putExtra("fechaYHora", fecha);
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
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
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void visitas(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitas);
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