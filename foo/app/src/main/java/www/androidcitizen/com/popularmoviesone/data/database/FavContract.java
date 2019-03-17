package www.androidcitizen.com.popularmoviesone.data.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mahi on 31/07/18.
 * www.androidcitizen.com
 */

public class FavContract {

    /*
    *  Step 1: Implement Contract Class:                    [ ****  THIS Class **** ]
    *
    *           -> Base Columns for Required Table.
    *           -> All Columns to be mentioned.
    *
    *  Step 2: Implement SQLiteOpenHelper Class:
    *           -> Database Name and its Version.
    *           -> Create Constructor, onCreate (create Table, execute), onUpgrade (delete, oncreate)
    *
    *  Step 3: Implement Content URI in Contract Class      [ ****  THIS Class **** ]
    *
    *           -> Set Authority, URI, path to identify the Table
    *           -> build URI.
    *
    *  Step 4: Implement Content Provider Class.
    *
    *           -> Implement Uri Matcher using Content URIs defined.
    *           -> Implement CRUD Methods with Data Validation.
    *
     *  Step 5: Use Content Resolver (from activity) to access CRUD methods from Content Provider.
    *
    *  Mention Provider in Manifest file.
    */

    public static final String AUTHORITY = "www.androidcitizen.com.popularmoviesone";
    public static final String PATH_FAV_MOVIES = "favorites";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private FavContract() {}

    public static final class FavMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV_MOVIES).build();

        public static Uri buildURIMovieId(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME               = "fav_movies";

        public static final String _ID                      = BaseColumns._ID;
        public static final String COLUMN_MOVIE_ID          = "movie_id";
        public static final String COLUMN_TITLE             = "title";
        public static final String COLUMN_POSTER_PATH       = "poster_path";
        public static final String COLUMN_BACKDROP_PATH     = "backdrop_path";
        public static final String COLUMN_RELEASE_DATE      = "release_date";
        public static final String COLUMN_OVERVIEW          = "overview";
        public static final String COLUMN_VOTE_AVERAGE      = "vote_average";

    }

}
