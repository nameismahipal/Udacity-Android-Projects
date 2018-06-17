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
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<MovieDetails> movieDetails = null;

    final private MovieClickListener movieClickListener;

    public MovieAdapter(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    @Override
    public int getItemCount() {
        if(null != movieDetails)
            return movieDetails.size();
        else
            return 0;
    }

    public void newData(List<MovieDetails> newData){
        movieDetails = newData;
        notifyDataSetChanged();
    }

    public interface MovieClickListener{
        void  onMovieItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.grid_item_view,
                                             parent, false);

        return (new MovieViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.onBind(position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        final ImageView imagePosterView;

        private MovieViewHolder(View view) {
            super(view);
            imagePosterView = (ImageView) view.findViewById(R.id.iv_posterImage);
            imagePosterView.setOnClickListener(this);
        }

        void onBind(int index) {

            Picasso.get()
                    .load(movieDetails.get(index).getPosterPath())
                    //.placeholder("http://via.placeholder.com/350x150")
                    //.error(R.drawable.user_placeholder_error)
                    .into(imagePosterView);

        }

        @Override
        public void onClick(View v) {
            int clickedItemIndex = getAdapterPosition();
            movieClickListener.onMovieItemClick(clickedItemIndex);
        }
    }

}
