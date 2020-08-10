package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AffectedCountries extends AppCompatActivity {

    EditText editText;
    ListView listView;
    SimpleArcLoader simpleArcLoader;

    public static List<country>countryList=new ArrayList<>();
    country countryModel;
    MyCustomAdaptar myCustomAdaptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        editText=findViewById(R.id.edt_search);
        listView=findViewById(R.id.listview);
        simpleArcLoader=findViewById(R.id.loader);

        fetchData();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position",position));
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myCustomAdaptar.getFilter().filter(s);
                myCustomAdaptar.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/countries/";
        simpleArcLoader.start();
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray jsonArray=new JSONArray(response);

                       for (int i=0;i<jsonArray.length();i++){


                           JSONObject jsonObject=jsonArray.getJSONObject(i);

                           String countryName=jsonObject.getString("country");
                           String cases=jsonObject.getString("cases");
                           String todayCases=jsonObject.getString("todayCases");
                           String deaths=jsonObject.getString("deaths");
                           String todayDeaths=jsonObject.getString("todayDeaths");
                           String recovered=jsonObject.getString("recovered");
                           String active=jsonObject.getString("active");
                           String critical=jsonObject.getString("critical");

                           JSONObject object=jsonObject.getJSONObject("countryInfo");
                           String flagUrl=object.getString("flag");

                           countryModel =new country(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical);
                           countryList.add(countryModel);

                       }

                       myCustomAdaptar=new MyCustomAdaptar(AffectedCountries.this,countryList);
                       listView.setAdapter(myCustomAdaptar);

                       simpleArcLoader.stop();
                       simpleArcLoader.setVisibility(View.GONE);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);

                Toast.makeText(AffectedCountries.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}