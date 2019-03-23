package main.android.com.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {


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
        if (intentThatCreatedThisActivity != null &&
                intentThatCreatedThisActivity.hasExtra(getString(R.string.movieTitle)) &&
                intentThatCreatedThisActivity.hasExtra(getString(R.string.movieReleaseDate)) &&
                intentThatCreatedThisActivity.hasExtra(getString(R.string.movieOverview)) &&
                intentThatCreatedThisActivity.hasExtra(getString(R.string.movieFullPosterPath)) &&
                intentThatCreatedThisActivity.hasExtra(getString(R.string.movieVoteAverage))) {
            String mMovieTitle = intentThatCreatedThisActivity.getStringExtra(getString(R.string.movieTitle));
            String mMovieReleaseDate = intentThatCreatedThisActivity.getStringExtra(getString(R.string.movieReleaseDate));
            String mMovieOverview = intentThatCreatedThisActivity.getStringExtra(getString(R.string.movieOverview));
            String mMovieFullPosterPath = intentThatCreatedThisActivity.getStringExtra(getString(R.string.movieFullPosterPath));
            String movieVoteAverage = intentThatCreatedThisActivity.getStringExtra(getString(R.string.movieVoteAverage));
            mTitle.setText(mMovieTitle);
            mReleaseDate.setText(mMovieReleaseDate);
            mOverview.setText(mMovieOverview);
            mMovieVoteAverage.setText(movieVoteAverage + getString(R.string.forwardSlashTen));
            Picasso.get()
                    .load(mMovieFullPosterPath)
                    .fit()
                    .centerCrop()
                    .into(mMoviePic);
        }
    }

}
