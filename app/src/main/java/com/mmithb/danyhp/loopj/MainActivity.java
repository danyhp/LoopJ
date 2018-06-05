package com.mmithb.danyhp.loopj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnLoopjCompleted {

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
                // make loopj HTTP call
                movieList = new ArrayList<>();
                movieList.addAll(myLoopjTask.executeLoopjCall(searchTerm));
            }
        });

        myLoopjTask = new MyLoopjTask(this, this);

        movieList = new ArrayList<>();
        // RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerview);
        updateUI();

    }

    @Override
    public void taskCompleted(String results) {
        tvSearchResults.setText(results);
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
