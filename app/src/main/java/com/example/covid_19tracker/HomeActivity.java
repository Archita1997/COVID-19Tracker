package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    TextView cases,recovery,critical,active,todaycases,todaydeaths,totaldeaths,affectedcountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    Toolbar toolbar;
    GridLayout mainGrid;
    CardView card1,card2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();

        mainGrid=findViewById(R.id.main_grid);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);

        cases=findViewById(R.id.tv_cases);
        recovery=findViewById(R.id.tv_recovery);
        critical=findViewById(R.id.tv_critical);
        active=findViewById(R.id.tv_active);
        todaycases=findViewById(R.id.tv_todaycases);
        todaydeaths=findViewById(R.id.tv_todaydeaths);
        totaldeaths=findViewById(R.id.tv_totaldeath);
       affectedcountries=findViewById(R.id.tv_afectedcountries);

       simpleArcLoader=findViewById(R.id.loader);
        scrollView=findViewById(R.id.scroll);
        pieChart=findViewById(R.id.piechart);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intInfo = new Intent(HomeActivity.this, ActivityInformation.class);
                startActivity(intInfo);

            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intTips = new Intent(HomeActivity.this, ActivityTipsForSafety.class);
                startActivity(intTips);
            }
        });





        fetchData();
    }

    private void setupToolbar() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.item1:
                android.app.AlertDialog.Builder alert1 = new AlertDialog.Builder(HomeActivity.this);
                alert1.setCancelable(false);
                alert1.setTitle("About");
                alert1.setMessage("This is a COVID-19 tracker app where you can get the daily updates about COVID-19 disease. " +
                        "Here you can see number of cases world wide, and also can get information of active cases, critical cases, deaths, infected countries, today's deaths, recovery.  " +
                        "You can also check the details of different countries affected by COVID-19.   " +
                        "Here is also some important information about this disease, symptoms and remedies are given.");

                alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert1.create().show();
                break;

            case R.id.item2:
                Toast.makeText(this, "RATE is under construction", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item3:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "COVID-19Tracker");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "COVID-19Tracker");
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response.toString());
                            cases.setText(jsonObject.getString("cases"));
                            recovery.setText(jsonObject.getString("recovered"));
                            critical.setText(jsonObject.getString("critical"));
                            active.setText(jsonObject.getString("active"));
                            todaycases.setText(jsonObject.getString("todayCases"));
                            totaldeaths.setText(jsonObject.getString("deaths"));
                            todaydeaths.setText(jsonObject.getString("todayDeaths"));
                            affectedcountries.setText(jsonObject.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(cases.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(recovery.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(totaldeaths.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(active.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(HomeActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }
}