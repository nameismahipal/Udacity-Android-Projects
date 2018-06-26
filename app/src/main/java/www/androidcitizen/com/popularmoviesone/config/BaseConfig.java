package www.androidcitizen.com.popularmoviesone.config;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class BaseConfig {

    public final static  String API_KEY = "-";

    private final static String IMAGE_BASE_URL        = "https://image.tmdb.org/t/p";
    public final static String MOVIES_BASE_URL       = "https://api.themoviedb.org/3/movie";
    public final static String DISCOVER_MOVIES_URL   = "https://api.themoviedb.org/3/discover/movie";

    public final static String POPULAR_BASE_URL       = "https://api.themoviedb.org/3/movie/popular";
    public final static String TOPRATED_BASE_URL       = "https://api.themoviedb.org/3/movie/top_rated";

    public static String CURRENT_BASE_URL = DISCOVER_MOVIES_URL;

    private final static  String PORTRAIT_POSTER_SIZE           = "/w154";
    private final static  String PORTRAIT_BACKDROP_POSTER_SIZE  = "/w300";

    public final static String PORT_POSTER_IMAGE_URL_PATH   = IMAGE_BASE_URL + PORTRAIT_POSTER_SIZE;
    public final static String PORT_BACKDROP_IMAGE_URL_PATH = IMAGE_BASE_URL + PORTRAIT_BACKDROP_POSTER_SIZE;

    public final static String MOVIE_ID = "mdb_movie_id";

    public final static  String POPULAR_KEY       = "popular";
    public final static  String TOP_RATED_KEY     = "top_rated";
    public final static  String NOW_PLAYING_KEY   = "now_playing";
    public final static  String UPCOMING_KEY      = "upcoming";

    public final static  String apiKey  = "api_key";


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
