package com.example.covid_19tracker;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityTipsForSafety extends AppCompatActivity {

    Toolbar toolbar1;

    ViewFlipper v_flipper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_for_safety);

        toolbar1 = (Toolbar) findViewById(R.id.tips_toolbar);
        setSupportActionBar(toolbar1);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int images[]={R.drawable.tips_safety1, R.drawable.tips_safety2, R.drawable.tips_safety3, R.drawable.tips_safety4,R.drawable.tips_safety5,R.drawable.tips_safety6};

        v_flipper=findViewById(R.id.v_flipper);

        for (int image:images){
            flipperImage(image);
        }

    }

    public void flipperImage(int image){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(2000);//4 sec
        v_flipper.setAutoStart(true);

        //animation

        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}