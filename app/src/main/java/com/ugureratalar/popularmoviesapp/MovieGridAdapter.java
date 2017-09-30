package com.ugureratalar.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by u. on 27.09.2017.
 */

public class MovieGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Movie> movies;
    private final String MOVIE = "movieSelected";

    public MovieGridAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        Movie selectedMovie = movies.get(position);
        return selectedMovie;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        final Movie movie = movies.get(position);


        ImageView ivImage = (ImageView) view.findViewById(R.id.movie_image);

        String movieUrl = "http://image.tmdb.org/t/p/w500/" + movie.getPoster_path();

        if (isOnline()) {
            Picasso.with(context).load(movieUrl).into(ivImage);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class movieDetailActivity = MovieDetailActivity.class;
                Intent movieDetailIntent = new Intent(context, movieDetailActivity);
                movieDetailIntent.putExtra(MOVIE, movie);
                context.startActivity(movieDetailIntent);
            }
        });

        return view;
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
