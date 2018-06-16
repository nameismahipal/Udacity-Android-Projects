package www.androidcitizen.com.popularmoviesone.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.popularmoviesone.model.Movie;
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class JsonUtils {

    private final static String KEY_RESULTS = "results";
    private final static String KEY_MOVIE_ID = "id";
    private final static String KEY_VOTE_AVERAGE = "vote_average";
    private final static String KEY_TITLE = "title";
    private final static String KEY_POSTER_PATH = "poster_path";
    private final static String KEY_BACKDROP_PATH = "backdrop_path";
    private final static String KEY_OVERVIEW = "overview";
    private final static String KEY_RELEASE_DATE = "release_date";

    public static List<Movie> processMovieOverviewJsonData(String jsonResponse)
            throws JSONException {

        List<Movie> movies = new ArrayList<>();

        JSONObject rootObject = new JSONObject(jsonResponse);

        JSONArray resultsArray = rootObject.optJSONArray(KEY_RESULTS);

        for (int index = 0; index < resultsArray.length(); index++) {
            JSONObject resultObject = resultsArray.optJSONObject(index);

            movies.add(new Movie(resultObject.optString(KEY_TITLE),
                    resultObject.optInt(KEY_MOVIE_ID),
                    resultObject.optString(KEY_POSTER_PATH),
                    Float.parseFloat(resultObject.optString(KEY_VOTE_AVERAGE)))
            );

        }

        return movies;

    }

    public static MovieDetails processMovieDetailsJsonData(String jsonResponse)
            throws JSONException {

        MovieDetails moviesDetails;

        JSONObject rootObject = new JSONObject(jsonResponse);

            moviesDetails = new MovieDetails(rootObject.optString(KEY_TITLE),
                    rootObject.optInt(KEY_MOVIE_ID),
                    rootObject.optString(KEY_POSTER_PATH),
                    Float.parseFloat(rootObject.optString(KEY_VOTE_AVERAGE)),
                    rootObject.optString(KEY_BACKDROP_PATH),
                    rootObject.optString(KEY_OVERVIEW),
                    rootObject.optString(KEY_RELEASE_DATE)
            );

        return moviesDetails;

    }

}