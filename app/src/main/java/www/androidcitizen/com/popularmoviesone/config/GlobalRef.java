package www.androidcitizen.com.popularmoviesone.config;

import android.net.Uri;

import www.androidcitizen.com.popularmoviesone.BuildConfig;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public final class GlobalRef {

    private GlobalRef() { }

    public final static  String API_KEY = BuildConfig.ApiKey;

    public static final String[] PROJECTION = new String[] {
            // Any Changes here, make sure the below index values match
            FavContract.FavMovieEntry.COLUMN_MOVIE_ID,
            FavContract.FavMovieEntry.COLUMN_TITLE,
            FavContract.FavMovieEntry.COLUMN_POSTER_PATH,
            FavContract.FavMovieEntry.COLUMN_BACKDROP_PATH,
            FavContract.FavMovieEntry.COLUMN_RELEASE_DATE,
            FavContract.FavMovieEntry.COLUMN_OVERVIEW,
            FavContract.FavMovieEntry.COLUMN_VOTE_AVERAGE
    };

    public static final int INDEX_MOVIE_ID      = 0;
    public static final int INDEX_TITLE         = 1;
    public static final int INDEX_POSTER_PATH   = 2;
    public static final int INDEX_BACKDROP_PATH = 3;
    public static final int INDEX_RELEASE_DATE  = 4;
    public static final int INDEX_OVERVIEW      = 5;
    public static final int INDEX_VOTE_AVERAGE  = 6;

    public static int PAGE_NUM = 1;
    public static int TOTAL_ITEMS_COUNT = 1;

    public static final String INSTANCE_STATE_LIST = "adapter_list";
    public static final String INSTANCE_STATE_MOVIE_TYPE_INDEX = "movie_type_index";

    public final static int ALL_MOVIES_INDEX       = 0;
    public final static int TOPRATED_MOVIES_INDEX  = 1;
    public final static int POPULAR_MOVIES_INDEX   = 2;
    public final static int FAVOURITE_MOVIES_INDEX = 3;

    public final static int    MOVIE_SERVER_LOADING_ID = 11;
    public final static String MOVIE_SERVICE_LOADING_KEY = "MOVIE_SERVICE_CALL";

    public final static int    MOVIE_DATABASE_LOADING_ID = 12;
    public final static String FAV_MOVIE_DB_FAV_ITEM     = "FAV_MOVIE_DB_FAV_ITEM";
    public final static String FAV_MOVIE_DB_FAV_MOVIE_ID = "FAV_MOVIE_DB_MOVIE_ID";

    public final static String FAV_MOVIE_DB_KEY   = "FAV_MOVIE_DB_UPDATE_KEY";
    public final static int FAV_MOVIE_NULL    = 20;
    public final static int FAV_MOVIE_ADD     = 21;
    public final static int FAV_MOVIE_DELETE  = 22;
    public final static int FAV_MOVIE_READ    = 23;

    private final static String IMAGE_BASE_URL        = "https://image.tmdb.org/t/p";

    private final static  String PORTRAIT_POSTER_SIZE           = "/w154";
    private final static  String PORTRAIT_BACKDROP_POSTER_SIZE  = "/w300";

    public final static String PORT_POSTER_IMAGE_URL_PATH   = IMAGE_BASE_URL + PORTRAIT_POSTER_SIZE;
    public final static String PORT_BACKDROP_IMAGE_URL_PATH = IMAGE_BASE_URL + PORTRAIT_BACKDROP_POSTER_SIZE;


    /*
    https://api.themoviedb.org/3/discover/movie?api_key=231770e320e461bebfa9c748217ae54e

    https://api.themoviedb.org/3/movie/popular?api_key=231770e320e461bebfa9c748217ae54e

    https://api.themoviedb.org/3/movie/now_playing?api_key=231770e320e461bebfa9c748217ae54e

    https://api.themoviedb.org/3/movie/top_rated?api_key=231770e320e461bebfa9c748217ae54e

    https://api.themoviedb.org/3/movie/upcoming?api_key=231770e320e461bebfa9c748217ae54e

    movie-id
    https://api.themoviedb.org/3/movie/{movie_id}/images?api_key=231770e320e461bebfa9c748217ae54e

    https://api.themoviedb.org/3/movie/351286?api_key=231770e320e461bebfa9c748217ae54e


     */



}
