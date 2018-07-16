package com.popular.movies.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.popular.movies.popularmovies.data.Database;
import com.popular.movies.popularmovies.data.Movie;
import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.reviews.ReviewsActivity;
import com.popular.movies.popularmovies.trailers.TrailersAdapter;
import com.popular.movies.popularmovies.trailers.model.TrailerViewModel;
import com.popular.movies.popularmovies.utilities.ImageLoader;
import com.popular.movies.popularmovies.utilities.NetworkUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by danielschneider on 5/7/18.
 */

public class MovieDetailActivity extends FragmentActivity implements TrailersAdapter.ItemClickListener {

    public static final String TAG = MovieDetailActivity.class.getSimpleName();
    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_MOVIE_BUNDLE_ITEM = "movieItem_bundle";
    private static final String DEFAULT_MOVIE_ID = "default";
    private String mMovieId = DEFAULT_MOVIE_ID;

    RecyclerView.LayoutManager mLayoutManager;
    TrailersAdapter mTrailerAdapter;

    Toolbar mToolbar;
    CompositeDisposable mCompositDisposable;
    RecyclerView mTrailerRecyclerView;

    private MovieListItem mMovieListItem;
    private Movie mMovie;
    private boolean mFavorited = false;
    Database mDb;

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

        mDb = Database.getAppDatabase(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Bundle data = getIntent().getExtras();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EXTRA_MOVIE_BUNDLE_ITEM)) {
                mMovieListItem = (MovieListItem) savedInstanceState.getParcelable(EXTRA_MOVIE_BUNDLE_ITEM);
            }
        } else {
            mMovieListItem = (MovieListItem) data.getParcelable(EXTRA_MOVIE);
        }

        setupViewModel();

        if (!isMovieInDb()) {
            mMovie = new Movie(mMovieListItem, false);
            addMovieToDb(mMovie, mFavorited);
            mFavorited = mMovie.getFavorited();
        } else {
            mMovie = mDb.movieDao().getMovieObject(mMovieListItem.getId());
            mFavorited = mMovie.getFavorited();
        }

        Log.i(TAG, mMovieListItem.getTitle() + "'s ID: " + mMovieListItem.getId());
        populateUi(mMovieListItem);

        Button favoritesButton = findViewById(R.id.favorites_button);
        setFavoriteButtonText(favoritesButton);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
                setFavoriteButtonText(favoritesButton);
            }
        });

        Button reviewButton = findViewById(R.id.reviews_button);

        if (hasReviews()) {
            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MovieDetailActivity.this, ReviewsActivity.class);
                    intent.putExtra(ReviewsActivity.EXTRA_MOVIE, mMovieListItem);
                    startActivity(intent);
                }
            });
        } else {
            reviewButton.setVisibility(View.GONE);
        }
        setFavoriteButtonText(favoritesButton);


    }

    private ViewModel setupViewModel() {
        MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(mDb, mMovieListItem.getId());
        final MovieDetailViewModel viewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);
        viewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (mFavorited) {
                    updateMovie(movie, mFavorited);
                    mFavorited = movie.getFavorited();
                } else {
                    updateMovie(movie, false);
                }
                viewModel.getMovie().removeObserver(this);
            }
        });
        return viewModel;
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
        createTrailerList();
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

    private void addMovieToDb(MovieListItem movieListItem, boolean favorited) {
        Movie movie = new Movie(movieListItem.getId(), movieListItem.getTitle(), movieListItem.getVoteCount(),
                movieListItem.getVoteAverage(), movieListItem.getVotePopularity(), movieListItem.getImageUrl(),
                movieListItem.getOverview(), movieListItem.getReleaseDate(), favorited);

        mDb.movieDao().addMovie(movie);
    }

    private void addMovieToDb(Movie movie, boolean favorited) {
        movie.setFavorited(favorited);
        mDb.movieDao().addMovie(movie);
    }

    private void updateMovie(Movie movie, boolean favorited) {
        movie.setFavorited(favorited);
        mDb.movieDao().updateMovie(movie);
    }

    private void removeMovieFromFavortites() {

        mMovie.setFavorited(false);
//        movie.setFavorited(false);
        mDb.movieDao().updateMovie(mMovie);
        mFavorited = false;
    }

    private void createTrailerList() {
        mCompositDisposable = new CompositeDisposable();
        String trailerString = NetworkUtilities.getMovieTrailors(mMovieListItem.getId());
        mTrailerRecyclerView = findViewById(R.id.movie_trailors_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        TrailerViewModel trailorViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);

        if (NetworkUtilities.isDeviceConnected(this)) {
            mCompositDisposable.add(trailorViewModel.getMovielist(trailerString).subscribe((trailorListItems -> {
                mTrailerRecyclerView.setLayoutManager(mLayoutManager);
                mTrailerAdapter = new TrailersAdapter(this, trailorListItems);
                mTrailerAdapter.setClickListener(this);
                mTrailerRecyclerView.setAdapter(mTrailerAdapter);

            }), throwable -> {
                Toast.makeText(this, "no internet connection", Toast.LENGTH_LONG).show();

            }));
        } else {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private boolean hasReviews() {
        String reviewString = NetworkUtilities.getMovieReview(mMovieListItem.getId());
        try {
            JSONObject json = new JSONObject(reviewString);
            JSONArray results = json.getJSONArray("results");

            if (results.length() == 0) {
                return false;
            } else {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setFavoriteButtonText(Button favoriteButton) {
        if (mFavorited) {
            favoriteButton.setText("remove from favorites");
        } else {
            favoriteButton.setText("add to favorites");
        }
    }

    private boolean isMovieInDb() {
        return  mDb.movieDao().getMovieObject(mMovieListItem.getId()) != null;
    }

    private void onSaveButtonClicked() {
        if (mFavorited) {
            mFavorited = false;
            updateMovie(mMovie, mFavorited);
        } else {
            mFavorited = true;
            updateMovie(mMovie ,mFavorited);
        }
    }
}
