package main.android.com.popularmoviesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.zip.Inflater;

class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.SingleMovieViewHolder> {
    int allMoviesJsonArray;

    public PopularMoviesAdapter(int allMoviesJsonArray) {
        this.allMoviesJsonArray = allMoviesJsonArray;
    }

    @NonNull
    @Override
    public SingleMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View viewHolder = layoutInflater.inflate(R.layout.single_movie_item, viewGroup, false);
        SingleMovieViewHolder singleMovieViewHolder = new SingleMovieViewHolder(viewHolder);
        return singleMovieViewHolder;
    }

    @Override
    public int getItemCount() {
        return allMoviesJsonArray;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleMovieViewHolder singleMovieViewHolder, int i) {
        singleMovieViewHolder.bind(i);
    }


    class SingleMovieViewHolder extends RecyclerView.ViewHolder{

        public TextView singleMovieImageView;

        public SingleMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            singleMovieImageView = itemView.findViewById(R.id.singleMovieView);
        }

        public void bind(int arraySize){
            singleMovieImageView.setText(String.valueOf(arraySize));
        }
    }

}
