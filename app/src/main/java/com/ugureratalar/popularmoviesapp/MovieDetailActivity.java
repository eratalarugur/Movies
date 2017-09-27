package com.ugureratalar.popularmoviesapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

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
        Picasso.with(this).load(movieBackdropPath).into(imMovieDetail);

        tvMovieDescription.setText(movie.getOverview());
        tvMovieTitle.setText(movie.getTitle());

    }
}
