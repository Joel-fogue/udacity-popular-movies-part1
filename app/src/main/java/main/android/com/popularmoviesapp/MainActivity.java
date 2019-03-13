package main.android.com.popularmoviesapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import main.android.com.popularmoviesapp.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PopularMoviesAdapter mAdapter;
    int NUMBER_COLUM_IN_GRID = 3;
    JSONArray moviesArray;
    int len;
    public TextView mContent;
    public ArrayList fullPosterPathsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //mContent = (TextView) findViewById(R.id.mContent);
        fullPosterPathsArray = new ArrayList();

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_COLUM_IN_GRID, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        // use this setting to improve performance if you know that changes
        //in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        URL moviesUrl = NetworkUtils.buildUrl();
        fetchMoviesUrl(moviesUrl);
    }

    public void fetchMoviesUrl(URL url){
        new DownloadMovieUrlsAsyncTask().execute(url);
    }

    private class DownloadMovieUrlsAsyncTask extends AsyncTask<URL, Void, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
            URL url = urls[0];
            JSONArray allMoviesJsonArray = new JSONArray();
            try {
                JSONObject allMoviesJsonObject = NetworkUtils.getResponseFromHttpUrl(url);
                allMoviesJsonArray = allMoviesJsonObject.getJSONArray("results");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return allMoviesJsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray allMoviesJsonArray) {
            moviesArray = allMoviesJsonArray;
            len=allMoviesJsonArray.length();
            for(int i=0; i<allMoviesJsonArray.length(); i++){
                JSONObject singleMovieJsonObject = null;
                try {
                    singleMovieJsonObject = allMoviesJsonArray.getJSONObject(i);
                    String posterPath = singleMovieJsonObject.getString("poster_path").split("/")[1];
                    URL fullPosterPath = NetworkUtils.buildPosterPathUrl(posterPath);
                    fullPosterPathsArray.add(fullPosterPath);
                    Log.v("blah", fullPosterPath.toString());
                    Log.v("lens", String.valueOf(fullPosterPathsArray.size()));
                    //Instantiating our adapter class
                    mAdapter = new PopularMoviesAdapter(fullPosterPathsArray);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }//end loop
            super.onPostExecute(allMoviesJsonArray);
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
