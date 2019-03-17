<<<<<<< HEAD
package www.androidcitizen.com.popularmoviesone.data.adapter;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.databinding.GridItemViewBinding;

import static www.androidcitizen.com.popularmoviesone.config.Constants.*;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<MovieDetails> movieDetails = null;

    final private MovieClickListener movieClickListener;

    //private GridItemViewBinding gridItemViewBinding;

    public MovieAdapter(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    public interface MovieClickListener{
        void  onMovieItemClick(int clickedItemIndex, MovieDetails movieDetails);
    }

    public List<MovieDetails> getMovies(){
        return movieDetails;
    }

    @Override
    public int getItemCount() {
        if(null != movieDetails)
            return movieDetails.size();
        else
            return 0;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GridItemViewBinding gridItemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.grid_item_view,
                    parent,
                    false);

        return new MovieViewHolder(gridItemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
         holder.onBind(position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final GridItemViewBinding itemViewBinding;

        private MovieViewHolder(GridItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.itemViewBinding = itemViewBinding;
            itemViewBinding.ivPosterImage.setOnClickListener(this);
        }

        void onBind(int index) {

            Picasso.get()
                    .load(PORT_POSTER_IMAGE_URL_PATH  + movieDetails.get(index).getPosterPath())
                    //.placeholder("http://via.placeholder.com/350x150")
                    //.error(R.drawable.user_placeholder_error)
                    .into(itemViewBinding.ivPosterImage);
        }

        @Override
        public void onClick(View v) {
            int clickedItemIndex = getAdapterPosition();
            movieClickListener.onMovieItemClick(clickedItemIndex, movieDetails.get(clickedItemIndex));
        }
    }

    public void newData(List<MovieDetails> movieList){

        if(null == movieDetails) {

            movieDetails = movieList;
            notifyDataSetChanged();

        } else {

            for (MovieDetails movieDetail : movieList) {
                add(movieDetail);
            }
        }
    }

    public void newCursorData(Cursor cursor) {

        if( null != cursor &&
            0 != cursor.getCount()) {

            cursor.moveToFirst();
            do {

                MovieDetails movieDetails =
                        new MovieDetails(cursor.getInt(INDEX_MOVIE_ID),
                                cursor.getString(INDEX_TITLE),
                                cursor.getString(INDEX_POSTER_PATH),
                                cursor.getString(INDEX_BACKDROP_PATH),
                                cursor.getString(INDEX_RELEASE_DATE),
                                cursor.getString(INDEX_OVERVIEW),
                                cursor.getFloat(INDEX_VOTE_AVERAGE));

                add(movieDetails);

            } while (cursor.moveToNext());
        }
        else if ( 0 == cursor.getCount() ||
                  null == cursor) {

            movieDetails = null;
        }
    }

    private void add(MovieDetails movieInfo) {
        movieDetails.add(movieInfo);
        notifyItemInserted(movieDetails.size() - 1);
    }

    public void clearAll() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private MovieDetails getItem(int position) {
        return movieDetails.get(position);
    }

    private void remove(MovieDetails movie) {
        int position = movieDetails.indexOf(movie);
        movieDetails.remove(position);
        notifyItemRemoved(position);

    }




}
||||||| merged common ancestors
=======
package www.androidcitizen.com.popularmoviesone.data.adapter;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.databinding.GridItemViewBinding;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<MovieDetails> movieDetails = null;

    final private MovieClickListener movieClickListener;

    //private GridItemViewBinding gridItemViewBinding;

    public MovieAdapter(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    public interface MovieClickListener{
        void  onMovieItemClick(int clickedItemIndex, MovieDetails movieDetails);
    }

    public List<MovieDetails> getMovies(){
        return movieDetails;
    }

    @Override
    public int getItemCount() {
        if(null != movieDetails)
            return movieDetails.size();
        else
            return 0;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GridItemViewBinding gridItemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.grid_item_view,
                    parent,
                    false);

        return new MovieViewHolder(gridItemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
         holder.onBind(position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final GridItemViewBinding itemViewBinding;

        private MovieViewHolder(GridItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.itemViewBinding = itemViewBinding;
            itemViewBinding.ivPosterImage.setOnClickListener(this);
        }

        void onBind(int index) {

            Picasso.get()
                    .load(GlobalRef.PORT_POSTER_IMAGE_URL_PATH  + movieDetails.get(index).getPosterPath())
                    //.placeholder("http://via.placeholder.com/350x150")
                    //.error(R.drawable.user_placeholder_error)
                    .into(itemViewBinding.ivPosterImage);
        }

        @Override
        public void onClick(View v) {
            int clickedItemIndex = getAdapterPosition();
            movieClickListener.onMovieItemClick(clickedItemIndex, movieDetails.get(clickedItemIndex));
        }
    }

    public void newData(List<MovieDetails> movieList){

        if(null == movieDetails) {

            movieDetails = movieList;
            notifyDataSetChanged();

        } else {

            for (MovieDetails movieDetail : movieList) {
                add(movieDetail);
            }
        }
    }

    public void newCursorData(Cursor cursor) {

        if( null != cursor &&
            0 != cursor.getCount()) {

            cursor.moveToFirst();
            do {

                MovieDetails movieDetails =
                        new MovieDetails(cursor.getInt(GlobalRef.INDEX_MOVIE_ID),
                                cursor.getString(GlobalRef.INDEX_TITLE),
                                cursor.getString(GlobalRef.INDEX_POSTER_PATH),
                                cursor.getString(GlobalRef.INDEX_BACKDROP_PATH),
                                cursor.getString(GlobalRef.INDEX_RELEASE_DATE),
                                cursor.getString(GlobalRef.INDEX_OVERVIEW),
                                cursor.getFloat(GlobalRef.INDEX_VOTE_AVERAGE));

                add(movieDetails);

            } while (cursor.moveToNext());
        }
        else if ( 0 == cursor.getCount() ||
                  null == cursor) {

            movieDetails = null;
        }
    }

    private void add(MovieDetails movieInfo) {
        movieDetails.add(movieInfo);
        notifyItemInserted(movieDetails.size() - 1);
    }

    public void clearAll() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private MovieDetails getItem(int position) {
        return movieDetails.get(position);
    }

    private void remove(MovieDetails movie) {
        int position = movieDetails.indexOf(movie);
        movieDetails.remove(position);
        notifyItemRemoved(position);

    }




}
>>>>>>> 5bd5f9266ec76f4bbe33414f7c43d01943498a61
