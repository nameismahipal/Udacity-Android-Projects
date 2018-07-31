package www.androidcitizen.com.popularmoviesone.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mahi on 01/07/18.
 * www.androidcitizen.com
 */

public class MovieInterface {

    private final static String MOVIES_BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit;

    private static volatile MovieService movieService;

    private MovieInterface() {

        //prevention from reflection api (to avoid creating another instance)
        if(null != movieService) {
            throw new RuntimeException("Use getMovieService() to get single instance of the class");
        }
    }

    public static Retrofit getMovieInterface() {

        if(null == retrofit) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor());

            retrofit = new Retrofit.Builder()
                     .baseUrl(MOVIES_BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .client(httpClient.build())
                     .build();
        }

        return retrofit;
    }

    public static MovieService getMovieService(){
        if(null == movieService) {

            synchronized (MovieInterface.class) {

                if(null == movieService) {
                    movieService = getMovieInterface().create(MovieService.class);
                }
            }
        }

        return movieService;
    }
}
