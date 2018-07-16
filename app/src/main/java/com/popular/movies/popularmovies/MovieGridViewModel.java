package com.popular.movies.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.popular.movies.popularmovies.data.Database;
import com.popular.movies.popularmovies.data.Movie;
import com.popular.movies.popularmovies.model.MovieListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by danielschneider on 5/6/18.
 */

public class MovieGridViewModel extends ViewModel {

    private List<MovieListItem> getMovieListData(String movieString, Context context) {
        List<MovieListItem> movieList = new ArrayList<>();

        if (movieString == null || movieString.isEmpty()) {
            return null;
        }

        try {
            JSONObject movieJson = new JSONObject(movieString);
            JSONArray resultsArray = movieJson.getJSONArray("results");
            for(int i = 0; i < resultsArray.length(); i++) {
                MovieListItem movieListItem = new MovieListItem();
                JSONObject movieObject = resultsArray.getJSONObject(i);
                movieListItem.setId(movieObject.getString("id"));
                movieListItem.setImageUrl(movieObject.getString("poster_path"));
                movieListItem.setTitle(movieObject.getString("title"));
                movieListItem.setVoteCount(movieObject.getInt("vote_count"));
                movieListItem.setVoteAverage(movieObject.getDouble("vote_average"));
                movieListItem.setVotePopularity(movieObject.getDouble("popularity"));
                movieListItem.setOverview(movieObject.getString("overview"));
                movieListItem.setReleaseDate(movieObject.getString("release_date"));
                movieList.add(movieListItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public Single<List<MovieListItem>> getMovielist(String movieJson, Context context) {
        return Single.fromCallable(() -> getMovieListData(movieJson, context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    public Single<LiveData<List<MovieListItem>>> getFavoriteslist(Context context) {
//        return Single.fromCallable(() -> getFavoritesFromDb(context))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    public LiveData<List<Movie>> getFavoritesFromDb(Context context) {
        LiveData<List<Movie>> favoriteMovies = Database.getAppDatabase(context).movieDao().getFavorites();
//        LiveData<List<MovieListItem>> favoriteList = new ArrayList<>();
//
//        for(int i = 0; i < favoriteMovies.size(); i++) {
//            MovieListItem favoriteListItem = new MovieListItem();
//            Movie movieObject = favoriteMovies.get(i);
//
//            favoriteListItem.setId(movieObject.getMovieId());
//            favoriteListItem.setImageUrl(movieObject.getPosterPath());
//            favoriteListItem.setTitle(movieObject.getMovieName());
//            favoriteListItem.setVoteCount(movieObject.getVoteCount());
//            favoriteListItem.setVoteAverage(movieObject.getVoteAverage());
//            favoriteListItem.setVotePopularity(movieObject.getPopularity());
//            favoriteListItem.setOverview(movieObject.getOverview());
//            favoriteListItem.setReleaseDate(movieObject.getReleaseDate());
//            favoriteList.getValue().add(favoriteListItem);
//        }
        return favoriteMovies;
    }
}
