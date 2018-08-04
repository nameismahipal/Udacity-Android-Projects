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

import com.like.LikeButton;
import com.like.OnLikeListener;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.Loader.GlideApp;
import www.androidcitizen.com.popularmoviesone.data.Loader.ReviewsLoader;
import www.androidcitizen.com.popularmoviesone.data.model.ReviewResultsItem;
import www.androidcitizen.com.popularmoviesone.data.adapter.ReviewAdapter;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract.*;
import www.androidcitizen.com.popularmoviesone.data.model.Reviews;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMovieDetailBinding;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;

public class MovieDetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks {

    ActivityMovieDetailBinding detailBinding;

    MovieDetails movieDetails;

    ReviewAdapter reviewAdapter;

    List<ReviewResultsItem> reviewResultsItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);


        if(null == savedInstanceState) {

            Intent intent = getIntent();
            if (intent.hasExtra("movieDetailsdata")) {

                movieDetails = getIntent().getParcelableExtra("movieDetailsdata");

                if (null != movieDetails) {
                    setViewData(movieDetails);
                }

                fetchReviewsAndVideosAndDataBase(GlobalRef.LOAD_MOVIE_REVIEWS_AND_VIDEOS);
            }

        } else {

        }

        setupRecycleView();

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

    void fetchReviewsAndVideosAndDataBase(int iIndex) {

        switch (iIndex) {
            case GlobalRef.LOAD_MOVIE_REVIEWS_AND_VIDEOS:
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalRef.KEY_MOVIE_ID, movieDetails.getId());
            getLoaderManager().restartLoader(GlobalRef.LOADING_ID_MOVIE_REVIEWS, bundle, this);
            //getLoaderManager().restartLoader(GlobalRef.LOADING_ID_MOVIE_VIDEOS, bundle, this);
            break;

            case GlobalRef.CHECK_DB_IF_MOVIE_IS_FAVORITE:
            getLoaderManager().restartLoader(GlobalRef.LOADING_ID_MOVIE_DATABASE, null, this);
            break;
        }
    }

    void setViewData(MovieDetails movieUIDetails){

        fetchReviewsAndVideosAndDataBase(GlobalRef.CHECK_DB_IF_MOVIE_IS_FAVORITE);

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

    private void setupRecycleView(){

        //reviewAdapter = new ReviewAdapter(reviewResultsItems);
        reviewAdapter = new ReviewAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        detailBinding.authorReviewsList.setHasFixedSize(true);
        detailBinding.authorReviewsList.setLayoutManager(layoutManager);
        detailBinding.authorReviewsList.setItemAnimator(new DefaultItemAnimator());
        detailBinding.authorReviewsList.setAdapter(reviewAdapter);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle bundleArgs) {

        switch (id) {

            case GlobalRef.LOADING_ID_MOVIE_REVIEWS:
                return new ReviewsLoader(this, bundleArgs);

            case GlobalRef.LOADING_ID_MOVIE_VIDEOS:
                    break;
            case GlobalRef.LOADING_ID_MOVIE_DATABASE:

                int movieId = movieDetails.getId();

                String[] projection = new String[]{FavMovieEntry.COLUMN_MOVIE_ID};

                return new CursorLoader(this,
                        FavMovieEntry.buildURIMovieId(movieId),
                        projection,
                        null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object objectData) {

        switch (loader.getId()) {

            case GlobalRef.LOADING_ID_MOVIE_REVIEWS:
                Reviews reviews = (Reviews) objectData;
                reviewAdapter.newData(reviews.getReviewItems());
                //reviewResultsItems = reviews.getReviewItems();
                break;

            case GlobalRef.LOADING_ID_MOVIE_VIDEOS:
                break;
            case GlobalRef.LOADING_ID_MOVIE_DATABASE:

                if (null != objectData) {
                    Cursor cursor = (Cursor) objectData;

                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        detailBinding.likeButton.setLiked(true);
                    }
                }

                break;
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
