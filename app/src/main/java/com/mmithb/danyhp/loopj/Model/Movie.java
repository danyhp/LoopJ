package com.mmithb.danyhp.loopj.Model;

public class Movie {
    private String movieTitle;
    private String movieYear;
    private String moviePosterUrl;
    private String movieId;

    public Movie(String movieTitle, String movieYear, String moviePosterUrl, String movieId) {
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.moviePosterUrl = moviePosterUrl;
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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
