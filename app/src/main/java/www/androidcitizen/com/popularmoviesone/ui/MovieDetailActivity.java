package www.androidcitizen.com.popularmoviesone.ui;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract.*;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMovieDetailBinding;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;

public class MovieDetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor>{

    ActivityMovieDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        Intent intent = getIntent();
        if (intent.hasExtra("movieDetailsdata")) {

            MovieDetails movieDetails = getIntent().getParcelableExtra("movieDetailsdata");

            if(null != movieDetails) {
                setViewData(movieDetails);
            }

        }

        //getLoaderManager().initLoader(GlobalRef.MOVIE_DATABASE_LOADING_ID, bundle, this);

        /*
        detailBinding.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(1 == DATA_AVAILABLE) {
                    // Add in database (use async thread)
                    Bundle bundle = new Bundle();
                    bundle.putInt(GlobalRef.FAV_MOVIE_DB_UPDATE, GlobalRef.FAV_MOVIE_ADD);
                    bundle.putParcelable(GlobalRef.FAV_MOVIE_DB_FAV_ITEM, movieSubDetails);

                    getLoaderManager().restartLoader(GlobalRef.MOVIE_DATABASE_LOADING_ID,
                            bundle,
                            MovieDetailActivity.this);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(1 == DATA_AVAILABLE) {
                    // Remove from database (use async thread)
                    Bundle bundle = new Bundle();
                    bundle.putInt(GlobalRef.FAV_MOVIE_DB_UPDATE, GlobalRef.FAV_MOVIE_DELETE);
                    bundle.putInt(GlobalRef.FAV_MOVIE_DB_FAV_MOVIE_ID, movieSubDetails.getMovie_id());

                    getLoaderManager().restartLoader(GlobalRef.MOVIE_DATABASE_LOADING_ID,
                            bundle,
                            MovieDetailActivity.this);
                }
            }
        });
        */

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void setViewData(MovieDetails movieUIDetails){

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

        detailBinding.tvTitle.setText(movieUIDetails.getTitle());
        detailBinding.ratingBar.setRating(movieUIDetails.getVoteAverage());
        detailBinding.tvReleaseDate.setText(movieUIDetails.getReleaseDate());
        detailBinding.tvOverView.setText(movieUIDetails.getOverview());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                FavMovieEntry.CONTENT_URI,
                GlobalRef.PROJECTION,
                null,
                null,
                null
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
