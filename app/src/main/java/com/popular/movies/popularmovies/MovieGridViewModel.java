package com.popular.movies.popularmovies;

import android.arch.lifecycle.ViewModel;

import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.utilities.NetworkUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielschneider on 5/6/18.
 */

public class MovieGridViewModel extends ViewModel {

    public List<MovieListItem> getMovieListData() {
        List<MovieListItem> movieList = new ArrayList<>();

        String movieJsonString = NetworkUtilities.getPopularMovies();

        try {
            JSONObject movieJson = new JSONObject(movieJsonString);
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
                movieList.add(movieListItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }
}
