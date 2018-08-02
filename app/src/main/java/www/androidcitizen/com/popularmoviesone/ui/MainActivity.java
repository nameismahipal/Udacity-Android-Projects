package www.androidcitizen.com.popularmoviesone.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.stetho.Stetho;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.config.GlobalRef;
import www.androidcitizen.com.popularmoviesone.data.adapter.MovieAdapter;
import www.androidcitizen.com.popularmoviesone.data.database.FavContract;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMainBinding;
import www.androidcitizen.com.popularmoviesone.data.model.Movie;
import www.androidcitizen.com.popularmoviesone.data.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.pagination.EndlessScrollListener;
import www.androidcitizen.com.popularmoviesone.data.Loader.MovieLoader;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieClickListener,
        LoaderManager.LoaderCallbacks,
        PopupMenu.OnMenuItemClickListener {

    ActivityMainBinding mainBinding;

    private static MovieAdapter adapter;
    private EndlessScrollListener scrollListener;

    private static int TOTAL_PAGES = 1;

    private static int MOVIE_FETCH_INDEX = GlobalRef.ALL_MOVIES_INDEX;

    private final static String API_KEY = GlobalRef.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Stetho.initializeWithDefaults(this);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Bundle bundle1 = new Bundle();
        bundle1.putInt(GlobalRef.MOVIE_SERVICE_LOADING_KEY, MOVIE_FETCH_INDEX);

        Bundle bundle2 = new Bundle();
        bundle2.putInt(GlobalRef.FAV_MOVIE_DB_KEY, GlobalRef.FAV_MOVIE_NULL);

        getLoaderManager().initLoader(GlobalRef.MOVIE_SERVER_LOADING_ID, bundle1, this);
        //getLoaderManager().initLoader(GlobalRef.MOVIE_DATABASE_LOADING_ID, bundle2, this);

        setupRecycleView();
    }

    private void fetchMovies(int iIndex) {

        if(GlobalRef.FAVOURITE_MOVIES_INDEX == iIndex) {
            Bundle bundle = new Bundle();
            bundle.putInt(GlobalRef.FAV_MOVIE_DB_KEY, GlobalRef.FAV_MOVIE_READ);

            //getLoaderManager().destroyLoader(GlobalRef.MOVIE_DATABASE_LOADING_ID);
            getLoaderManager().restartLoader(GlobalRef.MOVIE_DATABASE_LOADING_ID, bundle, this);

        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(GlobalRef.MOVIE_SERVICE_LOADING_KEY, iIndex);

            getLoaderManager().destroyLoader(GlobalRef.MOVIE_SERVER_LOADING_ID);
            getLoaderManager().restartLoader(GlobalRef.MOVIE_SERVER_LOADING_ID, bundle, this);

        }
    }

    private void setupRecycleView(){
        int spanCount = getColumnValue(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);

        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                GlobalRef.PAGE_NUM = page;
                if(page < TOTAL_PAGES){
                    fetchMovies(MOVIE_FETCH_INDEX);
                }
            }
        };

        mainBinding.rvMovieList.setHasFixedSize(true);
        mainBinding.rvMovieList.setLayoutManager(layoutManager);
        mainBinding.rvMovieList.addOnScrollListener(scrollListener);

        adapter = new MovieAdapter(this);

        mainBinding.rvMovieList.setAdapter(adapter);
    }

    private static int getColumnValue(Context context){
        if(context.getResources().getBoolean(R.bool.is_portrait)){
            return 2;
        } else {
            return 4;
        }
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex, MovieDetails movieDetails) {

            Intent movieDetailActivity = new Intent(this, MovieDetailActivity.class);
            movieDetailActivity.putExtra("movieDetailsdata", movieDetails);
            startActivity(movieDetailActivity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.ic_filter:
                View view = findViewById(R.id.ic_filter);
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.inflate(R.menu.menu_actions);
                popupMenu.getMenu().getItem(MOVIE_FETCH_INDEX).setChecked(true);
                popupMenu.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                MOVIE_FETCH_INDEX = GlobalRef.ALL_MOVIES_INDEX;
                break;
            case R.id.toprated:
                MOVIE_FETCH_INDEX = GlobalRef.TOPRATED_MOVIES_INDEX;
                break;
            case R.id.popular:
                MOVIE_FETCH_INDEX = GlobalRef.POPULAR_MOVIES_INDEX;
                break;
            case R.id.favourite:
                MOVIE_FETCH_INDEX = GlobalRef.FAVOURITE_MOVIES_INDEX;
                break;
        }

        clearList();
        GlobalRef.PAGE_NUM = 1;
        fetchMovies(MOVIE_FETCH_INDEX);

        return true;
    }

    private void clearList() {
        adapter.clearAll();
        scrollListener.resetState();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundleArgs) {

        switch (id) {

            case GlobalRef.MOVIE_SERVER_LOADING_ID:

                return new MovieLoader(this, bundleArgs);

            case GlobalRef.MOVIE_DATABASE_LOADING_ID:

                int iDbAction = bundleArgs.getInt(GlobalRef.FAV_MOVIE_DB_KEY);

                switch (iDbAction) {

                    case GlobalRef.FAV_MOVIE_NULL:
                            return null;

                    case GlobalRef.FAV_MOVIE_READ:
                            return new CursorLoader(this,
                            FavContract.FavMovieEntry.CONTENT_URI,
                            GlobalRef.PROJECTION,
                            null, null, null);
                }
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        int id = loader.getId();

        switch (id) {
            case GlobalRef.MOVIE_SERVER_LOADING_ID:
                setupAdapterServerData(data);
                break;

            case GlobalRef.MOVIE_DATABASE_LOADING_ID:
                setupAdapterDatabaseData(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void setupAdapterServerData(Object object){

        Movie movieObj = (Movie) object;
        GlobalRef.PAGE_NUM = movieObj.getPage();
        TOTAL_PAGES = movieObj.getTotalPages();
        adapter.newData(movieObj.getMovieDetails());
    }

    private void setupAdapterDatabaseData(Object object){

        Cursor cursor = (Cursor) object;
        adapter.newCursorData(cursor);
    }

}
