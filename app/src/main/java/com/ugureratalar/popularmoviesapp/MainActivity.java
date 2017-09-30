package com.ugureratalar.popularmoviesapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String LANGUAGE = "en-US";
    private static final int CACHE_SIZE = 20;
    private MovieGridAdapter movieAdapter;
    private MovieInfo  movieListFromApiCall;
    private GridView gridView;
    private ProgressBar progressHud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressHud = (ProgressBar)findViewById(R.id.indeterminateBar);
        gridView = (GridView)findViewById(R.id.popular_movies_gridView);
        checkConnection(POPULAR);
    }

    private void checkConnection(final String movieListType) {
        if(isOnline()) {
            progressHud.setVisibility(View.VISIBLE);
            getMovieList(movieListType);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please check your internet connection!")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            checkConnection(movieListType);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

//    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new Logger() {
//        @Override public void logging(String message) {
//            Log.d("Retrofit",message);
//        }
//    });

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

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

        Call<MovieInfo> call = service.movieList(sortType, API_KEY, LANGUAGE);
        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                movieListFromApiCall = response.body();
                Log.d("myLog", movieListFromApiCall.results.get(0).getTitle());
                movieAdapter = new MovieGridAdapter(MainActivity.this, movieListFromApiCall.results);
                gridView.setAdapter(movieAdapter);
                progressHud.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                progressHud.setVisibility(View.INVISIBLE);
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
                checkConnection(POPULAR);
                break;

            case R.id.top_rated:
                checkConnection(TOP_RATED);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
