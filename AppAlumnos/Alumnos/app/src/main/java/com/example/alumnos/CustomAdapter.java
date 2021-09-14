package com.example.alumnos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<CustomItems> {

    public CustomAdapter(@NonNull Context context, ArrayList<CustomItems> customItems) {
        super(context, 0, customItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    public View customView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner, parent, false);
        }
        CustomItems items = getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imgItem);
        TextView textView = convertView.findViewById(R.id.txtItem);
        if(items != null){
            imageView.setImageResource(items.getSpinnerItem());
            textView.setText(items.getSpinnerText());
        }
        return convertView;
    }
}
