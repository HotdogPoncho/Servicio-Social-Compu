package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button cmdLimpiar, cmdBuscar;

    TextView txtHora, txtProfesor, txtImpresion;

    Spinner cboMes;

    String mes, dato = "";

    int impresionesMensuales;

    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    String[] mesecito = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int[] itemMes = {R.drawable.ic_enero, R.drawable.ic_febrero, R.drawable.ic_marzo, R.drawable.ic_abril, R.drawable.ic_mayo, R.drawable.ic_junio, R.drawable.ic_julio, R.drawable.ic_agosto, R.drawable.ic_septiembre, R.drawable.ic_octubre, R.drawable.ic_noviembre, R.drawable.ic_diciembre};
    String[] fecha = new String[1];
    String[] hora = new String[1];
    int[] impresiones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        inicializar();

    }

    public void inicializar() {
        cmdLimpiar = findViewById(R.id.cmdLimpiar);
        cmdBuscar = findViewById(R.id.cmdBuscar);
        txtHora = findViewById(R.id.txtHora);
        txtProfesor = findViewById(R.id.txtProfesores);
        txtImpresion = findViewById(R.id.txtImpresiones);
        cboMes = findViewById(R.id.cboMes);

        ArrayList<CustomItems> customItemsArrayList = new ArrayList<>();

        for (int i = 0; i < meses.length; i++) {
            customItemsArrayList.add(new CustomItems(meses[i], itemMes[i]));
        }

        CustomAdapter customAdapter = new CustomAdapter(this, customItemsArrayList);

        cboMes.setAdapter(customAdapter);
        cboMes.setOnItemSelectedListener(this);

        cmdLimpiar.setOnClickListener(v -> {
            txtHora.setText("");
            txtProfesor.setText("");
            txtImpresion.setText("");
        });

        cmdBuscar.setOnClickListener(v -> obtenerDatos("https://enp2saladecomputo.000webhostapp.com/Profesores/Estadistica.php?fechaYHora="+mes));
    }

    public void obtenerDatos(String URL) {
        @SuppressLint("SetTextI18n") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            JSONObject jsonObject;
            impresionesMensuales = 0;
            fecha = new String[response.length()];
            impresiones = new int[response.length()];
            hora = new String[response.length()];
            for (int i = 0; i < response.length(); i++) {
                try {
                    jsonObject = response.getJSONObject(i);
                    fecha[i] = jsonObject.getString("fechaYHora");
                    impresiones[i] = jsonObject.getInt("impresiones");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            for (int i = 0; i < fecha.length; i++) {
                hora[i] = fecha[i].substring(14, 16);
            }
            for (int impresion : impresiones) {
                impresionesMensuales += impresion;
            }
            dato = horaDestacada(hora);
            if(hora[0] != null){
                txtImpresion.setText(impresionesMensuales+"");
                txtHora.setText(dato+":00 hrs");
                txtProfesor.setText(fecha.length+"");
            }
        }, error -> Toast.makeText(getApplicationContext(), "No se encontraron datos del mes", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        mes = mesecito[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String horaDestacada(String[] hora){
        String[] horario = {"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
        int[] conteo = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        String auxHorario;
        int auxConteo;

        for (String s : hora) {
            for (int j = 0; j < horario.length; j++) {
                if (s.equals(horario[j])) {
                    conteo[j] += 1;
                    break;
                }
            }
        }

        for(int i = 0; i < horario.length-1; i++){
            for(int j = 0; j < horario.length-1; j++){
                if(conteo[j] < conteo[j+1]){
                    auxConteo = conteo[j+1];
                    conteo[j+1] = conteo[j];
                    conteo[j] = auxConteo;
                    auxHorario = horario[j+1];
                    horario[j+1] = horario[j];
                    horario[j] = auxHorario;
                }
            }
        }

        String horaDestacada = horario[0];

        for(int i = 0; i < horario.length - 1; i++){
            if(conteo[i] == conteo[i+1]){
                horaDestacada.concat(" y " + conteo[i+1]);
            }
        }
        return horaDestacada;
    }
}