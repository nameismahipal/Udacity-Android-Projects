<<<<<<< HEAD
package www.androidcitizen.com.popularmoviesone.ui;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.like.LikeButton;
import com.like.OnLikeListener;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.data.Loader.GlideApp;
import www.androidcitizen.com.popularmoviesone.data.Loader.ReviewsLoader;
import www.androidcitizen.com.popularmoviesone.data.Loader.VideosLoader;
import www.androidcitizen.com.popularmoviesone.data.adapter.VideosAdapter;
import www.androidcitizen.com.popularmoviesone.data.model.ReviewResultsItem;
import www.androidcitizen.com.popularmoviesone.data.adapter.ReviewAdapter;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract.*;
import www.androidcitizen.com.popularmoviesone.data.model.Reviews;
import www.androidcitizen.com.popularmoviesone.data.model.VideoResultsItem;
import www.androidcitizen.com.popularmoviesone.data.model.Videos;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMovieDetailBinding;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;

import static www.androidcitizen.com.popularmoviesone.config.Constants.*;

public class MovieDetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks, VideosAdapter.VideoClickListener {

    private ActivityMovieDetailBinding detailBinding;

    private MovieDetails movieDetails;

    private ReviewAdapter reviewAdapter;
    private VideosAdapter videosAdapter;

    private static boolean toggleUserReviews = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        setupRecycleView();

