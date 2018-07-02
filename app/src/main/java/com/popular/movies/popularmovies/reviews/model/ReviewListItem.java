package com.popular.movies.popularmovies.reviews.model;

/**
 * Created by danielschneider on 6/18/18.
 */

//{
//        "id": 351286,
//        "page": 1,
//        "results": [
//        {
//        "author": "Law",
//        "content": "I felt embarrassed to be watching this. It's an embarrassing fever dream. I abandoned it halfway through its runtime.",
//        "id": "5b2d4c080e0a264aea001943",
//        "url": "https://www.themoviedb.org/review/5b2d4c080e0a264aea001943"
//        }
//        ],
//        "total_pages": 1,
//        "total_results": 1
//        }

public class ReviewListItem {

    private String author;
    private String content;
    private String id;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
