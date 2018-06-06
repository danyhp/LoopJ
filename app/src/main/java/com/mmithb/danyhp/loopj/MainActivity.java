package com.mmithb.danyhp.loopj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mmithb.danyhp.loopj.Adapter.MovieListAdapter;
import com.mmithb.danyhp.loopj.Adapter.RecyclerItemClickListener;
import com.mmithb.danyhp.loopj.Model.Movie;
import com.mmithb.danyhp.loopj.Service.MyLoopjTask;
import com.mmithb.danyhp.loopj.Service.OnLoopjCompleted;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnLoopjCompleted {

    public static final String MOVIE_ID = "movieID";

    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    public static List<Movie> movieList;

    EditText etSearchTerms;
    Button btnSearch;
    TextView tvSearchResults;
    MyLoopjTask myLoopjTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearchTerms = findViewById(R.id.etSearchTerms);
        btnSearch = findViewById(R.id.btnSearch);
        tvSearchResults = findViewById(R.id.tvSearchResults);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = etSearchTerms.getText().toString();
                etSearchTerms.setText("");
                movieList = new ArrayList<>();
                myLoopjTask.searchMovieLoopj(searchTerm);
            }
        });

        myLoopjTask = new MyLoopjTask(this, this);

        movieList = new ArrayList<>();
        // RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, MovieViewActivity.class);
                        TextView movieId = view.findViewById(R.id.movie_id);
                        intent.putExtra(MOVIE_ID, movieId.getText().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        updateUI();
    }


    @Override
    public void taskCompleted(JSONObject jsonObject) {
        updateUI();
    }

    public void updateUI() {
        if (!movieList.isEmpty()) {
            movieListAdapter = new MovieListAdapter(this, movieList);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(llm);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(movieListAdapter);
        }
    }
}
