package main.android.com.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import main.android.com.popularmoviesapp.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    int NUMBER_COLUM_IN_GRID = 2;
    int moviesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, NUMBER_COLUM_IN_GRID);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PopularMoviesAdapter(moviesArray);
        recyclerView.setAdapter(mAdapter);

        URL moviesUrl = NetworkUtils.buildUrl();
        new DownloadMovieUrlsAsyncTask().execute(moviesUrl);
    }


    private class DownloadMovieUrlsAsyncTask extends AsyncTask<URL, Void, JSONArray>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
            URL url = urls[0];
            JSONArray allMoviesJsonArray = null;
            try {
                JSONObject allMoviesJsonObject = NetworkUtils.getResponseFromHttpUrl(url);
                allMoviesJsonArray = allMoviesJsonObject.getJSONArray("results");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return allMoviesJsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray AllmoviesJsonArray) {
            super.onPostExecute(AllmoviesJsonArray);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
