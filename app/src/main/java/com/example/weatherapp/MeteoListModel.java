package com.example.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeteoListModel extends ArrayAdapter<MeteoItem> {
    private List<MeteoItem> listItems;
    private int resource;
    public static Map<String, Integer> images = new HashMap<>();

    static {
        images.put("Clear", R.drawable.clear);
        images.put("Clouds", R.drawable.clouds);
        images.put("Rain", R.drawable.rain);
    }

    public MeteoListModel(@NonNull Context context, int resource, List<MeteoItem> data) {
        super(context, resource, data);
        Log.i("MyLog", "MeteoListModel ......... Constructor");
        this.listItems = data;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i("MyLog", "MeteoListModel ...... View");
        View listItem = convertView;
        if (listItem==null)
            listItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        ImageView imageView = listItem.findViewById(R.id.imgView);
        TextView txtMax=listItem.findViewById(R.id.txtMax);
        TextView txtMin=listItem.findViewById(R.id.txtMin);
        TextView txtDate=listItem.findViewById(R.id.txtDate);
        String key = listItems.get(position).image;
        if (key != null)
            imageView.setImageResource(images.get(key));
        txtMin.setText(String.valueOf(listItems.get(position).min)+" °C");
        txtMax.setText(String.valueOf(listItems.get(position).max)+" °C");
        txtDate.setText(String.valueOf(listItems.get(position).date));

        return listItem;
    }
}
