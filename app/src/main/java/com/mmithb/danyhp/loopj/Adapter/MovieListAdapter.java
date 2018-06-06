package com.mmithb.danyhp.loopj.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmithb.danyhp.loopj.Model.Movie;
import com.mmithb.danyhp.loopj.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private final List<Movie> movieList;
    private LayoutInflater layoutInflater;
    private String noPoster = "https://upmaa-pennmuseum.netdna-ssl.com/collections/images/image_not_available_300.jpg";
    private Context context;

    public MovieListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = movieList.get(position);
        holder.movieTitleView.setText(currentMovie.getMovieTitle());
        holder.movieYearView.setText(currentMovie.getMovieYear());
        if (currentMovie.getMoviePosterUrl().length() < 5) {
            Picasso.with(context)
                    .load(noPoster)
                    .into(holder.moviePosterView);
        } else {
            Picasso.with(context)
                    .load(currentMovie.getMoviePosterUrl())
                    .into(holder.moviePosterView);
        }
    }


    @Override
    public int getItemCount() {
        if (!movieList.isEmpty()) {
            return movieList.size();
        }
        return 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        final TextView movieTitleView, movieYearView;
        final ImageView moviePosterView;
        final MovieListAdapter movieListAdapter;

        MovieViewHolder(View itemView, MovieListAdapter movieListAdapter) {
            super(itemView);
            movieTitleView = itemView.findViewById(R.id.movie_title);
            movieYearView = itemView.findViewById(R.id.movie_year);
            moviePosterView = itemView.findViewById(R.id.movie_poster);
            this.movieListAdapter = movieListAdapter;

        }
    }

}
