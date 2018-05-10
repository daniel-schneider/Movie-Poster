package com.popular.movies.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.utilities.ImageLoader;

/**
 * Created by danielschneider on 5/7/18.
 */

public class MovieDetailActivity extends Activity {

    public static final String EXTRA_MOVIE = "movie";


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

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Bundle data = getIntent().getExtras();
        MovieListItem movieListItem = (MovieListItem) data.getParcelable(EXTRA_MOVIE);

        populateUi(movieListItem);
    }

    private void populateUi(MovieListItem movieListItem) {
        TextView titleTv = findViewById(R.id.movie_title_tv);
        titleTv.setText(movieListItem.getTitle());
        ImageView posterIv = findViewById(R.id.detail_movie_poster);
        ImageLoader.loadImage(this, movieListItem.getDetailImageUrl(), posterIv);
        TextView yearTv = findViewById(R.id.year_tv);
        TextView lengthTv = findViewById(R.id.length_tv);
        TextView ratingTv = findViewById(R.id.rating_tv);
        Double voteAverage = movieListItem.getVoteAverage();
        ratingTv.setText(voteAverage.toString());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }
}
