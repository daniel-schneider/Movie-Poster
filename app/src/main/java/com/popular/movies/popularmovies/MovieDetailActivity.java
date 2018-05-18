package com.popular.movies.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.utilities.ImageLoader;

/**
 * Created by danielschneider on 5/7/18.
 */

public class MovieDetailActivity extends Activity {

    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_MOVIE_BUNDLE_ITEM = "movieItem_bundle";
    Toolbar mToolbar;



    private MovieListItem mMovieListItem;

    public MovieDetailActivity() {

    }


    public MovieDetailActivity(MovieListItem movieListItem) {
        this.mMovieListItem = movieListItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Bundle data = getIntent().getExtras();

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(EXTRA_MOVIE_BUNDLE_ITEM)) {
                mMovieListItem = (MovieListItem) savedInstanceState.getParcelable(EXTRA_MOVIE_BUNDLE_ITEM);
            }
        } else {
            mMovieListItem = (MovieListItem) data.getParcelable(EXTRA_MOVIE);
        }


        populateUi(mMovieListItem);
    }

    private void populateUi(MovieListItem movieListItem) {
        mToolbar.setTitle(movieListItem.getTitle());
//        TextView titleTv = findViewById(R.id.movie_title_tv);
//        titleTv.setText(movieListItem.getTitle());
        ImageView posterIv = findViewById(R.id.detail_movie_poster);
        ImageLoader.loadImage(this, movieListItem.getDetailImageUrl(), posterIv);
        TextView yearTv = findViewById(R.id.date_tv);
        yearTv.setText(movieListItem.getReleaseDate());
        TextView lengthTv = findViewById(R.id.length_tv);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        double voteAverage = movieListItem.getVoteAverage();
        float va = (float) voteAverage;
        ratingBar.setRating(va);
        TextView descriptionText = findViewById(R.id.movie_description_tv);
        descriptionText.setText(movieListItem.getOverview());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(EXTRA_MOVIE_BUNDLE_ITEM, mMovieListItem);

    }
}
