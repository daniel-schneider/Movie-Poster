package com.popular.movies.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.ItemClickListener {

    MovieGridAdapter mMovieGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Initialize recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_posters_recycler_view);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mMovieGridAdapter = new MovieGridAdapter();
        mMovieGridAdapter.setClickListener(this);
        recyclerView.setAdapter(mMovieGridAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
