package com.popular.movies.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danielschneider on 5/6/18.
 */

public class MovieListItem implements Parcelable {

    private static String LIST_IMAGE_URL = "http://image.tmdb.org/t/p/w342/";
    private static String DETAIL_IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private String uri;
    private String imageUrl;
    private String title;
    private int voteCount;
    private double voteAverage;
    private double popularity;
    private String id;
    private boolean hasVideo;
    private String overView;

    public MovieListItem() {

    }

    protected MovieListItem(Parcel in) {
        uri = in.readString();
        imageUrl = in.readString();
        title = in.readString();
        voteCount = in.readInt();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        id = in.readString();
        hasVideo = in.readByte() != 0;
        overView = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeInt(voteCount);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeString(id);
        dest.writeByte((byte) (hasVideo ? 1 : 0));
        dest.writeString(overView);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieListItem> CREATOR = new Creator<MovieListItem>() {
        @Override
        public MovieListItem createFromParcel(Parcel in) {
            return new MovieListItem(in);
        }

        @Override
        public MovieListItem[] newArray(int size) {
            return new MovieListItem[size];
        }
    };

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getListImageUrl() {
        return LIST_IMAGE_URL + imageUrl;
    }

    public String getDetailImageUrl() {
        return DETAIL_IMAGE_URL + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int count) {
        this.voteCount = count;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double average) {
        this.voteAverage = average;
    }

    public double getVotePopularity() {
        return popularity;
    }

    public void setVotePopularity(double popularityNumber) {
        this.popularity = popularityNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
