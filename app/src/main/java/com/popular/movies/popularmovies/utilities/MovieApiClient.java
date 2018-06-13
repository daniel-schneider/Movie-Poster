package com.popular.movies.popularmovies.utilities;

import retrofit2.Response;
import io.reactivex.Single;

import retrofit2.http.GET;

/**
 * Created by danielschneider on 5/2/18.
 */

public interface MovieApiClient {

    String TAG = MovieApiClient.class.getSimpleName();

    @GET("{api_key}/{language}/{page}/{region}")
    Single<Response<String>> downloadMovieData ();


}
