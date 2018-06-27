package www.androidcitizen.com.popularmoviesone.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import www.androidcitizen.com.popularmoviesone.Pagination.EndlessScrollListener;
import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.adapter.MovieAdapter;
import www.androidcitizen.com.popularmoviesone.config.BaseConfig;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMainBinding;
import www.androidcitizen.com.popularmoviesone.model.Movie;
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.utilities.MovieLoader;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieClickListener,
        LoaderManager.LoaderCallbacks<Movie>,
        PopupMenu.OnMenuItemClickListener {

    ActivityMainBinding mainBinding;

    private static MovieAdapter adapter;
    private EndlessScrollListener scrollListener;

    private static final int MOVIE_LOADING_ID = 301;
    private static final String MOVIE_LOADING_KEY = "MOVIE_LOADING_KEY";

    private static final String MOVIE_PAGE_KEY = "MOVIE_PAGE";
    private static int PAGE_NUM = 1;
    private static int TOTAL_PAGES = 1;
    private static int POP_ITEM_INDEX = 0;

    private final static int ALL_MOVIES_INDEX = 0;
    private final static int TOPRATED_MOVIES_INDEX = 1;
    private final static int POPULAR_MOVIES_INDEX = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupRecycleView();

        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_LOADING_KEY, BaseConfig.CURRENT_BASE_URL);
        bundle.putInt(MOVIE_PAGE_KEY, PAGE_NUM);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(MOVIE_LOADING_ID, bundle,this);

    }

    private void setupRecycleView(){
        int spanCount = getColumnValue(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);

        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(page < TOTAL_PAGES){
                    loadNewList(BaseConfig.CURRENT_BASE_URL, page);
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
                popupMenu.getMenu().getItem(POP_ITEM_INDEX).setChecked(true);
                popupMenu.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                POP_ITEM_INDEX = ALL_MOVIES_INDEX;
                BaseConfig.CURRENT_BASE_URL = BaseConfig.DISCOVER_MOVIES_URL;
                break;
            case R.id.toprated:
                POP_ITEM_INDEX = TOPRATED_MOVIES_INDEX;
                BaseConfig.CURRENT_BASE_URL = BaseConfig.TOPRATED_BASE_URL;
                break;
            case R.id.popular:
                POP_ITEM_INDEX = POPULAR_MOVIES_INDEX;
                BaseConfig.CURRENT_BASE_URL = BaseConfig.POPULAR_BASE_URL;
                break;
            default:
                return false;
        }

        setPopMenuCheckedAndCallUrl(item);
        return true;
    }

    private void setPopMenuCheckedAndCallUrl(MenuItem item) {
        clearList();
        item.setChecked(true);
        PAGE_NUM = 1;
        loadNewList(BaseConfig.CURRENT_BASE_URL, PAGE_NUM);
    }

    private void clearList() {
        adapter.clearAll();
        scrollListener.resetState();
    }

    private void loadNewList(String url, int pageNum) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_LOADING_KEY, url);
        bundle.putInt(MOVIE_PAGE_KEY, pageNum);
        getLoaderManager().restartLoader(MOVIE_LOADING_ID, bundle, this);
    }

    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle bundle) {

        return new MovieLoader(this, bundle);
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie movieObj) {

        if (null != movieObj) {
            Movie movie = movieObj;
            PAGE_NUM = movie.getPage();
            TOTAL_PAGES = movie.getTotalPages();
            adapter.newData(movieObj.getMovieDetails());
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

}
