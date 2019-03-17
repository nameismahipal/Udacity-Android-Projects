package www.androidcitizen.com.popularmoviesone.data.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import www.androidcitizen.com.popularmoviesone.data.model.Reviews;
import www.androidcitizen.com.popularmoviesone.data.remote.MovieInterface;
import www.androidcitizen.com.popularmoviesone.data.remote.MovieService;

import static www.androidcitizen.com.popularmoviesone.config.Constants.*;

/**
 * Created by Mahi on 27/06/18.
 * www.androidcitizen.com
 *
 * Updated on 30/07/18
 *
 */

public class ReviewsLoader extends AsyncTaskLoader<Object> {

    Reviews reviewChache;
    Bundle bundle;

    public ReviewsLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(null != reviewChache) {
            deliverResult(reviewChache);
        } else {
            forceLoad();
        }
    }

    @Override
    public Object loadInBackground() {

        Reviews reviews = null;

        int movieId = bundle.getInt(KEY_MOVIE_ID);

        Call<Reviews> movieReviewsCall = initializeMovieService().getMovieReviews(movieId, API_KEY);

        try {

            Response<Reviews> reviewsResponse = movieReviewsCall.execute();

            if(reviewsResponse.isSuccessful()) {

                reviews = reviewsResponse.body();

            } else {
                reviews = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        movieReviewsCall.cancel();

        return reviews;
    }

    @Override
    public void deliverResult(Object data) {
        reviewChache = (Reviews) data;
        super.deliverResult(reviewChache);
    }


    private MovieService initializeMovieService(){

        MovieService movieService = MovieInterface.getMovieInterface().create(MovieService.class);
        return movieService;
    }



}
