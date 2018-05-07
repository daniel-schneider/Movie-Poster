package com.popular.movies.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.utilities.ImageLoader;

/**
 * Created by danielschneider on 5/7/18.
 */

public class MovieDetailActivity extends Activity {

    private MovieListItem mMovieListItem;

    public MovieDetailActivity() {
    }


    public MovieDetailActivity(MovieListItem movieListItem) {
        this.mMovieListItem = movieListItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.movie_detail);
        TextView titleTv = findViewById(R.id.movie_title_tv);
        titleTv.setText(mMovieListItem.getTitle());
        ImageView posterIv = findViewById(R.id.detail_movie_poster);
        ImageLoader.loadRoundedFitImageWithPlaceholder(this, mMovieListItem.getImageUrl(), posterIv, R.color.colorAccent, 0);
        TextView yearTv = findViewById(R.id.year_tv);
        TextView lengthTv = findViewById(R.id.length_tv);
        TextView ratingTv = findViewById(R.id.rating_tv);
        Double voteAverage = mMovieListItem.getVoteAverage();
        ratingTv.setText(voteAverage.toString());
    }
}
