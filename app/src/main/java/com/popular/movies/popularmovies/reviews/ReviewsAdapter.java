package com.popular.movies.popularmovies.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popular.movies.popularmovies.R;
import com.popular.movies.popularmovies.reviews.model.ReviewListItem;

import java.util.List;

/**
 * Created by danielschneider on 6/28/18.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<ReviewListItem> mReviewListItems;
    private Context mContext;

    public ReviewsAdapter(Context context, List<ReviewListItem> reviewListItems) {
        mInflater = LayoutInflater.from(context);
        mReviewListItems = reviewListItems;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String review = mReviewListItems.get(position).getContent();
        String author = mReviewListItems.get(position).getAuthor();

        holder.review.setText(review);
        holder.author.setText(author);

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.lavender));
        }
    }

    @Override
    public int getItemCount() {
        if (mReviewListItems == null) {
            return 0;
        }

        return mReviewListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView review;

        public ViewHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author_tv);
            review = itemView.findViewById(R.id.review_tv);
        }
    }
}
