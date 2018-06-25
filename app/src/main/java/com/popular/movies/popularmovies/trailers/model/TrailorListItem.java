package com.popular.movies.popularmovies.trailers.model;

/**
 * Created by danielschneider on 6/22/18.
 */

public class TrailorListItem {

//    {
//        "id": "58cfd30ec3a36850fb033b0f",
//            "iso_639_1": "en",
//            "iso_3166_1": "US",
//            "key": "Z5ezsReZcxU",
//            "name": "No Good Deed",
//            "site": "YouTube",
//            "size": 1080,
//            "type": "Featurette"
//    }

    private String id;
    private String key;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
