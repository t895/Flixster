package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Populates data into the item though the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        
        // Bind the movie data into the ViewHolder
        holder.bind(movie);
    }

    // Returns total items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            // Change URL based on screen orientation
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageURL = movie.getBackdropPath();
            else
                imageURL = movie.getPosterPath();

            Resources res = context.getResources();
            Drawable errorFigure = ResourcesCompat.getDrawable(res, android.R.drawable.stat_notify_error, null);
            Drawable placeholderFigure = ResourcesCompat.getDrawable(res, android.R.drawable.ic_menu_gallery, null);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new RoundedCorners(32));

            Glide.with(context)
                    .load(imageURL)
                    .apply(requestOptions)
                    .placeholder(placeholderFigure)
                    .error(errorFigure)
                    .into(ivPoster);
        }
    }
}
