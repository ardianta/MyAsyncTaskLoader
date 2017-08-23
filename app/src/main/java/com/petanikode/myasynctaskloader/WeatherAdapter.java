package com.petanikode.myasynctaskloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by petanikode on 8/16/17.
 */

public class WeatherAdapter extends BaseAdapter {

    private ArrayList<WeatherItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    private String urlConfig;

    public WeatherAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<WeatherItems> items){
        mData = items;
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public WeatherItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.wheather_items, null);

            holder.textViewNamaKota= (TextView)convertView.findViewById(R.id.tv_kota);

            holder.textViewTemperature = (TextView)convertView.findViewById(R.id.tv_temp);
            holder.textViewDescription = (TextView)convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewNamaKota.setText(mData.get(position).getNama());
        holder.textViewTemperature.setText(mData.get(position).getTemperature());
        holder.textViewDescription.setText(mData.get(position).getDescription());
        return convertView;
    }

    public static class ViewHolder {
        public TextView textViewNamaKota;
        public TextView textViewTemperature;
        public TextView textViewDescription;
    }
}
