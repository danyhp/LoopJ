package com.mmithb.danyhp.loopj.Model;

public class Movie {
    private String movieTitle;
    private String movieYear;
    private String moviePosterUrl;

    public Movie(String movieTitle, String movieYear, String moviePosterUrl) {
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.moviePosterUrl = moviePosterUrl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }
}
