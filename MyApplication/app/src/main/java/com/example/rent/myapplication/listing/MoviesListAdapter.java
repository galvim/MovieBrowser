package com.example.rent.myapplication.listing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rent.myapplication.R;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int GAMES_VIEW_HOLDER = 1;
    private static final int MY_VIEW_HOLDER = 2;

    private List<MovieListingItem> items = Collections.emptyList();
    private OnMovieItemClickListener onMovieItemClickListener;

    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    public void setItems(List<MovieListingItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GAMES_VIEW_HOLDER) {
            //RecyclerView jest rodzicem(parent) //
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
            return new GamesVieHolder(layout);
        } else if (viewType == MY_VIEW_HOLDER) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(layout);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieListingItem movieListingItem = items.get(position);

        if (getItemViewType(position) == MY_VIEW_HOLDER) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            Glide.with(myViewHolder.poster.getContext()).load(movieListingItem.getPoster()).into(myViewHolder.poster);
            myViewHolder.titleAndYear.setText(movieListingItem.getTitle() + " (" + movieListingItem.getYear() + ")");
            myViewHolder.type.setText("typ :" + movieListingItem.getType());
            myViewHolder.itemView.setOnClickListener(v -> {
                if (onMovieItemClickListener != null) {
                    onMovieItemClickListener.onMovieItemClick(movieListingItem.getImdbID());
                }

            });
        } else {
            GamesVieHolder gamesVieHolder = (GamesVieHolder) holder;
            Glide.with(gamesVieHolder.poster.getContext()).load(movieListingItem.getPoster()).into(gamesVieHolder.poster);
            gamesVieHolder.title.setText(movieListingItem.getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
     if  ("Game".equalsIgnoreCase(items.get(position).getType())){
            return GAMES_VIEW_HOLDER;
        } else {
            return MY_VIEW_HOLDER;
        }
    }

    public void addItems(List<MovieListingItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    class GamesVieHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        public GamesVieHolder(View itemView) {
            super(itemView);
            poster = ButterKnife.findById(itemView, R.id.game_poster);
            title = ButterKnife.findById(itemView, R.id.game_title);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView titleAndYear;
        TextView type;
        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView) itemView.findViewById(R.id.title_and_year);
            type = (TextView) itemView.findViewById(R.id.type);
        }
    }
}
