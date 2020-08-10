package com.example.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {
    Context context;
    int[] image;
    LayoutInflater layoutInflater;

    public MyAdapter(Context context, int[]image){
        this.context=context;
        this.image=image;
        layoutInflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view=layoutInflater.inflate(R.layout.flipper_item,null);
        ImageView imageView=view.findViewById(R.id.tips_images);

        imageView.setImageResource(image[position]);
        return view;
    }
}
