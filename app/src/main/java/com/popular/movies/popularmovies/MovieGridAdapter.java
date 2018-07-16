package com.popular.movies.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popular.movies.popularmovies.data.Movie;
import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.utilities.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielschneider on 4/28/18.
 */

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {

    private static final String TAG = MovieGridViewModel.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<MovieListItem> mMovieList;
    private List<Movie> mFavorites;


    MovieGridAdapter(Context context, List<MovieListItem> movieListItems) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mMovieList = movieListItems;
    }

    public MovieListItem getMovieListItemForPosition(int position) {
        return mMovieList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_grid_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String title = mMovieList.get(position).getTitle();
        holder.title.setText(title);

        Log.d(TAG, title + "'s image URL: " + mMovieList.get(position).getListImageUrl());

        ImageLoader.loadRoundedImage(mContext, mMovieList.get(position).getListImageUrl(), holder.MoviePosterImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, position);
            }
        });



    }

    @Override
    public int getItemCount() {

        if (null == mMovieList) {
            return 0;
        }

        return mMovieList.size();
    }

    public List<Movie> getFavorites() {
        return mFavorites;
    }

    public void setFavorites(List<Movie> favorites) {
        mFavorites = favorites;
        loadFavoriteMovies(favorites);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView MoviePosterImage;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            MoviePosterImage = (ImageView) itemView.findViewById(R.id.poster_image);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void clearAllMovieData() {
        mMovieList.clear();
    }

    public void loadHighestRatedMovies(List<MovieListItem> movieListItems) {
        mMovieList.addAll(movieListItems);
    }

    public void loadFavoriteMovies(List<Movie> movieList) {

        List<Movie> favoriteMovies = movieList;
        List<MovieListItem> favoriteList = new ArrayList<>();

        for(int i = 0; i < favoriteMovies.size(); i++) {
            MovieListItem favoriteListItem = new MovieListItem();
            Movie movieObject = favoriteMovies.get(i);

            favoriteListItem.setId(movieObject.getMovieId());
            favoriteListItem.setImageUrl(movieObject.getPosterPath());
            favoriteListItem.setTitle(movieObject.getMovieName());
            favoriteListItem.setVoteCount(movieObject.getVoteCount());
            favoriteListItem.setVoteAverage(movieObject.getVoteAverage());
            favoriteListItem.setVotePopularity(movieObject.getPopularity());
            favoriteListItem.setOverview(movieObject.getOverview());
            favoriteListItem.setReleaseDate(movieObject.getReleaseDate());
            favoriteList.add(favoriteListItem);
        }


        for (MovieListItem item : favoriteList) {
            Log.d(TAG, item.getTitle() + " loaded from db");

        }
        mMovieList.addAll(favoriteList);
    }
}
