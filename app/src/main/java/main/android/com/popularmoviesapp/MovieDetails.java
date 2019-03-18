package main.android.com.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetails extends AppCompatActivity {


    public TextView mTitle, mDate, mLength, mAbout, mFavorite, mYear;
    public ImageView mMoviePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.mMoviePic = findViewById(R.id.singleMovieView);
        mTitle = findViewById(R.id.title);
        mDate = findViewById(R.id.date);
        mLength = findViewById(R.id.movie_length);
        mAbout = findViewById(R.id.about);
        mFavorite = findViewById(R.id.favorite);
        mYear = findViewById(R.id.year);

        Intent intentThatCreatedThisActivity = getIntent();
        if(intentThatCreatedThisActivity!=null && intentThatCreatedThisActivity.hasExtra("firstname")){
            String firstName = intentThatCreatedThisActivity.getStringExtra("firstname");
            mTitle.setText(firstName);
        }
    }

}
