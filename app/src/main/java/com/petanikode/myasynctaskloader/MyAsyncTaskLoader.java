package com.petanikode.myasynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by petanikode on 8/16/17.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<WeatherItems>> {

    private ArrayList<WeatherItems> mData;
    public boolean hasResult = false;

    private static String ID_JAKARTA;
    private static String ID_BANDUNG;
    private static String ID_SEMARANG;

    public MyAsyncTaskLoader(final Context context, Bundle args) {
        super(context);
        onContentChanged();
        Log.d("INIT ASYNCLOADER", "1");

        // tangkap nilai dari Bundle
        ID_JAKARTA = args.getString("ID_JAKARTA");
        ID_BANDUNG = args.getString("ID_BANDUNG");
        ID_SEMARANG = args.getString("ID_SEMARANG");
    }

    @Override
    protected void onStartLoading() {
        Log.d("Content Changed", "1");
        if(takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(ArrayList<WeatherItems> data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStartLoading();
        if(hasResult){
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }


    // openweathermap.org API KEY
    private String API_KEY = getContext().getString(R.string.API_KEY);

    @Override
    public ArrayList<WeatherItems> loadInBackground() {
        Log.d("LOAD BG","1");

        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<WeatherItems> weatherItemses = new ArrayList<>();
        String url = "http://api.openweathermap.org/data/2.5/group?id=" + ID_BANDUNG + "," + ID_JAKARTA + "," + ID_SEMARANG + "&units=metric&appid=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for(int i = 0; i < list.length(); i++){
                        JSONObject weater = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems(weater);
                        weatherItemses.add(weatherItems);
                    }

                    Log.d("REQUEST SUCCESS", "1");
                    Log.d("REQUEST SUCCESS", result);

                } catch (Exception e){
                    e.printStackTrace();
                    Log.d("REQUEST FAILED", "1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        for(int i = 0; i < weatherItemses.size(); i++){
            Log.d("KOTA", weatherItemses.get(i).getNama());
        }

        Log.d("BEFORE RETURN","1");

        return weatherItemses;
    }

    protected void onReleaseResources(ArrayList<WeatherItems> data) {
        //nothing to do.
    }
    public ArrayList<WeatherItems> getResult() {
        return mData;
    }

}
