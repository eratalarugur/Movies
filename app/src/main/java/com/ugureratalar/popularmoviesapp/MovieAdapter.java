package com.ugureratalar.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u. on 17/09/2017.
 */



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieInfoViewHolder> {

    Movie movieSelected;
    List<Movie> movies;
    Context context;
    private final String MOVIE = "movieSelected";

    public static class MovieInfoViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        ImageView moviewImage;
        FrameLayout frameLayout;

        public MovieInfoViewHolder(View itemView) {
            super(itemView);
            frameLayout = (FrameLayout)itemView.findViewById(R.id.frm_root);
            moviewImage = (ImageView)itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            /* Called whenever user clicks on an item in the list..*/

        }
    }

    public MovieAdapter (Context contextMovie) {
        context = contextMovie;

    }

    @Override
    public MovieInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForMovieListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForMovieListItem, viewGroup, shouldAttachToParentImmediately);
        MovieInfoViewHolder viewHolder = new MovieInfoViewHolder(view);
        return viewHolder;
    }


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public void onBindViewHolder(MovieInfoViewHolder holder, int position) {

        final Movie movie = movies.get(position);
        String movieUrl="http://image.tmdb.org/t/p/w1000/"+movie.getPoster_path();
        Log.d("logMovie","url: "+movieUrl);

        Picasso.with(context).load(movieUrl).into(holder.moviewImage);

        holder.frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Class movieDetailActivity = MovieDetailActivity.class;
                Intent movieDetailIntent = new Intent(context, movieDetailActivity);
                movieDetailIntent.putExtra(MOVIE, movie);
                context.startActivity(movieDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movies==null) {
            return 0;
        }
        return movies.size();
    }
}

