package com.ugureratalar.popularmoviesapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends Activity {

    @BindView(R.id.im_movie_detail)
    ImageView imMovieDetail;
    @BindView(R.id.im_rate)
    ImageView imRate;
    @BindView(R.id.tv_moview_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_movie_description)
    TextView tvMovieDescription;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        movie = (Movie) bundle.getSerializable("movieSelected");
        String movieBackdropPath = "http://image.tmdb.org/t/p/w1000/"+movie.getBackdrop_path();
        Log.d("backDropImage: ", movieBackdropPath);
        if (isOnline()) { Picasso.with(this).load(movieBackdropPath).into(imMovieDetail); }

        tvMovieDescription.setText(movie.getOverview());
        tvMovieTitle.setText(movie.getTitle() + "  ("+getRelaseYear(movie.getReleaseDate())+")");
    }

    String getRelaseYear (String releaseDate) {
        String[] seperatedDate = releaseDate.split("-");
        return seperatedDate[0];
    }

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
}
