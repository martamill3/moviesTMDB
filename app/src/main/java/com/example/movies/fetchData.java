package com.example.movies;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fetchData extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected String dataResults = "";
    protected String movie;

    public fetchData(String movie) {
        this.movie = movie;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=9d3d87dfab03093a4a78ce77f8b3072b&query=" + movie.replaceAll(" ", "+"));
            Log.i("logtest", "https://api.themoviedb.org/3/search/movie?api_key=9d3d87dfab03093a4a78ce77f8b3072b&query=" + movie.replaceAll(" ", "+"));

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();

            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            data = sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(data);

            JSONArray results = new JSONArray(jObject.getString("results"));
            for(int i=0; i<results.length(); i++){
                JSONObject movie = new JSONObject(results.getString(i));
                String title  = movie.getString("title");
                Log.i("logTest",  title);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
