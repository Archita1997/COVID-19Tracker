package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private int positionCountry;
    TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        positionCountry=intent.getIntExtra("position",0);

        tvCountry=findViewById(R.id.tv_country);
        tvCases=findViewById(R.id.tv_cases);
        tvRecovered=findViewById(R.id.tv_recovery);
        tvCritical=findViewById(R.id.tv_critical);
        tvActive=findViewById(R.id.tv_active);
        tvTodayCases=findViewById(R.id.tv_todaycases);
        tvTotalDeaths=findViewById(R.id.tv_deaths);
        tvTodayDeaths=findViewById(R.id.tv_todaydeaths);

        tvCountry.setText(AffectedCountries.countryList.get(positionCountry).getCountry());
        tvCases.setText(AffectedCountries.countryList.get(positionCountry).getCases());
        tvRecovered.setText(AffectedCountries.countryList.get(positionCountry).getRecovered());
        tvCritical.setText(AffectedCountries.countryList.get(positionCountry).getCritical());
        tvActive.setText(AffectedCountries.countryList.get(positionCountry).getActive());
        tvTodayCases.setText(AffectedCountries.countryList.get(positionCountry).getTodaycases());
        tvTotalDeaths.setText(AffectedCountries.countryList.get(positionCountry).getDeaths());
        tvTodayDeaths.setText(AffectedCountries.countryList.get(positionCountry).getTodaydeath());


    }
}