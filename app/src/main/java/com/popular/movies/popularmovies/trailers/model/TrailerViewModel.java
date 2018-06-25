package com.popular.movies.popularmovies.trailers.model;

import android.arch.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by danielschneider on 6/22/18.
 */

public class TrailerViewModel extends ViewModel {

    private List<TrailorListItem> getTrailorList(String json) {
        List<TrailorListItem> trailorListItems = new ArrayList<>();

        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            JSONObject trailorJson = new JSONObject(json);
            JSONArray results = trailorJson.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                TrailorListItem trailorListItem = new TrailorListItem();
                JSONObject trailorObject = results.getJSONObject(i);
                trailorListItem.setId(trailorObject.getString("id"));
                trailorListItem.setKey(trailorObject.getString("key"));
                trailorListItem.setName(trailorObject.getString("name"));
                trailorListItems.add(trailorListItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailorListItems;
    }

    public Single<List<TrailorListItem>> getMovielist(String trailorJson) {
        return Single.fromCallable(() -> getTrailorList(trailorJson))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
