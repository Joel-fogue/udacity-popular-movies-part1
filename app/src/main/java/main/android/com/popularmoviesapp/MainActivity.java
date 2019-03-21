package main.android.com.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import main.android.com.popularmoviesapp.parcels.Movie;
import main.android.com.popularmoviesapp.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements PopularMoviesAdapter.OnRecyclerViewClickListener {

    private RecyclerView mRecyclerView;
    private PopularMoviesAdapter mAdapter;
    int NUMBER_COLUMN_IN_GRID = 3;
    JSONArray moviesArray;
    int len;
    public ArrayList moviePojosArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        moviePojosArrayList = new ArrayList<Movie>();
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_COLUMN_IN_GRID, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        // use this setting to improve performance if you know that changes
        //in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        URL moviesUrl = NetworkUtils.buildUrl(getString(R.string.popular_endpoint));
        //Checking if we got an internet connection prior to making network call
        if (isOnline())
            fetchMoviesUrl(moviesUrl);
    }

    /**
     * Got this code from here: https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
     *
     * @return a boolean true if we're connected
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

    public void fetchMoviesUrl(URL url) {
        new DownloadMovieUrlsAsyncTask().execute(url);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.MoviePojosArrayListParcel))) {
            moviePojosArrayList = savedInstanceState.getParcelableArrayList(getString(R.string.MoviePojosArrayListParcel));
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.MoviePojosArrayListParcel), moviePojosArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onclickListener(int itemClicked) {
        Toast.makeText(getApplicationContext(), getString(R.string.ItemClickedWas) + itemClicked, Toast.LENGTH_SHORT).show();
    }

    private class DownloadMovieUrlsAsyncTask extends AsyncTask<URL, Void, JSONArray>
            implements PopularMoviesAdapter.OnRecyclerViewClickListener {

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
                allMoviesJsonArray = allMoviesJsonObject.getJSONArray(getString(R.string.results));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return allMoviesJsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray allMoviesJsonArray) {
            moviesArray = allMoviesJsonArray;
            len = allMoviesJsonArray.length();
            for (int i = 0; i < allMoviesJsonArray.length(); i++) {
                JSONObject singleMovieJsonObject = null;
                try {
                    singleMovieJsonObject = allMoviesJsonArray.getJSONObject(i);
                    String movieTitle = singleMovieJsonObject.getString(getString(R.string.title));
                    String movieReleaseDate = singleMovieJsonObject.getString(getString(R.string.release_date));
                    String movieOverview = singleMovieJsonObject.getString(getString(R.string.overview));
                    String posterPath = singleMovieJsonObject.getString(getString(R.string.poster_path)).split(getString(R.string.forward_slash))[1];
                    String fullPosterPath = NetworkUtils.buildPosterPathUrl(posterPath).toString();
                    String movieVoteAverage = singleMovieJsonObject.getString(getString(R.string.vote_average));
                    Movie aMovie = new Movie(movieTitle, movieReleaseDate, movieOverview, fullPosterPath, movieVoteAverage);
                    moviePojosArrayList.add(aMovie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }//end loop
            if (mAdapter == null && moviePojosArrayList.size() != 0) {
                //Instantiating our adapter class
                mAdapter = new PopularMoviesAdapter(moviePojosArrayList, this);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.updateMoviesListWithinAdapter(moviePojosArrayList);
            }
            super.onPostExecute(allMoviesJsonArray);
        }

        @Override
        public void onclickListener(int itemClicked) {
            //Toast.makeText(getApplicationContext(), "Item click was: "+itemClicked, Toast.LENGTH_SHORT).show();
            Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetails.class);
            Movie movieClickedOn = (Movie) moviePojosArrayList.get(itemClicked);
            movieDetailsIntent.putExtra(getString(R.string.movieTitle), movieClickedOn.getMovieTitle());
            movieDetailsIntent.putExtra(getString(R.string.movieReleaseDate), movieClickedOn.getMovieReleaseDate());
            movieDetailsIntent.putExtra(getString(R.string.movieOverview), movieClickedOn.getMovieOverview());
            movieDetailsIntent.putExtra(getString(R.string.movieFullPosterPath), movieClickedOn.getMovieFullPosterPath());
            movieDetailsIntent.putExtra(getString(R.string.movieVoteAverage), movieClickedOn.getMovieVoteAverage());
            startActivity(movieDetailsIntent);
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
        if (id == R.id.most_popular) {
            URL moviesUrl = NetworkUtils.buildUrl(getString(R.string.popular_endpoint));
            //Checking if we got an internet connection prior to making network call
            if (isOnline()) {
                moviePojosArrayList.clear();
                fetchMoviesUrl(moviesUrl);
            }
            return true;
        } else if (id == R.id.top_rated) {
            URL moviesUrl = NetworkUtils.buildUrl(getString(R.string.top_rated_endpoint));
            //Checking if we got an internet connection prior to making network call
            if (isOnline()) {
                moviePojosArrayList.clear();
                fetchMoviesUrl(moviesUrl);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
