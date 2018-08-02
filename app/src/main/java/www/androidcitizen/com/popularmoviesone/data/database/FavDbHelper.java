package www.androidcitizen.com.popularmoviesone.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import www.androidcitizen.com.popularmoviesone.data.database.FavContract.*;

/**
 * Created by Mahi on 31/07/18.
 * www.androidcitizen.com
 */

/*
 *  Step 1: Implement Contract Class:
 *
 *           -> Base Columns for Required Table.
 *           -> All Columns to be mentioned.
 *
 *  Step 2: Implement SQLiteOpenHelper Class:           [ ****  THIS Class **** ]
 *           -> Database Name and its Version.
 *           -> Create Constructor, onCreate (create Table, execute), onUpgrade (delete, oncreate)
 *
 *  Step 3: Implement Content URI in Contract Class
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

public class FavDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favoriteMovies.db";

    public FavDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE =

                        "CREATE TABLE " + FavMovieEntry.TABLE_NAME + "(" +
                        FavMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FavMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                        FavMovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                        FavMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL," +
                        FavMovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL," +
                        FavMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                        FavMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                        FavMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL" +
                        ")";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + FavMovieEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
