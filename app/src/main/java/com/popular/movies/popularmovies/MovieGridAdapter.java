package com.popular.movies.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popular.movies.popularmovies.model.MovieListItem;
import com.popular.movies.popularmovies.utilities.ImageLoader;

import java.util.List;

/**
 * Created by danielschneider on 4/28/18.
 */

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<MovieListItem> mMovieList;


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

        ImageLoader.loadRoundedImageWithPlaceholder(mContext, mMovieList.get(position).getImageUrl(), holder.MoviePosterImage, R.color.colorAccent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {

        //TODO mMovieList is returning null. need to figure how to populate that list
        if (null == mMovieList) {
            return 0;
        }

        return mMovieList.size();
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
}
