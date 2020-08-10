package com.example.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdaptar extends ArrayAdapter<country> {

    private Context context;
    private List<country> countryList;
    private List<country> countryListFiltered;


    public MyCustomAdaptar(Context context, List<country>countryList) {
        super(context, R.layout.list_custom_item,countryList);

        this.context=context;
        this.countryList=countryList;
        this.countryListFiltered=countryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);

        TextView tvCountryName=view.findViewById(R.id.tvCountryName);
        ImageView imageView =view.findViewById(R.id.imageFlag);

        tvCountryName.setText(countryListFiltered.get(position).getCountry());
        Glide.with(context).load(countryListFiltered.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countryListFiltered.size();
    }

    @Nullable
    @Override
    public country getItem(int position) {
        return countryListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = countryList.size();
                    filterResults.values = countryList;
                } else {
                    List<country> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (country itemModel : countryList) {
                        if (itemModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultModel.add(itemModel);
                        }
                        filterResults.count = resultModel.size();
                        filterResults.values = resultModel;
                    }
                }

                return filterResults;
            }



            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            countryListFiltered=(List<country>)results.values;
            AffectedCountries.countryList=(List<country>)results.values;
            notifyDataSetChanged();

            }
        };

        return filter;
    }
}


