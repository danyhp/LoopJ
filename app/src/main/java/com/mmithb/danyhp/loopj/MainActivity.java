package com.mmithb.danyhp.loopj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnLoopjCompleted {

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
    }

    @Override
    public void onClick(View v) {
        String searchTerm = etSearchTerms.getText().toString();
        etSearchTerms.setText("");
        // make loopj HTTP call
        myLoopjTask.executeLoopjCall(searchTerm);
    }

    @Override
    public void taskCompleted(String results) {
        tvSearchResults.setText(results);
    }
}