        if(null == savedInstanceState) {

            Intent intent = getIntent();
            if (intent.hasExtra(KEY_MOVIE_DETAILS_DATA)) {

                movieDetails = getIntent().getParcelableExtra(KEY_MOVIE_DETAILS_DATA);

                if (null != movieDetails) {
                    setViewData(movieDetails);
                }

                fetchReviewsAndVideosAndDataBase(LOAD_MOVIE_REVIEWS_AND_VIDEOS);
            }

        } else {

                List<ReviewResultsItem> reviewsItems = savedInstanceState.getParcelableArrayList(INSTANCE_STATE_LIST_REVIEWS);
                reviewAdapter.newData(reviewsItems);


                List<VideoResultsItem> videoItems = savedInstanceState.getParcelableArrayList(INSTANCE_STATE_LIST_VIDEOS);
                videosAdapter.newData(videoItems);

                movieDetails = savedInstanceState.getParcelable(INSTANCE_STATE_LIST_MOVIES);

                if(null != movieDetails) {
                    setViewData(movieDetails);

                    String noOfReviews = savedInstanceState.getString(KEY_MOVIE_NO_OF_REVIEWS);
                    detailBinding.itemDetails.reviewContainer.reviewValue.setText(noOfReviews);
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

    void fetchReviewsAndVideosAndDataBase(int iIndex) {

        switch (iIndex) {
            case LOAD_MOVIE_REVIEWS_AND_VIDEOS:

                Bundle bundle = new Bundle();
                bundle.putInt(KEY_MOVIE_ID, movieDetails.getId());

                getLoaderManager().restartLoader(LOADING_ID_MOVIE_REVIEWS, bundle, this);
                getLoaderManager().restartLoader(LOADING_ID_MOVIE_VIDEOS, bundle, this);
                break;

            case CHECK_DB_IF_MOVIE_IS_FAVORITE:
                getLoaderManager().restartLoader(LOADING_ID_MOVIE_DATABASE, null, this);
                break;
        }
    }

    void setViewData(MovieDetails movieUIDetails){

        getSupportActionBar().setTitle(movieUIDetails.getTitle());

        fetchReviewsAndVideosAndDataBase(CHECK_DB_IF_MOVIE_IS_FAVORITE);

        GlideApp.with(this)
                .load(PORT_BACKDROP_IMAGE_URL_PATH + movieUIDetails.getBackdropPath())
                .into(detailBinding.ivBackgroundImage);

        GlideApp.with(this)
                .load(PORT_POSTER_IMAGE_URL_PATH  + movieUIDetails.getPosterPath())
                .into(detailBinding.ivPosterImage);

        detailBinding.tvTitle.setText(movieUIDetails.getTitle());
        detailBinding.ratingBar.setRating(movieUIDetails.getVoteAverage());
        detailBinding.tvReleaseDate.setText(movieUIDetails.getReleaseDate());
        detailBinding.itemDetails.tvOverView.setText(movieUIDetails.getOverview());

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

        //Set Review Adapter
        reviewAdapter = new ReviewAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        detailBinding.itemDetails.reviewContainer.authorReviewsList.setHasFixedSize(true);
        detailBinding.itemDetails.reviewContainer.authorReviewsList.setLayoutManager(layoutManager);
        detailBinding.itemDetails.reviewContainer.authorReviewsList.setItemAnimator(new DefaultItemAnimator());
        detailBinding.itemDetails.reviewContainer.authorReviewsList.setAdapter(reviewAdapter);

        //Set Video Adapter
        videosAdapter = new VideosAdapter(this);

        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        detailBinding.itemDetails.movieTrailersList.setHasFixedSize(true);
        detailBinding.itemDetails.movieTrailersList.setLayoutManager(videoLayoutManager);
        detailBinding.itemDetails.movieTrailersList.setItemAnimator(new DefaultItemAnimator());
        detailBinding.itemDetails.movieTrailersList.setAdapter(videosAdapter);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle bundleArgs) {

        switch (id) {

            case LOADING_ID_MOVIE_REVIEWS:
                return new ReviewsLoader(this, bundleArgs);

            case LOADING_ID_MOVIE_VIDEOS:
                return new VideosLoader(this, bundleArgs);

            case LOADING_ID_MOVIE_DATABASE:

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

        if(null != objectData) {

            switch (loader.getId()) {

                case LOADING_ID_MOVIE_REVIEWS:
                    Reviews reviews = (Reviews) objectData;
                    detailBinding.itemDetails.reviewContainer.reviewValue.setText(String.valueOf(reviews.getTotalResults()));
                    reviewAdapter.newData(reviews.getReviewItems());

                    break;

                case LOADING_ID_MOVIE_VIDEOS:

                    Videos videos = (Videos) objectData;
                    videosAdapter.newData(videos.getVideoItems());
                    break;

                case LOADING_ID_MOVIE_DATABASE:

                    Cursor cursor = (Cursor) objectData;

                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        detailBinding.likeButton.setLiked(true);
                    }
                    break;
            }
        }

    }

    @Override
    public void onLoaderReset(Loader loader) { }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        if (0 < reviewAdapter.getItemCount()) {
            ArrayList<ReviewResultsItem> reviewItemsStateList = new ArrayList<>(reviewAdapter.getReviewResults());
            outState.putParcelableArrayList(INSTANCE_STATE_LIST_REVIEWS, reviewItemsStateList);
        }

        if (0 < videosAdapter.getItemCount()) {
            ArrayList<VideoResultsItem> videoResultsItems = new ArrayList<>(videosAdapter.getVideoResults());
            outState.putParcelableArrayList(INSTANCE_STATE_LIST_VIDEOS, videoResultsItems);
        }

        outState.putParcelable(INSTANCE_STATE_LIST_MOVIES, movieDetails);
        outState.putString(KEY_MOVIE_NO_OF_REVIEWS, detailBinding.itemDetails.reviewContainer.reviewValue.getText().toString());
    }


    public void toggleReviewDetails(View view){

        if(toggleUserReviews) {
            detailBinding.itemDetails.reviewContainer.authorReviewsList.setVisibility(View.VISIBLE);
            detailBinding.itemDetails.reviewContainer.downButton.setImageResource(R.drawable.ic_twotone_keyboard_arrow_down_24px);
        } else {
            detailBinding.itemDetails.reviewContainer.authorReviewsList.setVisibility(View.GONE);
            detailBinding.itemDetails.reviewContainer.downButton.setImageResource(R.drawable.ic_twotone_keyboard_arrow_up_24px);
        }

        int iDpi = getResources().getDisplayMetrics().densityDpi;

        detailBinding.nestedScroll.smoothScrollBy(0, iDpi);
        toggleUserReviews = !toggleUserReviews;

    }

    @Override
    public void onVideoItemClick(int iClickedIndex, String videoKey) {

        String videoPath = YOUTUBE_BASE_URL + videoKey;

        Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath));

        Intent chooseWith = Intent.createChooser(videoIntent, "Open With");

