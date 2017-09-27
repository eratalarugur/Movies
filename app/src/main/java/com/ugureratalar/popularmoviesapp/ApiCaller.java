package com.ugureratalar.popularmoviesapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by u. on 20/09/2017.
 */

public interface ApiCaller {

    @GET("/3/movie/{sortType}")
    Call<MovieInfo> movieList (@Path("sortType") String sortType,
                                  @Query("api_key") String apiKey,
                                  @Query("page") String page,
                                  @Query("language") String language);


}
