package main.android.com.popularmoviesapp.models;

public class Movie {

    private String movieTitle;
    private String movieDate;
    private String movieLength;
    private String movieReleaseYear;
    private String movieAboutText;
    private String moviePosterPath;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(String movieLength) {
        this.movieLength = movieLength;
    }

    public String getMovieReleaseYear() {
        return movieReleaseYear;
    }

    public void setMovieReleaseYear(String movieReleaseYear) {
        this.movieReleaseYear = movieReleaseYear;
    }

    public String getMovieAboutText() {
        return movieAboutText;
    }

    public void setMovieAboutText(String movieAboutText) {
        this.movieAboutText = movieAboutText;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public Movie(String movieTitle, String movieDate, String movieLength, String movieReleaseYear, String movieAboutText, String moviePosterPath) {
        this.movieTitle = movieTitle;
        this.movieDate = movieDate;
        this.movieLength = movieLength;
        this.movieReleaseYear = movieReleaseYear;
        this.movieAboutText = movieAboutText;
        this.moviePosterPath = moviePosterPath;

    }
}
