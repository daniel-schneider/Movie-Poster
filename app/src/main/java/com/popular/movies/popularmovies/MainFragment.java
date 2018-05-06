package com.popular.movies.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popular.movies.popularmovies.utilities.NetworkUtilities;

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

        MovieGridViewModel movieGridViewModel = ViewModelProviders.of(this).get(MovieGridViewModel.class);
//        mCompositDisposable.add(movieGridViewModel.getMovieListData()).

        movieGridViewModel.getMovieListData();

        NetworkUtilities.getPopularMovies();

        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};


        //TODO Initialize recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.movie_posters_recycler_view);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        mMovieGridAdapter = new MovieGridAdapter(getActivity(), data);
        mMovieGridAdapter.setClickListener(this);
        recyclerView.setAdapter(mMovieGridAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
