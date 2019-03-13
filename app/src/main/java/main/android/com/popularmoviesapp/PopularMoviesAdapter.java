package main.android.com.popularmoviesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.zip.Inflater;

class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.SingleMovieViewHolder> {
    ArrayList allMoviesArrayList;

    public PopularMoviesAdapter(ArrayList allMoviesArrayList) {
        this.allMoviesArrayList = allMoviesArrayList;
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
        return allMoviesArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SingleMovieViewHolder singleMovieViewHolder, int i) {
        singleMovieViewHolder.bind(i);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(singleMovieViewHolder.singleMovieImageView);
    }

    class SingleMovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView singleMovieImageView;

        public SingleMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            singleMovieImageView = itemView.findViewById(R.id.singleMovieView);
        }

        public void bind(int position){
            //singleMovieImageView.setText(String.valueOf(arraySize));
           URL fullPosterPathUrl = (URL) allMoviesArrayList.get(position);
            Log.v("fullPosterPathUrl", fullPosterPathUrl.toString());
            //Picasso.get().load(fullPosterPathUrl.toString()).into(singleMovieImageView);
            Picasso.get().load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(singleMovieImageView);
            //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

        }
    }

}
