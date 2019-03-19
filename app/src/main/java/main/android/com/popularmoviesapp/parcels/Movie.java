package main.android.com.popularmoviesapp.parcels;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

    private String movieTitle, movieReleaseDate, movieOverview, movieFullPosterPath;

    public Movie(String movieTitle, String movieReleaseDate, String movieOverview, String moviePosterPath) {
        this.movieTitle = movieTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieOverview = movieOverview;
        this.movieFullPosterPath = moviePosterPath;
    }

    public Movie(Parcel parcel){
        this.movieTitle = parcel.readString();
        this.movieReleaseDate = parcel.readString();
        this.movieOverview = parcel.readString();
        this.movieFullPosterPath = parcel.readString();
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieFullPosterPath() {
        return movieFullPosterPath;
    }

    public void setMovieFullPosterPath(String movieFullPosterPath) {
        this.movieFullPosterPath = movieFullPosterPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieTitle);
        parcel.writeString(movieReleaseDate);
        parcel.writeString(movieOverview);
        parcel.writeString(movieFullPosterPath);
    }

    Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
