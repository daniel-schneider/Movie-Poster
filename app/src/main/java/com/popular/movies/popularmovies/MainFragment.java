package com.popular.movies.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.popular.movies.popularmovies.data.Movie;
import com.popular.movies.popularmovies.utilities.NetworkUtilities;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by danielschneider on 5/6/18.
 */

public class MainFragment extends android.support.v4.app.Fragment implements MovieGridAdapter.ItemClickListener {

    private static final String SORT_FAVORITE = "user_favorites";
    MovieGridAdapter mMovieGridAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    private CompositeDisposable mCompositDisposable;
    public Parcelable mListState = null;
    ProgressBar mProgressBar = null;
    private static final String SORT_POPULAR = "popular";
    private static final String SORT_TOP_RATED = "top_rated";
    private static String mCurrentSort;
    private static final String CURRENT_SORT_KEY = "CURRENT_SORT_KEY";
    int mLastFirstVisiblePosition;
    private static final String FIRST_VISIBLE_POSITION_KEY = "FIRST_VISIBLE_POSITION_KEY";



    public final static String TAG = MainFragment.class.getSimpleName();
    public static final String LIST_STATE_KEY = "LIST_STATE_KEY";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(LIST_STATE_KEY)) {
                mLayoutManager.onRestoreInstanceState(mListState);

            }
            if(savedInstanceState.containsKey(CURRENT_SORT_KEY)) {
                mCurrentSort = savedInstanceState.getString(CURRENT_SORT_KEY, SORT_POPULAR);

            }
        } else {
            mCurrentSort = SORT_POPULAR;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCompositDisposable = new CompositeDisposable();
        checkConfig();


        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(LIST_STATE_KEY)) {
                mLayoutManager.onRestoreInstanceState(mListState);
            }
            if(savedInstanceState.containsKey(FIRST_VISIBLE_POSITION_KEY)) {
                mLastFirstVisiblePosition = savedInstanceState.getInt(FIRST_VISIBLE_POSITION_KEY);
                mRecyclerView.getLayoutManager().scrollToPosition(mLastFirstVisiblePosition);

            }
        }

        mProgressBar = view.findViewById(R.id.main_progress_bar);
        mRecyclerView = view.findViewById(R.id.movie_posters_recycler_view);
        String movieJsonString = NetworkUtilities.getPopularMovies();


        MovieGridViewModel movieGridViewModel = ViewModelProviders.of(this).get(MovieGridViewModel.class);

        if (NetworkUtilities.isDeviceConnected(getContext())) {
            mCompositDisposable.add(movieGridViewModel.getMovielist(movieJsonString, getContext()).subscribe((movieListItems -> {
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mMovieGridAdapter = new MovieGridAdapter(getActivity(), movieListItems);
                mMovieGridAdapter.setClickListener(this);
                mRecyclerView.setAdapter(mMovieGridAdapter);

            }), throwable -> {
                Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_LONG).show();

            }));
        } else {
            Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_LONG).show();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieGridAdapter.getMovieListItemForPosition(position));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(FIRST_VISIBLE_POSITION_KEY, mLastFirstVisiblePosition);


        if (mListState != null) {
            mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LIST_STATE_KEY, mListState);
            outState.putString(CURRENT_SORT_KEY, mCurrentSort);
        }
    }



    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mCurrentSort = savedInstanceState.getString(CURRENT_SORT_KEY);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Retrieve list state and list/item positions
        if(savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }


    private void checkConfig() {
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        }
        else{
            mLayoutManager = new GridLayoutManager(getActivity(), 4);
        }

        if(mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
            mRecyclerView.getLayoutManager().scrollToPosition(mLastFirstVisiblePosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mLastFirstVisiblePosition =  ((GridLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();


        Bundle bundle = new Bundle();
        if (mListState != null) {
            mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
            bundle.putParcelable(LIST_STATE_KEY, mListState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
            mRecyclerView.getLayoutManager().scrollToPosition(mLastFirstVisiblePosition);

        }

        MovieGridViewModel movieGridViewModel = ViewModelProviders.of(this).get(MovieGridViewModel.class);

        if (NetworkUtilities.isDeviceConnected(getContext())) {
            if(mCurrentSort.equals(SORT_POPULAR)) {
                movieGridViewModel.getMovielist(NetworkUtilities.getPopularMovies(), getContext());
            } else if(mCurrentSort.equals(SORT_TOP_RATED)) {
                movieGridViewModel.getMovielist(NetworkUtilities.getHighestRatedMovies(), getContext());
            } else if (mCurrentSort.equals((SORT_FAVORITE))) {
                movieGridViewModel.getFavoritesFromDb(getContext());
                mMovieGridAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.filter_type, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        MovieGridViewModel movieGridViewModel = ViewModelProviders.of(this).get(MovieGridViewModel.class);
        item.setChecked(true);


        if(id == R.id.action_popular && !mCurrentSort.equals(SORT_POPULAR)) {
            mCurrentSort = SORT_POPULAR;
            mCompositDisposable.clear();
            mCompositDisposable.add(movieGridViewModel.getMovielist(NetworkUtilities.getPopularMovies(), getContext()).subscribe(movieListItems ->
            {
                if (mMovieGridAdapter != null) {
                    mMovieGridAdapter.clearAllMovieData();
                    mMovieGridAdapter.loadHighestRatedMovies(movieListItems);
                    mMovieGridAdapter.notifyDataSetChanged();
                } else {
                    mMovieGridAdapter = new MovieGridAdapter(getActivity(), movieListItems);
                    mMovieGridAdapter.setClickListener(this);
                    mRecyclerView.setAdapter(mMovieGridAdapter);
                    mMovieGridAdapter.notifyDataSetChanged();

                }

            }, throwable -> {
                Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_LONG).show();

            }));
        }

        if (id == R.id.action_highest_rated && !mCurrentSort.equals(SORT_TOP_RATED)) {
            mCurrentSort = SORT_TOP_RATED;
            mCompositDisposable.clear();
            mCompositDisposable.add(movieGridViewModel.getMovielist(NetworkUtilities.getHighestRatedMovies(), getContext()).subscribe(movieListItems ->
            {
                if (mMovieGridAdapter != null) {
                    mMovieGridAdapter.clearAllMovieData();
                    mMovieGridAdapter.loadHighestRatedMovies(movieListItems);
                    mMovieGridAdapter.notifyDataSetChanged();
                } else {
                    mMovieGridAdapter = new MovieGridAdapter(getActivity(), movieListItems);
                    mMovieGridAdapter.setClickListener(this);
                    mRecyclerView.setAdapter(mMovieGridAdapter);
                    mMovieGridAdapter.notifyDataSetChanged();
                }

            }, throwable -> {
                Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_LONG).show();

            }));
        }


        if (id == R.id.action_favorite) {
            mCurrentSort = SORT_FAVORITE;
            mMovieGridAdapter.clearAllMovieData();
            movieGridViewModel.getFavoritesFromDb(getActivity()).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    if (mCurrentSort.equals(SORT_FAVORITE)) {
                        mMovieGridAdapter.setFavorites(movies);
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentSort.equals(SORT_TOP_RATED)) {
            menu.findItem(R.id.action_highest_rated).setChecked(true);

        } else if (mCurrentSort.equals(SORT_POPULAR)) {
            menu.findItem(R.id.action_popular).setChecked(true);
        } else if(mCurrentSort.equals(SORT_FAVORITE)) {
            menu.findItem(R.id.action_favorite).setChecked(true);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        checkConfig();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositDisposable.dispose();
    }
}
