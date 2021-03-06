package com.mmithb.danyhp.loopj.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mmithb.danyhp.loopj.MainActivity;
import com.mmithb.danyhp.loopj.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MyLoopjTask {
    public static final String TAG = "MOVIE_TRIVIA";
    private List<Movie> movieList;

    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    private Context context;
    private OnLoopjCompleted loopjListener;

    private String API_KEY = "9509b8b5";
    private String BASE_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&";
    private String jsonResponse;
    private JSONArray jsonArray;
    private boolean jsonResultBoolean;

    public MyLoopjTask(Context context, OnLoopjCompleted listener) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        this.context = context;
        this.loopjListener = listener;
        movieList = new ArrayList<>();
    }

    public void searchMovieLoopj(String queryTerm) {
        requestParams.put("s", queryTerm);
        asyncHttpClient.get(BASE_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                movieList = MainActivity.movieList;
                movieList.clear();
                super.onSuccess(statusCode, headers, response);
                try {
                    jsonResultBoolean = checkResponse(response);
                    jsonResponse = response.toString();
                    Log.i(TAG, "onSuccess: " + jsonResponse);
                    if (jsonResultBoolean) {
                        jsonArray = response.getJSONArray("Search");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String movieTitle = object.getString("Title");
                            String movieYear = object.getString("Year");
                            String moviePoster = object.getString("Poster");
                            String movieId = object.getString("imdbID");
                            Movie movie = new Movie(movieTitle, movieYear, moviePoster, movieId);
                            movieList.add(movie);
                            Log.d(TAG, movieTitle + " - " + movieYear + " - " + moviePoster);
                        }
                        loopjListener.taskCompleted(response);
                    } else {
                        Toast.makeText(context, "Error: " + response.getString("Error"), Toast.LENGTH_SHORT).show();
                    }


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

    }

    public void searchSpecificMovieLoopj(String queryTerm) {
        requestParams.put("i", queryTerm);
        asyncHttpClient.get(BASE_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    jsonResultBoolean = checkResponse(response);
                    if (jsonResultBoolean) {
                        loopjListener.taskCompleted(response);
                    } else {
                        Toast.makeText(context, "Error: " + response.getString("Error"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
//        return new Movie(null, null, null);
    }

    private boolean checkResponse(JSONObject jsonResponse) {
        try {
            return jsonResponse.getBoolean("Response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
