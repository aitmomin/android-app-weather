package com.example.weatherapp;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText txtCity;
    private ListView listInfosMeteo;
    List<MeteoItem> data = new ArrayList<>();
    private MeteoListModel model;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCity=findViewById(R.id.txtCity);
        listInfosMeteo=findViewById(R.id.listInfosMeteo);
        btnSearch=findViewById(R.id.btnSearch);
        model = new MeteoListModel(getApplicationContext(), R.layout.list_item, data);
        listInfosMeteo.setAdapter(model);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyLog", ".......");
                data.clear();
                model.notifyDataSetChanged();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String city = txtCity.getText().toString();
                String id =  "GET_YOUR_ID_FROM_THE_WEBSITE";

                Log.i("MyLog", "la ville : "+city);
                String url = "https://samples.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+id;
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) { try {
                                Log.i("MyLog", "-----------------");
                                Log.i("MyLog", response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MeteoItem meteoItem = new MeteoItem();
                                    JSONObject o = jsonArray.getJSONObject(i);
                                    Date d = new Date(o.getLong("dt") * 1000);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  HH:mm");
                                    String date = sdf.format(d);
                                    JSONObject main = o.getJSONObject("main");
                                    JSONArray weather = o.getJSONArray("weather");
                                    int min = (int) (main.getDouble("temp_min") - 273.15);
                                    int max = (int) (main.getDouble("temp_max") - 273.15);
                                    meteoItem.date = date;
                                    meteoItem.min = min;
                                    meteoItem.max = max;
                                    meteoItem.image = weather.getJSONObject(0).getString("main");
                                    data.add(meteoItem);
                                }
                                model.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("MyLog", "Connection problem!");
                            }
                        }
                );
                queue.add(stringRequest);
            }
        });
    }
}
