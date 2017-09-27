package com.ugureratalar.popularmoviesapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "8cead25b70b2f6a9107a688f984efef0";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String LANGUAGE = "en-US";
    private static final int CACHE_SIZE = 20;
    private int page = 1;
    private MovieAdapter movieAdapter;
    private RecyclerView mRecylerView;
    private MovieInfo  movieListFromApiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecylerView = (RecyclerView)findViewById(R.id.popular_movies);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setHasFixedSize(false);
        mRecylerView.setItemViewCacheSize(CACHE_SIZE);
        movieAdapter = new MovieAdapter(this);

        mRecylerView.setAdapter(movieAdapter);

        getMovieList(POPULAR);
    }

//    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new Logger() {
//        @Override public void logging(String message) {
//            Log.d("Retrofit",message);
//        }
//    });

    void getMovieList(String sortType) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        ApiCaller service = retrofit.create(ApiCaller.class);

        Call<MovieInfo> call = service.movieList(sortType, API_KEY, String.valueOf(page), LANGUAGE);
        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                movieListFromApiCall = response.body();
                Log.d("myLog", movieListFromApiCall.results.get(0).getTitle());
                movieAdapter.setMovies(movieListFromApiCall.results);
                movieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popular:
                getMovieList("popular");
                break;

            case R.id.top_rated:
                getMovieList("top_rated");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
