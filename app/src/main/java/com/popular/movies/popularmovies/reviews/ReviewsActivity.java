package com.popular.movies.popularmovies.reviews;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.popular.movies.popularmovies.R;
import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.reviews.model.ReviewViewModel;
import com.popular.movies.popularmovies.utilities.NetworkUtilities;

import io.reactivex.disposables.CompositeDisposable;

public class ReviewsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";

    private MovieListItem mMovieListItem;
    private CompositeDisposable mCompositDisposable;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mReviewRecyclerView;
    private ReviewsAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Bundle data = getIntent().getExtras();
        mMovieListItem = (MovieListItem) data.getParcelable(EXTRA_MOVIE);


//        if(savedInstanceState != null) {
//            if(savedInstanceState.containsKey(EXTRA_MOVIE_BUNDLE_ITEM)) {
//                mMovieListItem = (MovieListItem) savedInstanceState.getParcelable(EXTRA_MOVIE_BUNDLE_ITEM);
//            }
//        } else {
//            mMovieListItem = (MovieListItem) data.getParcelable(EXTRA_MOVIE);
//        }



        createReviewList();
    }

    private void createReviewList() {
        mCompositDisposable = new CompositeDisposable();
        String reviewString = NetworkUtilities.getMovieReview(mMovieListItem.getId());
        mReviewRecyclerView = findViewById(R.id.reviews_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        ReviewViewModel reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);

        if (NetworkUtilities.isDeviceConnected(this)) {
            mCompositDisposable.add(reviewViewModel.getReviewlist(reviewString).subscribe((reviewListItems -> {
                mReviewRecyclerView.setLayoutManager(mLayoutManager);
                mReviewAdapter = new ReviewsAdapter(this, reviewListItems);
                mReviewRecyclerView.setAdapter(mReviewAdapter);

            }), throwable -> {
                Toast.makeText(this, "no internet connection", Toast.LENGTH_LONG).show();

            }));
        } else {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_LONG).show();
        }


    }

}
