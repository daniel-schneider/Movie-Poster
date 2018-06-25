package com.popular.movies.popularmovies.trailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popular.movies.popularmovies.R;
import com.popular.movies.popularmovies.trailers.model.TrailorListItem;

import java.util.List;

/**
 * Created by danielschneider on 6/19/18.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private static final String TAG = TrailersAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TrailorListItem> mTrailorList;
    private ItemClickListener mClickListener;


    public TrailersAdapter(Context context, List<TrailorListItem> trailorListItems) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mTrailorList = trailorListItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trailer_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String trailorTitle = mTrailorList.get(position).getName();
        holder.title.setText(trailorTitle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (null == mTrailorList) {
            return 0;
        }

        return mTrailorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.review_title);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
