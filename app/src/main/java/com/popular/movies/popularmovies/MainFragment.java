package com.popular.movies.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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
    private CompositeDisposable mCompositDisposable;

    public final static String TAG = MainFragment.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCompositDisposable = new CompositeDisposable();
        RecyclerView recyclerView = view.findViewById(R.id.movie_posters_recycler_view);

        MovieGridViewModel movieGridViewModel = ViewModelProviders.of(this).get(MovieGridViewModel.class);
        mCompositDisposable.add(movieGridViewModel.getMovielist().subscribe((movieListItems -> {

            int numberOfColumns = 2;
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
            mMovieGridAdapter = new MovieGridAdapter(getActivity(), movieListItems);
            mMovieGridAdapter.setClickListener(this);
            recyclerView.setAdapter(mMovieGridAdapter);

        })));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onItemClick(View view, int position) {
        MovieDetailActivity movieDetailActivity = new MovieDetailActivity(mMovieGridAdapter.getMovieListItemForPosition(position));
        Intent intent = new Intent(getActivity(), movieDetailActivity.getClass());
        startActivity(intent);
    }
}
