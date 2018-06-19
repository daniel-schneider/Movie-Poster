package com.popular.movies.popularmovies.data;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * Created by danielschneider on 6/19/18.
 */

@android.arch.persistence.room.Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public abstract MovieDao movieDao();


    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    public static Database getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), Database.class, "user-database")
                            // allow queries on the main thread.
                            //TODO Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
