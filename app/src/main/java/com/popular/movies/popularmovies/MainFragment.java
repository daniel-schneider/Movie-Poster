package com.popular.movies.popularmovies;

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
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by danielschneider on 5/6/18.
 */

public class MainFragment extends android.support.v4.app.Fragment implements MovieGridAdapter.ItemClickListener {

    MovieGridAdapter mMovieGridAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    private CompositeDisposable mCompositDisposable;
    private Parcelable mListState = null;

    public final static String TAG = MainFragment.class.getSimpleName();
    public static final String LIST_STATE_KEY = "LIST_STATE_KEY";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(LIST_STATE_KEY)) {
                checkConfig();
                mLayoutManager.onRestoreInstanceState(mListState);

            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCompositDisposable = new CompositeDisposable();

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(LIST_STATE_KEY)) {
                checkConfig();
                mLayoutManager.onRestoreInstanceState(mListState);

            }
        } else {
            checkConfig();
        }


        mRecyclerView = view.findViewById(R.id.movie_posters_recycler_view);

        MovieGridViewModel movieGridViewModel = ViewModelProviders.of(this).get(MovieGridViewModel.class);
        mCompositDisposable.add(movieGridViewModel.getMovielist().subscribe((movieListItems -> {

            mRecyclerView.setLayoutManager(mLayoutManager);
            mMovieGridAdapter = new MovieGridAdapter(getActivity(), movieListItems);
            mMovieGridAdapter.setClickListener(this);
            mRecyclerView.setAdapter(mMovieGridAdapter);

        })));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        checkConfig();
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
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    private void checkConfig() {
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        }
        else{
            mLayoutManager = new GridLayoutManager(getActivity(), 4);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Bundle bundle = new Bundle();
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        bundle.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            checkConfig();
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }
}
