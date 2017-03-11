package com.thenewboston.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.thenewboston.movies.R.id.text;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.List);
        listView.setOnItemClickListener(this);

        new checkConnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=89d9d5f2f4659107342683107e99622f&language=en-US&page=1");


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this,MovieDetailActivity.class);
        intent.putExtra("MOVIE_DETAILS",(MoviesDetails) parent.getItemAtPosition(position));
        startActivity(intent);
    }

    class checkConnectionStatus extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;

            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String s = bufferedReader.readLine();
                bufferedReader.close();
                return s;

            } catch (IOException e) {

                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                ArrayList<MoviesDetails> movieList = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    MoviesDetails moviesDetails = new MoviesDetails();
                    moviesDetails.setOriginal_title(object.getString("original_title"));
                    moviesDetails.setVote_average(object.getDouble("vote_average"));
                    moviesDetails.setRelease_date(object.getString("release_date"));
                    moviesDetails.setOverview(object.getString("overview"));
                    moviesDetails.setPoster_path(object.getString("poster_path"));
                    movieList.add(moviesDetails);


                }
                MovieArrayAdapter movieArrayAdapter = new MovieArrayAdapter(MainActivity.this,R.layout.movie_list,movieList);
                listView.setAdapter(movieArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
