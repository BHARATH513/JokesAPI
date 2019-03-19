package com.acer.jokesapi;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class JokesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    RecyclerView recyclerView;
    ArrayList<DataModel> jokemodels;
    ProgressBar bar;
    String jokeurl="http://api.icndb.com/jokes/random/";
    String val;
    public static int loader_id=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);
        val=getIntent().getStringExtra("count");
        recyclerView=findViewById(R.id.jokesrecycler);
        bar=findViewById(R.id.progress);
        jokemodels=new ArrayList<>();
        // new JokeLoadingTask().execute();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new JokeAdapter(this,jokemodels));
        // getSupportLoaderManager().initLoader(loader_id,null, (android.support.v4.app.LoaderManager.LoaderCallbacks<Object>) this);
        //Toast.makeText(this, ""+jokemodels.get(0).getJoke(), Toast.LENGTH_SHORT).show();
        if(isNetworkAvailable()) {
            getSupportLoaderManager().initLoader(loader_id, null, JokesActivity.this);
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        return new android.support.v4.content.AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {

                try {
                    URL url=new URL(jokeurl+val);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream=connection.getInputStream();
                    Scanner s=new Scanner(stream);
                    s.useDelimiter("\\A");
                    if(s.hasNext()){
                        return s.next();
                    }else {
                        return null;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                bar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<String> loader, String data) {

        bar.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray values = jsonObject.getJSONArray("value");
            for (int i = 0; i < values.length(); i++) {
                JSONObject object = values.getJSONObject(i);
                String text = object.getString("joke");
                Log.i("data",text);
                jokemodels.add(new DataModel(text));
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
    }



    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<String> loader) {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
