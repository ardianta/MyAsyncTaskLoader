package com.petanikode.myasynctaskloader;



import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<WeatherItems>>  {

    ListView listView ;
    WeatherAdapter adapter;
    ProgressBar progressBar;
    Button btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnLoad = (Button) findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(btnOnclickListener);


        adapter = new WeatherAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.list_view);

        listView.setAdapter(adapter);

    }

    private View.OnClickListener btnOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btn_load){

                // persiapkan parameter yang akan dikirim ke loader
                Bundle args = new Bundle();
                args.putString("ID_JAKARTA", "1642911");
                args.putString("ID_BANDUNG", "1650357");
                args.putString("ID_SEMARANG", "1627896");

                // execute loader and send with paramter
                // getLoaderManager().initLoader(0, args, MainActivity.this);

                getLoaderManager().restartLoader(0, args, MainActivity.this);

                // diable button biar user tidak klik2
                btnLoad.setEnabled(false);

            }
        }
    };

    @Override
    public Loader<ArrayList<WeatherItems>>
    onCreateLoader(int id, Bundle args) {
        Log.d("Create loader","1");
        progressBar.setVisibility(View.VISIBLE);

        // get the args from Bundle
        // send it to MyAsyncTaskLoader
        return new MyAsyncTaskLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherItems>>
                                       loader, ArrayList<WeatherItems> data) {
        Log.d("Load Finish","1");
        progressBar.setVisibility(View.GONE);
        adapter.setData(data);

        // Enable button setelah dapat hasil
        btnLoad.setEnabled(true);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherItems>> loader) {
        Log.d("Load Reset","1");
//        adapter.setData(null);
    }


}

