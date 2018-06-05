package com.mmithb.danyhp.loopj;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MyLoopjTask {
    public static final String TAG = "MOVIE_TRIVIA";

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    Context context;
    OnLoopjCompleted loopjListener;

    String API_KEY = "9509b8b5";
    String BASE_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&";
    String jsonResponse;



    public MyLoopjTask(Context context, OnLoopjCompleted listener) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        this.context = context;
        this.loopjListener = listener;
    }

    public void executeLoopjCall(String queryTerm) {
        requestParams.put("s", queryTerm);
        asyncHttpClient.get(BASE_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                jsonResponse = response.toString();
                loopjListener.taskCompleted(jsonResponse);
                Log.i(TAG, "onSuccess: " + jsonResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: " + errorResponse);
            }
        });
    }
}
