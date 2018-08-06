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

    public static final String INSTANCE_STATE_LIST_MOVIES = "save_movies_list";
    public static final String INSTANCE_STATE_LIST_REVIEWS = "save_reviews_list";
    public static final String INSTANCE_STATE_LIST_VIDEOS = "save_videos_list";

    public static final String INSTANCE_STATE_MOVIE_TYPE_INDEX = "save_movie_type_index";
    public static final String INSTANCE_STATE_TOOLBAR_MOVIE_TITLE = "save_title_toolbar";

    public final static int NOW_PLAYING_MOVIES = 0;
    public final static int TOPRATED_MOVIES_INDEX  = 1;
    public final static int POPULAR_MOVIES_INDEX   = 2;
    public final static int FAVOURITE_MOVIES_INDEX = 3;

    public final static int LOAD_MOVIE_REVIEWS_AND_VIDEOS = 4;
    public final static int CHECK_DB_IF_MOVIE_IS_FAVORITE = 5;

    public final static int LOADING_ID_MOVIE_SERVER = 11;
    public final static String KEY_MOVIE_SERVICE_LOADING = "key_movie_service_call";

    public final static String KEY_MOVIE_ID = "key_movie_id";
    public final static String KEY_MOVIE_NO_OF_REVIEWS = "key_num_of_reviews";
    public final static String KEY_MOVIE_DETAILS_DATA = "key_movie_details_data";

    public final static int LOADING_ID_MOVIE_DATABASE = 12;
    public final static int LOADING_ID_MOVIE_REVIEWS = 13;
    public final static int LOADING_ID_MOVIE_VIDEOS = 14;

    public final static String FAV_MOVIE_DB_FAV_ITEM     = "fav_movie_db_fav_item";
    public final static String FAV_MOVIE_DB_FAV_MOVIE_ID = "fav_movie_db_movie_id";


    public final static String KEY_FAV_MOVIE_ID = "fav_movie_db_update_key";
    public final static int FAV_MOVIE_NULL    = 20;
    public final static int FAV_MOVIE_ADD     = 21;
    public final static int FAV_MOVIE_DELETE  = 22;
    public final static int FAV_MOVIE_READ    = 23;

    private final static String IMAGE_BASE_URL              = "https://image.tmdb.org/t/p";
    public final static String YOUTUBE_BASE_URL             = "https://www.youtube.com/watch?v=";
    public final static String YOUTUBE_THUMBNAIL_BASE_URL   = "https://img.youtube.com/vi/";
    public final static String YOUTUBE_THUMBNAIL_END_URL   = "/0.jpg";

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

    video:
    key=vn9mMeWcgoM
    https://www.youtube.com/watch?v=vn9mMeWcgoM

    https://api.themoviedb.org/3/movie/299536/videos?api_key=231770e320e461bebfa9c748217ae54e

    Reviews:
    https://api.themoviedb.org/3/movie/299536/reviews?api_key=<KEY>&page=1


     */



}