        if(null != videoIntent.resolveActivity(getPackageManager())){
            startActivity(chooseWith);
        }
    }
}
||||||| merged common ancestors
=======
package www.androidcitizen.com.popularmoviesone.ui;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.like.LikeButton;
import com.like.OnLikeListener;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.Loader.GlideApp;
import www.androidcitizen.com.popularmoviesone.data.Loader.ReviewsLoader;
import www.androidcitizen.com.popularmoviesone.data.Loader.VideosLoader;
import www.androidcitizen.com.popularmoviesone.data.adapter.VideosAdapter;
import www.androidcitizen.com.popularmoviesone.data.model.ReviewResultsItem;
import www.androidcitizen.com.popularmoviesone.data.adapter.ReviewAdapter;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract.*;
import www.androidcitizen.com.popularmoviesone.data.model.Reviews;
import www.androidcitizen.com.popularmoviesone.data.model.VideoResultsItem;
import www.androidcitizen.com.popularmoviesone.data.model.Videos;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMovieDetailBinding;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;

public class MovieDetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks, VideosAdapter.VideoClickListener {

    private ActivityMovieDetailBinding detailBinding;

    private MovieDetails movieDetails;

    private ReviewAdapter reviewAdapter;
    private VideosAdapter videosAdapter;

    private static boolean toggleUserReviews = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        setupRecycleView();

