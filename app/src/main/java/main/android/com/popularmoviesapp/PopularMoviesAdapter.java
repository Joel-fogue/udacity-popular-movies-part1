package main.android.com.popularmoviesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import main.android.com.popularmoviesapp.parcels.Movie;

class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.SingleMovieViewHolder> {
    ArrayList<Movie> allMoviePojosArrayList;
    public OnRecyclerViewClickListener mOnclickListenner;

    public interface OnRecyclerViewClickListener{
        void onclickListener(int itemClicked);
    }

    public PopularMoviesAdapter(ArrayList allMoviePojosArrayList, OnRecyclerViewClickListener mOnclickListenner) {
        this.allMoviePojosArrayList = allMoviePojosArrayList;
        this.mOnclickListenner = mOnclickListenner;
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
        return allMoviePojosArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SingleMovieViewHolder singleMovieViewHolder, int i) {
        singleMovieViewHolder.bind(i);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(singleMovieViewHolder.singleMovieImageView);
    }

    class SingleMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView singleMovieImageView;


        public SingleMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            singleMovieImageView = itemView.findViewById(R.id.singleMovieView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            //singleMovieImageView.setText(String.valueOf(arraySize));
           String fullPosterPathUrl = allMoviePojosArrayList.get(position).getMovieFullPosterPath();
            Log.v("arraylist", String.valueOf(allMoviePojosArrayList.size()));
            Log.v("fullPosterPathUrl", fullPosterPathUrl.toString());
            //Picasso.get().load(fullPosterPathUrl.toString()).into(singleMovieImageView);
            Picasso.get().load(fullPosterPathUrl).into(singleMovieImageView);
            //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

        }

        @Override
        public void onClick(View view) {
            int itemClickedPosition = getAdapterPosition();
            mOnclickListenner.onclickListener(itemClickedPosition);
        }
    }

}
