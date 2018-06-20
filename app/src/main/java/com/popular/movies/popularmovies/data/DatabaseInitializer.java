package com.popular.movies.popularmovies.data;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by danielschneider on 6/20/18.
 */

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final Database db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final Database mDb;

        PopulateDbAsync(Database db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }

    }
}
