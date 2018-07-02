package com.popular.movies.popularmovies.reviews.model;

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
 * Created by danielschneider on 6/28/18.
 */

public class ReviewViewModel extends ViewModel {

    private List<ReviewListItem> getReviewList(String json) {

        List<ReviewListItem> reviewListItems = new ArrayList<>();

        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            JSONObject trailorJson = new JSONObject(json);
            JSONArray results = trailorJson.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                ReviewListItem reviewListItemListItem = new ReviewListItem();
                JSONObject trailorObject = results.getJSONObject(i);
                reviewListItemListItem.setId(trailorObject.getString("id"));
                reviewListItemListItem.setContent(trailorObject.getString("content"));
                reviewListItemListItem.setAuthor(trailorObject.getString("author"));
                reviewListItems.add(reviewListItemListItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewListItems;
    }

    public Single<List<ReviewListItem>> getReviewlist(String reviewJson) {
        return Single.fromCallable(() -> getReviewList(reviewJson))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
