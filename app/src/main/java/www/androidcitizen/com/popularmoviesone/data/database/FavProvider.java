package www.androidcitizen.com.popularmoviesone.data.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.URI;

import www.androidcitizen.com.popularmoviesone.data.database.FavContract.*;

/**
 * Created by Mahi on 31/07/18.
 * www.androidcitizen.com
 */

public class FavProvider extends ContentProvider {

    /*
     *  Step 1: Implement Contract Class:
     *
     *           -> Base Columns for Required Table.
     *           -> All Columns to be mentioned.
     *
     *  Step 2: Implement SQLiteOpenHelper Class:
     *           -> Database Name and its Version.
     *           -> Create Constructor, onCreate (create Table, execute), onUpgrade (delete, oncreate)
     *
     *  Step 3: Implement Content URI in Contract Class
     *
     *           -> Set Authority, URI, path to identify the Table
     *           -> build URI.
     *
     *  Step 4: Implement Content Provider Class.           [ ****  THIS Class **** ]
     *
     *           -> Implement Uri Matcher using IDs, Content URIs defined.
     *           -> Implement CRUD Methods with Data Validation.
     *
     *  Step 5: Use Content Resolver (from activity) to access CRUD methods from Content Provider.
     *
     *  Mention Provider in Manifest file.
     */

    public static final int FAV_MOVIES      = 1000;
    public static final int FAV_MOVIES_ID   = 1001;

    private FavDbHelper favDbHelper;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*---------------------------------------------------------------------------
          |                        URI Pattern                             |  Code  |
          ---------------------------------------------------------------------------
          |                    Authority  / Path                           |   #    |
          ---------------------------------------------------------------------------
          |"content://www.androidcitizen.com.popularmoviesone/favorites"   |  1000  |
          ---------------------------------------------------------------------------
          |"content://www.androidcitizen.com.popularmoviesone/favorites/#" |  1001  |
          ---------------------------------------------------------------------------
         */

        uriMatcher.addURI(FavContract.AUTHORITY, FavContract.PATH_FAV_MOVIES, FAV_MOVIES);
        uriMatcher.addURI(FavContract.AUTHORITY,FavContract.PATH_FAV_MOVIES + "/#", FAV_MOVIES_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        favDbHelper = new FavDbHelper(context);

        return true;
    }

    /*
     * getType() is an abstract method in ContentProvider.
     * Normally, this method handles requests for the MIME type of the data at the
     * given URI. For example, if your app provided images at a particular URI, then you would
     * return an image URI from this method.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match){
            case FAV_MOVIES:
                return "vnd.android.cursor.dir" + "/" + FavContract.AUTHORITY + "/" + FavContract.PATH_FAV_MOVIES;
            case FAV_MOVIES_ID:
                return "vnd.android.cursor.item" + "/" + FavContract.AUTHORITY + "/" + FavContract.PATH_FAV_MOVIES;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        /*
        Step 1: Get READ Access of db Helper.
        Step 2: Match uri
        Step 3: Query Method for each case
        Step 4: Set a notification Uri on the cursor
         */
        Cursor retCursor;

        // Step 1
        final SQLiteDatabase db = favDbHelper.getReadableDatabase();

        // Step 2
        int matchId = uriMatcher.match(uri);

        // Step 3
        switch (matchId) {

            case FAV_MOVIES:
                // SELECT * FROM <TABLE NAME>
                retCursor = db.query(FavMovieEntry.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                        null, null,
                            sortOrder);

                break;

            case FAV_MOVIES_ID:

                /* SELECT -id, name FROM <TABLE_NAME> WHERE _id=1
                *  selection = _id + "=?";
                *  selectionArgs = new String[] {id}
                */

                /*
                String id = String.valueOf(ContentUris.parseId(uri));
                OR
                 */
                String id = uri.getPathSegments().get(1);
                String lSelection = "movie_id=?";
                String[] lSelectionArgs = new String[] {id};

                retCursor = db.query(FavMovieEntry.TABLE_NAME,
                            projection,
                            lSelection,
                            lSelectionArgs,
                        null,null ,
                            sortOrder);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }

        // Step 4
            // // Set a notification URI on the Cursor and return that Cursor.
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
         /*
        Step 1: Get WRITE Access of db Helper.
        Step 2: Match uri
        Step 3: Insert Method for each case
        Step 4: Notify Resolver
         */

        // Step 1
        final SQLiteDatabase db = favDbHelper.getWritableDatabase();

        // Step 2
        int matchId = uriMatcher.match(uri);

        Uri returnUri;

        // Insert happens to the table , and not to any specific Id.

        // Step 3
        switch (matchId) {

            case FAV_MOVIES:

                long id = db.insert(FavMovieEntry.TABLE_NAME,
                        null,
                        values);

                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(FavMovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }

        // Step 4
            // Notify all listeners that data has changed.
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        /*
        Step 1: Get WRITE Access of db Helper.
        Step 2: Match uri
        Step 3: Delete Method
        Step 4: Notify all listeners that data has changed.
         */

        int iNumOfItemsDeleted;

        // Step 1
        final SQLiteDatabase db = favDbHelper.getWritableDatabase();

        // Step 2
        int matchId = uriMatcher.match(uri);

        // Step 3
        switch (matchId) {

            case FAV_MOVIES:

                        // If selection is null, func delete whole table without any count of no. if items deleted.
                        // If selection is "1", func deletes whole table, also returns no. of items deleted
                        if(null == selection) selection = "1";

                        iNumOfItemsDeleted = db.delete(FavMovieEntry.TABLE_NAME,
                                            selection,
                                            selectionArgs);
                        break;

            case FAV_MOVIES_ID:

                        String id = uri.getPathSegments().get(1);
                        String lSelection = "movie_id=?";
                        String[] lSelectionArgs = new String[] {id};

                        iNumOfItemsDeleted = db.delete(FavMovieEntry.TABLE_NAME,
                                            lSelection,
                                            lSelectionArgs);
                        break;

                default: throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        // Step 4
        if (iNumOfItemsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return iNumOfItemsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        /*
        Step 1: Get WRITE Access of db Helper.
        Step 2: Match uri
        Step 3: Update Method
        Step 4: Notify all listeners that data has changed.
         */

        int iNumOfItemsUpdated;

        // Step 1
        final SQLiteDatabase db = favDbHelper.getWritableDatabase();

        // Step 2
        int matchId = uriMatcher.match(uri);

        switch (matchId) {

            case FAV_MOVIES_ID:

                String id = uri.getPathSegments().get(1);
                String lSelection = "_id=?";
                String[] lSelectionArgs = new String[] {id};

                iNumOfItemsUpdated = db.update(FavMovieEntry.TABLE_NAME, values,
                                    lSelection,
                                    lSelectionArgs);
                break;

            default: throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        // Step 4
        if (iNumOfItemsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }


        return iNumOfItemsUpdated;
    }
}
