package www.androidcitizen.com.popularmoviesone.utilities;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;
import java.net.URL;

import www.androidcitizen.com.popularmoviesone.model.Movie;

/**
 * Created by Mahi on 27/06/18.
 * www.androidcitizen.com
 */

public class MovieLoader extends AsyncTaskLoader<Movie> {

    private static final String MOVIE_LOADING_KEY = "MOVIE_LOADING_KEY";
    private static final String MOVIE_PAGE_KEY = "MOVIE_PAGE";

    Movie movieChache;
    Bundle bundle;

    public MovieLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    public Movie loadInBackground() {
        String baseUrl = bundle.getString(MOVIE_LOADING_KEY); //strings[0];
        int pageNum = bundle.getInt(MOVIE_PAGE_KEY);

        URL searchUrl;
        String jsonResponse;

        Movie movie = null;

        searchUrl = NetworkUtils.buildUrl(baseUrl, pageNum);

        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            movie = JsonUtils.processMovieOverviewJsonData(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return movie;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(null != movieChache) {
            deliverResult(movieChache);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(Movie data) {
        movieChache = data;
        super.deliverResult(data);
    }

}
