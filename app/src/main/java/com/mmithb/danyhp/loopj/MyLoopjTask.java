package com.mmithb.danyhp.loopj;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MyLoopjTask {
    public static final String TAG = "MOVIE_TRIVIA";
    List<Movie> movieList;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    Context context;
    OnLoopjCompleted loopjListener;

    String API_KEY = "9509b8b5";
    String BASE_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&";
    String jsonResponse;
    JSONArray jsonArray;
    boolean jsonResultBoolean;

    public MyLoopjTask(Context context, OnLoopjCompleted listener) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        this.context = context;
        this.loopjListener = listener;
        movieList = new ArrayList<>();
    }

    public List<Movie> executeLoopjCall(String queryTerm) {

        requestParams.put("s", queryTerm);
        asyncHttpClient.get(BASE_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                movieList = MainActivity.movieList;
                movieList.clear();
                super.onSuccess(statusCode, headers, response);
                try {
                    jsonResultBoolean = response.getBoolean("Response");
                    jsonResponse = response.toString();
                    Log.i(TAG, "onSuccess: " + jsonResponse);
                    if (jsonResultBoolean) {
                        jsonArray = response.getJSONArray("Search");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String movieTitle = object.getString("Title");
                            String movieYear = object.getString("Year");
                            String moviePoster = object.getString("Poster");
                            Movie movie = new Movie(movieTitle, movieYear, moviePoster);
                            movieList.add(movie);
                            Log.d(TAG, movieTitle + " - " + movieYear + " - " + moviePoster);
                        }
                    } else {
                        Toast.makeText(context, "Error: " + response.getString("Error"), Toast.LENGTH_SHORT).show();
                    }
                    loopjListener.taskCompleted(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: " + errorResponse);
            }
        });
        return movieList;
    }
}
