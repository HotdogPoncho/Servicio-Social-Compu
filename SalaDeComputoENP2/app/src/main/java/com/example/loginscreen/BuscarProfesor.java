package com.example.loginscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuscarProfesor extends AppCompatActivity {

    EditText txtBuscar;

    ListView lstVisitas;

    RadioButton optProfesores, optVisitas;

    String[] visitas = new String[0];

    String[] nombres = new String[0];

    String[] numeros = new String[0];

    String[] colegios = new String[0];

    String[] areas = new String[0];

    int[] items = new int[0];

    String[] areasColegios = {"Fisica", "Informatica", "Matematicas",
                      "Biologia", "Educacion Fisica", "Morfologia, Fisiologia y Salud", "Orientacion Educativa", "Psicologia e Higiene Mental", "Quimica",
                      "Ciencias Sociales", "Geografia", "Historia",
                      "Aleman", "Artes Plasticas", "Danza", "Dibujo y Modelado", "Filosofia", "Frances", "Ingles", "Italiano", "Letras Clasicas", "Literatura", "Musica", "Teatro"};

    int[] itemsColegios = {R.drawable.ic_fisica, R.drawable.ic_informatica, R.drawable.ic_matematicas,
                   R.drawable.ic_bacterias, R.drawable.ic_deporte, R.drawable.ic_fisiologia, R.drawable.ic_exito, R.drawable.ic_psicologia, R.drawable.ic_quimica,
                   R.drawable.ic_ciencias_sociales, R.drawable.ic_geografia, R.drawable.ic_libro,
                   R.drawable.ic_aleman, R.drawable.ic_escultura, R.drawable.ic_danza, R.drawable.ic_bosquejo, R.drawable.ic_filosofia, R.drawable.ic_frances, R.drawable.ic_english, R.drawable.ic_italiano, R.drawable.ic_letras, R.drawable.ic_literatura, R.drawable.ic_musica, R.drawable.ic_teatro};


    String fechaYHora, fecha, nombre, numero, colegio, area;

    boolean control;

    CountDownTimer inactividad;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_profesor);

        txtBuscar = findViewById(R.id.txtBuscar);

        lstVisitas = findViewById(R.id.lstvwProfesores);

        optProfesores = findViewById(R.id.optProfesores);

        optVisitas = findViewById(R.id.optVisitas);

        lstVisitas.setOnItemClickListener((parent, view, position, id) -> {
            if(control){
                fecha = visitas[position];
            }else{
                fecha = numeros[position];
            }
            view.setSelected(true);
        });

        inactividad = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if((int)millisUntilFinished / 1000 == 25){
                    Toast.makeText(getApplicationContext(), "Se detectó inactividad, presione la pantalla para desactivar el cierre de la aplicación.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFinish() {
                finishAffinity();
            }
        }.start();

        buscarProfe("https://enp2saladecomputo.000webhostapp.com/BusquedaInicial.php?");

    }

    public void modificar(View view){
        if(control){
            Intent modificarDatos = new Intent(BuscarProfesor.this, ModificarDatos.class);
            modificarDatos.putExtra("fechaYHora", fecha);
            startActivity(modificarDatos);
        }else{
            Intent modificarDatos = new Intent(BuscarProfesor.this, ModificarDatosProfesores.class);
            modificarDatos.putExtra("fechaYHora", fecha);
            startActivity(modificarDatos);
        }
    }

    public void buscar(View view) {
        if (txtBuscar.length() > 0) {
            if(optVisitas.isChecked()){
                buscarProfe("https://enp2saladecomputo.000webhostapp.com/BusquedaApellido.php?apellidos=" + txtBuscar.getText().toString() + "");
            }else{
                buscarProfesor("https://enp2saladecomputo.000webhostapp.com/BusquedaProfesores.php?apellidos=" + txtBuscar.getText().toString() + "");
            }

        } else {
            if(optVisitas.isChecked()){
                buscarProfe("https://enp2saladecomputo.000webhostapp.com/BuscarTodasVisitas.php");
            }else{
                buscarProfesor("https://enp2saladecomputo.000webhostapp.com/BuscarTodosProfesores.php");
            }
        }
    }

    public void item(String[] datos){

        items = new int[colegios.length];

        for(int i = 0; i < colegios.length; i++){
            for(int j = 0; j < areasColegios.length; j++){
                if(colegios[i].equals(areasColegios[j].toUpperCase())){
                    items[i] = itemsColegios[j];
                    break;
                }
            }
        }

        CustomAdapterListView customAdapter = new CustomAdapterListView(this, nombres, datos, items);
        lstVisitas.setAdapter(customAdapter);

    }

    public void buscarProfe(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            JSONObject jsonObject;
            visitas = new String[response.length()];
            nombres = new String[response.length()];
            numeros = new String[response.length()];
            colegios = new String[response.length()];
            for(int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    fechaYHora = jsonObject.getString("fechaYHora");
                    numero = jsonObject.getString("numeroDeTrabajador");
                    nombre = jsonObject.getString("nombre") + " " + jsonObject.getString("apellidos");
                    colegio = jsonObject.getString("colegio");
                    visitas[i] = fechaYHora;
                    nombres[i] = nombre;
                    numeros[i] = numero;
                    colegios[i] = colegio;
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            control = true;
            item(visitas);
        }, error -> Toast.makeText(getApplicationContext(), "No se encontró al profesor.", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void buscarProfesor(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( URL, response -> {
            JSONObject jsonObject;
            numeros = new String[response.length()];
            nombres = new String[response.length()];
            areas = new String[response.length()];
            colegios = new String[response.length()];
            for(int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    numero = jsonObject.getString("numeroDeTrabajador");
                    nombre = jsonObject.getString("nombre") + " " + jsonObject.getString("apellidos");
                    area = jsonObject.getString("area");
                    colegio = jsonObject.getString("colegio");

                    numeros[i] = numero;
                    nombres[i] = nombre;
                    areas[i] = area;
                    colegios[i] = colegio;
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            control = false;
            item(colegios);
        }, error -> Toast.makeText(getApplicationContext(), "No se encontró al profesor.", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onUserInteraction(){

        super.onUserInteraction();

        inactividad.cancel();
        inactividad.start();
    }

    class CustomAdapterListView extends ArrayAdapter<String> {

        Context context;
        String[] nombre, visita;
        int[] item;

        CustomAdapterListView(Context context, String[] nombre, String[] visita, int[] item) {
            super(context, R.layout.custom_listview_layout, nombre);
            this.context = context;
            this.nombre = nombre;
            this.visita = visita;
            this.item = item;
        }

        public String getNombre(int i){
            return nombre[i];
        }

        public String getVisita(int i){
            return visita[i];
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View lstVisitas = layoutInflater.inflate(R.layout.custom_listview_layout, parent, false);

            ImageView imageView = lstVisitas.findViewById(R.id.Item);
            TextView nombre = lstVisitas.findViewById(R.id.Nombre);
            TextView visita = lstVisitas.findViewById(R.id.Visita);

            imageView.setImageResource(items[position]);
            nombre.setText(this.getNombre(position));
            visita.setText(this.getVisita(position));

            return lstVisitas;
        }
    }

}