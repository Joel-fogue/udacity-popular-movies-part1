package main.android.com.popularmoviesapp.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import main.android.com.popularmoviesapp.MainActivity;
import main.android.com.popularmoviesapp.MovieDetailsActivity;
import main.android.com.popularmoviesapp.PopularMoviesAdapter;
import main.android.com.popularmoviesapp.R;
import main.android.com.popularmoviesapp.parcels.Movie;
import main.android.com.popularmoviesapp.utilities.NetworkUtils;

public class DownloadMovieUrlsAsyncTask extends AsyncTask<URL, Void, JSONArray>
        implements PopularMoviesAdapter.OnRecyclerViewClickListener {

    int len;
    private PopularMoviesAdapter mAdapter;
    ArrayList<Movie> moviePojosArrayList;
    private RecyclerView mRecyclerView;
    public Context context;

    public DownloadMovieUrlsAsyncTask(Context context, ArrayList<Movie> moviePojosArrayList, RecyclerView mRecyclerView){
        this.context = context;
        this.moviePojosArrayList = moviePojosArrayList;
        this.mRecyclerView = mRecyclerView;
    }

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
            allMoviesJsonArray = allMoviesJsonObject.getJSONArray(context.getString(R.string.results));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMoviesJsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray allMoviesJsonArray) {
        JSONArray moviesArray = allMoviesJsonArray;
        len = allMoviesJsonArray.length();
        for (int i = 0; i < allMoviesJsonArray.length(); i++) {
            JSONObject singleMovieJsonObject = null;
            try {
                singleMovieJsonObject = allMoviesJsonArray.getJSONObject(i);
                String movieTitle = singleMovieJsonObject.getString(context.getString(R.string.title));
                String movieReleaseDate = singleMovieJsonObject.getString(context.getString(R.string.release_date));
                String movieOverview = singleMovieJsonObject.getString(context.getString(R.string.overview));
                String posterPath = singleMovieJsonObject.getString(context.getString(R.string.poster_path)).split(context.getString(R.string.forward_slash))[1];
                String fullPosterPath = NetworkUtils.buildPosterPathUrl(posterPath).toString();
                String movieVoteAverage = singleMovieJsonObject.getString(context.getString(R.string.vote_average));
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
        Intent movieDetailsIntent = new Intent(context, MovieDetailsActivity.class);
        Movie movieClickedOn = (Movie) moviePojosArrayList.get(itemClicked);
        movieDetailsIntent.putExtra(context.getString(R.string.movieTitle), movieClickedOn.getMovieTitle());
        movieDetailsIntent.putExtra(context.getString(R.string.movieReleaseDate), movieClickedOn.getMovieReleaseDate());
        movieDetailsIntent.putExtra(context.getString(R.string.movieOverview), movieClickedOn.getMovieOverview());
        movieDetailsIntent.putExtra(context.getString(R.string.movieFullPosterPath), movieClickedOn.getMovieFullPosterPath());
        movieDetailsIntent.putExtra(context.getString(R.string.movieVoteAverage), movieClickedOn.getMovieVoteAverage());
        context.startActivity(movieDetailsIntent);
    }
}