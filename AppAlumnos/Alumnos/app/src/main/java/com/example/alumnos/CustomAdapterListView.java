package com.example.alumnos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapterListView extends ArrayAdapter<String> {

    Context context;
    String[] nombre, visitas;

    CustomAdapterListView(Context context, String[] nombre, String[] visitas){
        super(context, R.layout.custom_listview_layout, nombre);
        this.context = context;
        this.nombre = nombre;
        this.visitas = visitas;
    }

    public String getNombre(int i) {
        return nombre[i];
    }

    public String getVisitas(int i) {
        return visitas[i];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View lstVisitas = layoutInflater.inflate(R.layout.custom_listview_layout, parent, false);

        ImageView imageView = lstVisitas.findViewById(R.id.imgItem);
        TextView nombreAlumno = lstVisitas.findViewById(R.id.txtNombreAlumno);
        TextView visita = lstVisitas.findViewById(R.id.txtVisita);

        imageView.setImageResource(R.drawable.alumno);
        nombreAlumno.setText(this.getNombre(position));
        visita.setText(this.getVisitas(position));

        return lstVisitas;
    }
}