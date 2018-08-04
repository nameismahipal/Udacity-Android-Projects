package www.androidcitizen.com.popularmoviesone.ui;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import android.support.v7.widget.Toolbar;
import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.Loader.GlideApp;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract.*;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMovieDetailBinding;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;

public class MovieDetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor>{

    ActivityMovieDetailBinding detailBinding;

    MovieDetails movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        Intent intent = getIntent();
        if (intent.hasExtra("movieDetailsdata")) {

            movieDetails = getIntent().getParcelableExtra("movieDetailsdata");

            if(null != movieDetails) {
                setViewData(movieDetails);
            }
        }


        detailBinding.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                // Add in database (use async thread)
                insertFavItem();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                // Remove from database (use async thread)
                deleteFavItem();
            }
        });

    }

    void setViewData(MovieDetails movieUIDetails){

        checkIfFavoriteAndSet();

        GlideApp.with(this)
                .load(GlobalRef.PORT_BACKDROP_IMAGE_URL_PATH + movieUIDetails.getBackdropPath())
                .into(detailBinding.ivBackgroundImage);

        GlideApp.with(this)
                .load(GlobalRef.PORT_POSTER_IMAGE_URL_PATH  + movieUIDetails.getPosterPath())
                .into(detailBinding.ivPosterImage);
/*
        Picasso.get()
                .load(movieUIDetails.getBackdropPath())
                //.placeholder("http://via.placeholder.com/350x150")
                //.error(R.drawable.user_placeholder_error)
                .into(detailBinding.ivBackgroundImage);

        Picasso.get()
                .load(movieUIDetails.getPosterPath())
                //.placeholder("http://via.placeholder.com/350x150")
                //.error(R.drawable.user_placeholder_error)
                .into(detailBinding.ivPosterImage);
*/

        detailBinding.tvTitle.setText(movieUIDetails.getTitle());
        detailBinding.ratingBar.setRating(movieUIDetails.getVoteAverage());
        detailBinding.tvReleaseDate.setText(movieUIDetails.getReleaseDate());
        detailBinding.tvOverView.setText(movieUIDetails.getOverview());
    }

    void insertFavItem() {

        ContentValues values = new ContentValues();

        values.put(FavContract.FavMovieEntry.COLUMN_MOVIE_ID, movieDetails.getId());
        values.put(FavContract.FavMovieEntry.COLUMN_TITLE, movieDetails.getTitle());
        values.put(FavMovieEntry.COLUMN_POSTER_PATH, movieDetails.getPosterPath());
        values.put(FavMovieEntry.COLUMN_BACKDROP_PATH, movieDetails.getBackdropPath());
        values.put(FavMovieEntry.COLUMN_RELEASE_DATE, movieDetails.getReleaseDate());
        values.put(FavMovieEntry.COLUMN_OVERVIEW, movieDetails.getOverview());
        values.put(FavMovieEntry.COLUMN_VOTE_AVERAGE, movieDetails.getVoteAverage());

        getContentResolver().insert(FavMovieEntry.CONTENT_URI, values);
    }

    void deleteFavItem() {
        getContentResolver().delete(FavMovieEntry.buildURIMovieId(movieDetails.getId()), null, null);
    }

    void checkIfFavoriteAndSet() {

        getLoaderManager().restartLoader(GlobalRef.MOVIE_DATABASE_LOADING_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        int movieId = movieDetails.getId();
        String[] projection = new String[] {FavMovieEntry.COLUMN_MOVIE_ID};

        return new CursorLoader(this,
                FavMovieEntry.buildURIMovieId(movieId),
                projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if(null != cursor) {

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                detailBinding.likeButton.setLiked(true);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
