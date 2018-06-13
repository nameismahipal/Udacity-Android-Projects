package www.androidcitizen.com.popularmoviesone.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.model.Movie;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    List<Movie> movies = null;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.grid_item_view,
                                             parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if(null != movies)
            return movies.size();
        else
            return 0;
    }


     class MovieViewHolder extends RecyclerView.ViewHolder {

         ImageView imagePosterView;

        public MovieViewHolder(View view) {
            super(view);
            imagePosterView = (ImageView) view.findViewById(R.id.iv_posterImage);
        }

        void onBind(int index) {

            Picasso.get()
                    .load(movies.get(index).getPosterPath())
                    //.placeholder("http://via.placeholder.com/350x150")
                    //.error(R.drawable.user_placeholder_error)
                    .into(imagePosterView);

        }
    }

    public void newData(List<Movie> newData){
        movies = newData;
        notifyDataSetChanged();
    }

}