        if(null == savedInstanceState) {

            Intent intent = getIntent();
            if (intent.hasExtra(GlobalRef.KEY_MOVIE_DETAILS_DATA)) {

                movieDetails = getIntent().getParcelableExtra(GlobalRef.KEY_MOVIE_DETAILS_DATA);

                if (null != movieDetails) {
                    setViewData(movieDetails);
                }

                fetchReviewsAndVideosAndDataBase(GlobalRef.LOAD_MOVIE_REVIEWS_AND_VIDEOS);
            }

        } else {

                List<ReviewResultsItem> reviewsItems = savedInstanceState.getParcelableArrayList(GlobalRef.INSTANCE_STATE_LIST_REVIEWS);
                reviewAdapter.newData(reviewsItems);


                List<VideoResultsItem> videoItems = savedInstanceState.getParcelableArrayList(GlobalRef.INSTANCE_STATE_LIST_VIDEOS);
                videosAdapter.newData(videoItems);

                movieDetails = savedInstanceState.getParcelable(GlobalRef.INSTANCE_STATE_LIST_MOVIES);

                if(null != movieDetails) {
                    setViewData(movieDetails);

                    String noOfReviews = savedInstanceState.getString(GlobalRef.KEY_MOVIE_NO_OF_REVIEWS);
                    detailBinding.itemDetails.reviewContainer.reviewValue.setText(noOfReviews);
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

    void fetchReviewsAndVideosAndDataBase(int iIndex) {

        switch (iIndex) {
            case GlobalRef.LOAD_MOVIE_REVIEWS_AND_VIDEOS:

                Bundle bundle = new Bundle();
                bundle.putInt(GlobalRef.KEY_MOVIE_ID, movieDetails.getId());

                getLoaderManager().restartLoader(GlobalRef.LOADING_ID_MOVIE_REVIEWS, bundle, this);
                getLoaderManager().restartLoader(GlobalRef.LOADING_ID_MOVIE_VIDEOS, bundle, this);
                break;

            case GlobalRef.CHECK_DB_IF_MOVIE_IS_FAVORITE:
                getLoaderManager().restartLoader(GlobalRef.LOADING_ID_MOVIE_DATABASE, null, this);
                break;
        }
    }

    void setViewData(MovieDetails movieUIDetails){

        getSupportActionBar().setTitle(movieUIDetails.getTitle());

        fetchReviewsAndVideosAndDataBase(GlobalRef.CHECK_DB_IF_MOVIE_IS_FAVORITE);

        GlideApp.with(this)
                .load(GlobalRef.PORT_BACKDROP_IMAGE_URL_PATH + movieUIDetails.getBackdropPath())
                .into(detailBinding.ivBackgroundImage);

        GlideApp.with(this)
                .load(GlobalRef.PORT_POSTER_IMAGE_URL_PATH  + movieUIDetails.getPosterPath())
                .into(detailBinding.ivPosterImage);

        detailBinding.tvTitle.setText(movieUIDetails.getTitle());
        detailBinding.ratingBar.setRating(movieUIDetails.getVoteAverage());
        detailBinding.tvReleaseDate.setText(movieUIDetails.getReleaseDate());
        detailBinding.itemDetails.tvOverView.setText(movieUIDetails.getOverview());

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

        //Set Review Adapter
        reviewAdapter = new ReviewAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        detailBinding.itemDetails.reviewContainer.authorReviewsList.setHasFixedSize(true);
        detailBinding.itemDetails.reviewContainer.authorReviewsList.setLayoutManager(layoutManager);
        detailBinding.itemDetails.reviewContainer.authorReviewsList.setItemAnimator(new DefaultItemAnimator());
        detailBinding.itemDetails.reviewContainer.authorReviewsList.setAdapter(reviewAdapter);

        //Set Video Adapter
        videosAdapter = new VideosAdapter(this);

        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        detailBinding.itemDetails.movieTrailersList.setHasFixedSize(true);
        detailBinding.itemDetails.movieTrailersList.setLayoutManager(videoLayoutManager);
        detailBinding.itemDetails.movieTrailersList.setItemAnimator(new DefaultItemAnimator());
        detailBinding.itemDetails.movieTrailersList.setAdapter(videosAdapter);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle bundleArgs) {

        switch (id) {

            case GlobalRef.LOADING_ID_MOVIE_REVIEWS:
                return new ReviewsLoader(this, bundleArgs);

            case GlobalRef.LOADING_ID_MOVIE_VIDEOS:
                return new VideosLoader(this, bundleArgs);

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

        if(null != objectData) {

            switch (loader.getId()) {

                case GlobalRef.LOADING_ID_MOVIE_REVIEWS:
                    Reviews reviews = (Reviews) objectData;
                    detailBinding.itemDetails.reviewContainer.reviewValue.setText(String.valueOf(reviews.getTotalResults()));
                    reviewAdapter.newData(reviews.getReviewItems());

                    break;

                case GlobalRef.LOADING_ID_MOVIE_VIDEOS:

                    Videos videos = (Videos) objectData;
                    videosAdapter.newData(videos.getVideoItems());
                    break;

                case GlobalRef.LOADING_ID_MOVIE_DATABASE:

                    Cursor cursor = (Cursor) objectData;

                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        detailBinding.likeButton.setLiked(true);
                    }
                    break;
            }
        }

    }

    @Override
    public void onLoaderReset(Loader loader) { }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        if (0 < reviewAdapter.getItemCount()) {
            ArrayList<ReviewResultsItem> reviewItemsStateList = new ArrayList<>(reviewAdapter.getReviewResults());
            outState.putParcelableArrayList(GlobalRef.INSTANCE_STATE_LIST_REVIEWS, reviewItemsStateList);
        }

        if (0 < videosAdapter.getItemCount()) {
            ArrayList<VideoResultsItem> videoResultsItems = new ArrayList<>(videosAdapter.getVideoResults());
            outState.putParcelableArrayList(GlobalRef.INSTANCE_STATE_LIST_VIDEOS, videoResultsItems);
        }

        outState.putParcelable(GlobalRef.INSTANCE_STATE_LIST_MOVIES, movieDetails);
        outState.putString(GlobalRef.KEY_MOVIE_NO_OF_REVIEWS, detailBinding.itemDetails.reviewContainer.reviewValue.getText().toString());
    }


    public void toggleReviewDetails(View view){

        if(toggleUserReviews) {
            detailBinding.itemDetails.reviewContainer.authorReviewsList.setVisibility(View.VISIBLE);
            detailBinding.itemDetails.reviewContainer.downButton.setImageResource(R.drawable.ic_twotone_keyboard_arrow_down_24px);
        } else {
            detailBinding.itemDetails.reviewContainer.authorReviewsList.setVisibility(View.GONE);
            detailBinding.itemDetails.reviewContainer.downButton.setImageResource(R.drawable.ic_twotone_keyboard_arrow_up_24px);
        }

        toggleUserReviews = !toggleUserReviews;

    }

    @Override
    public void onVideoItemClick(int iClickedIndex, String videoKey) {

        String videoPath = GlobalRef.YOUTUBE_BASE_URL + videoKey;

        Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath));

        Intent chooseWith = Intent.createChooser(videoIntent, "Open With");

        if(null != videoIntent.resolveActivity(getPackageManager())){
            startActivity(chooseWith);
        }
    }
}
>>>>>>> 5bd5f9266ec76f4bbe33414f7c43d01943498a61
