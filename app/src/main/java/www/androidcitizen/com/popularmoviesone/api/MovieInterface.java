package www.androidcitizen.com.popularmoviesone.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mahi on 01/07/18.
 * www.androidcitizen.com
 */

public class MovieInterface {

    private final static String MOVIES_BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit;

    public static Retrofit getMovieInterface() {

        if(null == retrofit) {
             retrofit = new Retrofit.Builder()
                     .baseUrl(MOVIES_BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .build();
        }

        return retrofit;
    }
}
