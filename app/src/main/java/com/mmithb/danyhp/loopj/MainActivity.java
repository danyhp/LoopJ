package com.mmithb.danyhp.loopj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnLoopjCompleted {

    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private LinkedList<Movie> movieLinkedList;

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

        btnSearch.setOnClickListener(this);

        myLoopjTask = new MyLoopjTask(this, this);

        movieLinkedList = new LinkedList<>();
        movieListAdapter = new MovieListAdapter(this, movieLinkedList);

        // RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerview);
        updateUI();

    }

    @Override
    public void onClick(View v) {
        String searchTerm = etSearchTerms.getText().toString();
        etSearchTerms.setText("");
        // make loopj HTTP call
        movieLinkedList = myLoopjTask.executeLoopjCall(searchTerm);
        movieListAdapter = new MovieListAdapter(this, movieLinkedList);
        updateUI();
        movieListAdapter.notifyDataSetChanged();

    }

    @Override
    public void taskCompleted(String results) {
        tvSearchResults.setText(results);
        movieLinkedList.clear();
    }

    public void updateUI() {
        if (!movieLinkedList.isEmpty()) {
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(movieListAdapter);
        }
    }
}
