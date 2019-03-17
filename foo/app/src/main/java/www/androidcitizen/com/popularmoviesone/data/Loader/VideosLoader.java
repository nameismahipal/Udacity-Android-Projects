<<<<<<< HEAD
package www.androidcitizen.com.popularmoviesone.data.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import www.androidcitizen.com.popularmoviesone.data.model.Videos;
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

public class VideosLoader extends AsyncTaskLoader<Object> {

    Videos videoCache;
    Bundle bundle;

    public VideosLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(null != videoCache) {
            deliverResult(videoCache);
        } else {
            forceLoad();
        }
    }

    @Override
    public Object loadInBackground() {

        Videos videos = null;

        int movieId = bundle.getInt(KEY_MOVIE_ID);

        Call<Videos> movieVideosCall = initializeMovieService().getMovieVideos(movieId, API_KEY);

        try {

            Response<Videos> videosResponse = movieVideosCall.execute();

            if(videosResponse.isSuccessful()) {

                videos = videosResponse.body();

            } else {
                videos = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        movieVideosCall.cancel();

        return videos;
    }

    @Override
    public void deliverResult(Object data) {
        videoCache = (Videos) data;
        super.deliverResult(videoCache);
    }


    private MovieService initializeMovieService(){

        MovieService movieService = MovieInterface.getMovieInterface().create(MovieService.class);
        return movieService;
    }



}
||||||| merged common ancestors
=======
package www.androidcitizen.com.popularmoviesone.data.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.model.Videos;
import www.androidcitizen.com.popularmoviesone.data.remote.MovieInterface;
import www.androidcitizen.com.popularmoviesone.data.remote.MovieService;

/**
 * Created by Mahi on 27/06/18.
 * www.androidcitizen.com
 *
 * Updated on 30/07/18
 *
 */

public class VideosLoader extends AsyncTaskLoader<Object> {

    Videos videoCache;
    Bundle bundle;

    public VideosLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(null != videoCache) {
            deliverResult(videoCache);
        } else {
            forceLoad();
        }
    }

    @Override
    public Object loadInBackground() {

        Videos videos = null;

        int movieId = bundle.getInt(GlobalRef.KEY_MOVIE_ID);

        Call<Videos> movieVideosCall = initializeMovieService().getMovieVideos(movieId, GlobalRef.API_KEY);

        try {

            Response<Videos> videosResponse = movieVideosCall.execute();

            if(videosResponse.isSuccessful()) {

                videos = videosResponse.body();

            } else {
                videos = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        movieVideosCall.cancel();

        return videos;
    }

    @Override
    public void deliverResult(Object data) {
        videoCache = (Videos) data;
        super.deliverResult(videoCache);
    }


    private MovieService initializeMovieService(){

        MovieService movieService = MovieInterface.getMovieInterface().create(MovieService.class);
        return movieService;
    }



}
>>>>>>> 5bd5f9266ec76f4bbe33414f7c43d01943498a61
