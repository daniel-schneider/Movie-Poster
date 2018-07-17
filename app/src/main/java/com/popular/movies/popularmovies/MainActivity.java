package com.popular.movies.popularmovies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.popular.movies.popularmovies.data.Database;
import com.popular.movies.popularmovies.data.DatabaseInitializer;
import com.popular.movies.popularmovies.data.MovieDao;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    Fragment mFragment;


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

        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, MainFragment.TAG);
        } else {
            mFragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, mFragment, MainFragment.TAG);
            fragmentTransaction.commit();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);


        getSupportFragmentManager().putFragment(outState, MainFragment.TAG, mFragment);
//        outState.putParcelable(MainFragment.LIST_STATE_KEY, MainFragment.mListState);
    }
}
