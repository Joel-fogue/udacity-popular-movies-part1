package main.android.com.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {


    public TextView mTitle, mReleaseDate, mOverview, mFavorite, mMovieVoteAverage;
    public ImageView mMoviePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mMoviePic = findViewById(R.id.movieDetailPic);
        mTitle = findViewById(R.id.title);
        mReleaseDate = findViewById(R.id.year);
        mOverview = findViewById(R.id.overview);
        mFavorite = findViewById(R.id.favorite);
        mMovieVoteAverage = findViewById(R.id.vote_average);

        Intent intentThatCreatedThisActivity = getIntent();
        if(intentThatCreatedThisActivity!=null &&
            intentThatCreatedThisActivity.hasExtra("movieTitle") &&
            intentThatCreatedThisActivity.hasExtra("movieReleaseDate") &&
                intentThatCreatedThisActivity.hasExtra("movieOverview")&&
                intentThatCreatedThisActivity.hasExtra("movieFullPosterPath")&&
                intentThatCreatedThisActivity.hasExtra("movieVoteAverage") )
        {
            String mMovieTitle = intentThatCreatedThisActivity.getStringExtra("movieTitle");
            String mMovieReleaseDate = intentThatCreatedThisActivity.getStringExtra("movieReleaseDate");
            String mMovieOverview = intentThatCreatedThisActivity.getStringExtra("movieOverview");
            String mMovieFullPosterPath = intentThatCreatedThisActivity.getStringExtra("movieFullPosterPath");
            String movieVoteAverage = intentThatCreatedThisActivity.getStringExtra("movieVoteAverage");
            mTitle.setText(mMovieTitle);
            mReleaseDate.setText(mMovieReleaseDate);
            mOverview.setText(mMovieOverview);
            mMovieVoteAverage.setText(movieVoteAverage+"/10");
            Picasso.get()
                    .load(mMovieFullPosterPath)
                    .fit()
                    .centerCrop()
                    .into(mMoviePic);
        }
    }

}
