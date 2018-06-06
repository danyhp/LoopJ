package com.mmithb.danyhp.loopj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmithb.danyhp.loopj.Service.MyLoopjTask;
import com.mmithb.danyhp.loopj.Service.OnLoopjCompleted;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieViewActivity extends AppCompatActivity implements OnLoopjCompleted {

    private TextView mTitle, mYear, mRating, mRated, mRuntime, mGenre, mRelease, mPlot;
    private TextView mDirector, mWriters, mStars;
    private ImageView mPoster;
    MyLoopjTask myLoopjTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        Intent intent = getIntent();
        String searchTerm = intent.getStringExtra(MainActivity.MOVIE_ID);

        Log.d(MyLoopjTask.TAG, searchTerm);

        findViewById(R.id.root_layout).setVisibility(View.GONE);

        mPoster = findViewById(R.id.movie_poster);
        mTitle = findViewById(R.id.movie_title);
        mYear = findViewById(R.id.movie_year);
        mRating = findViewById(R.id.ratingValue);
        mRated = findViewById(R.id.rated);
        mRuntime = findViewById(R.id.runtime);
        mGenre = findViewById(R.id.genre);
        mRelease = findViewById(R.id.release);
        mPlot = findViewById(R.id.plot);

        mDirector = findViewById(R.id.director);
        mWriters = findViewById(R.id.writer);
        mStars = findViewById(R.id.stars);

        myLoopjTask = new MyLoopjTask(this, this);
        myLoopjTask.searchSpecificMovieLoopj(searchTerm);
    }


    @Override
    public void taskCompleted(JSONObject jsonObject) {
        try {
            mTitle.setText(jsonObject.getString("Title"));
            mYear.setText(jsonObject.getString("Year"));
            mRating.setText(jsonObject.getString("imdbRating"));
            mRated.setText(jsonObject.getString("Rated"));
            mRuntime.setText(jsonObject.getString("Runtime"));
            mGenre.setText(jsonObject.getString("Genre"));
            mRelease.setText(jsonObject.getString("Released"));
            mPlot.setText(jsonObject.getString("Plot"));

            mDirector.setText(jsonObject.getString("Director"));
            mWriters.setText(jsonObject.getString("Writer"));
            mStars.setText(jsonObject.getString("Actors"));

            Picasso.with(this)
                    .load(jsonObject.getString("Poster"))
                    .into(mPoster);

            findViewById(R.id.root_layout).setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
