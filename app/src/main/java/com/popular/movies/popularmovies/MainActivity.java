package com.popular.movies.popularmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.popular.movies.popularmovies.data.Database;
import com.popular.movies.popularmovies.data.DatabaseInitializer;
import com.popular.movies.popularmovies.data.MovieDao;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseInitializer.populateAsync(new Database() {
            @Override
            public MovieDao movieDao() {
                return null;
            }
        });

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, mainFragment, MainFragment.TAG);
        fragmentTransaction.commit();
    }

}
