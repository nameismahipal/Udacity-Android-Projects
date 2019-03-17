package www.androidcitizen.com.popularmoviesone.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import www.androidcitizen.com.popularmoviesone.data.model.Movie;
import www.androidcitizen.com.popularmoviesone.data.model.Reviews;
import www.androidcitizen.com.popularmoviesone.data.model.Videos;

/**
 * Created by Mahi on 01/07/18.
 * www.androidcitizen.com
 */

public interface MovieService {

    @GET("movie/top_rated")
    Call<Movie> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int pageNum);

    @GET("movie/popular")
    Call<Movie> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int pageNum);

    @GET("movie/now_playing")
    Call<Movie> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int pageNum);

    @GET("movie/{movie_id}/videos")
    Call<Videos> getMovieVideos(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<Reviews> getMovieReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}
