package www.androidcitizen.com.popularmoviesone.data.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.remote.MovieInterface;
import www.androidcitizen.com.popularmoviesone.data.remote.MovieService;
import www.androidcitizen.com.popularmoviesone.data.model.Movie;

/**
 * Created by Mahi on 27/06/18.
 * www.androidcitizen.com
 *
 * Updated on 30/07/18
 *
 */

public class MovieLoader extends AsyncTaskLoader<Object> {

    Movie movieChache;
    Bundle bundle;

    public MovieLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
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
    public Object loadInBackground() {
        Movie movie = null;

        int index = bundle.getInt(GlobalRef.MOVIE_SERVICE_LOADING_KEY);

        Call<Movie> movieServiceCall = fetchMovies(index);

        try {

            Response<Movie> movieResponse = movieServiceCall.execute();

            if(movieResponse.isSuccessful()) {

                movie = movieResponse.body();

            } else {
                movie = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return movie;
    }

    @Override
    public void deliverResult(Object data) {
        movieChache = (Movie) data;
        super.deliverResult(data);
    }

    /*
        @Override
        public Movie loadInBackground() {

            Movie movie = null;

            int index = bundle.getInt(GlobalRef.MOVIE_SERVICE_LOADING_KEY);

            Call<Movie> movieServiceCall = fetchMovies(index);

            try {

                Response<Movie> movieResponse = movieServiceCall.execute();

                if(movieResponse.isSuccessful()) {

                    movie = movieResponse.body();

                } else {
                    movie = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return movie;
        }

        @Override
        public void deliverResult(Movie data) {
            movieChache = data;
            super.deliverResult(data);
        }
    */

    private MovieService initializeMovieService(){

        MovieService movieService = MovieInterface.getMovieInterface().create(MovieService.class);
        return movieService;
    }

    private Call<Movie> fetchMovies(int movieIndex){

        Call<Movie> moviesServiceCall = null;

        switch (movieIndex) {

            case GlobalRef.ALL_MOVIES_INDEX:
                                moviesServiceCall = fetchAllMovies();
                                break;
            case GlobalRef.TOPRATED_MOVIES_INDEX:
                                moviesServiceCall = fetchTopRatedMovies();
                                break;
            case GlobalRef.POPULAR_MOVIES_INDEX:
                                moviesServiceCall = fetchPopularMovies();
                                break;
            case GlobalRef.FAVOURITE_MOVIES_INDEX:
                                //moviesServiceCall = fetchFavouriteMovies();
                                break;
        }

        return moviesServiceCall;
    }

    private void fetchFavouriteMovies() {
        // Call Loader for quering data from database, and set to adapter
    }

    private Call<Movie> fetchAllMovies() {

        Call<Movie> moviesServiceCall = initializeMovieService().getAllMovies(GlobalRef.API_KEY, GlobalRef.PAGE_NUM);

        return moviesServiceCall;
    }

    private Call<Movie>  fetchTopRatedMovies() {

        Call<Movie> moviesServiceCall = initializeMovieService().getTopRatedMovies(GlobalRef.API_KEY, GlobalRef.PAGE_NUM);

        return moviesServiceCall;
    }

    private Call<Movie>  fetchPopularMovies() {

        Call<Movie> moviesServiceCall = initializeMovieService().getPopularMovies(GlobalRef.API_KEY, GlobalRef.PAGE_NUM);

        return moviesServiceCall;
    }

}
