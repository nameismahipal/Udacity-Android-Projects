package www.androidcitizen.com.popularmoviesone.ui;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.net.URL;

import www.androidcitizen.com.popularmoviesone.Pagination.EndlessScrollListener;
import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.adapter.MovieAdapter;
import www.androidcitizen.com.popularmoviesone.config.BaseConfig;
import www.androidcitizen.com.popularmoviesone.model.Movie;
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.utilities.JsonUtils;
import www.androidcitizen.com.popularmoviesone.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieClickListener,
        LoaderManager.LoaderCallbacks<Movie>,
        PopupMenu.OnMenuItemClickListener {

    private RecyclerView rvMovieGridList;
    private static MovieAdapter adapter;
    private EndlessScrollListener scrollListener;

    private static Movie movie = null;

    private static final int MOVIE_LOADING_ID = 301;
    private static final String MOVIE_LOADING_KEY = "MOVIE_LOADING_KEY";

    private static final String MOVIE_PAGE_KEY = "MOVIE_PAGE";
    private static int PAGE_NUM = 1;

    private static int POP_ITEM_INDEX = 0;

    private static boolean isLastPage = false;
    private static boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BaseConfig.setMovieOverviewActivity();

        rvMovieGridList = (RecyclerView) findViewById(R.id.rv_movieList);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(page < movie.getTotalPages()){
                    loadNewList(BaseConfig.DISCOVER_MOVIES_URL, page);
                }
            }
        };

        rvMovieGridList.setHasFixedSize(true);
        rvMovieGridList.setLayoutManager(layoutManager);
        rvMovieGridList.addOnScrollListener(scrollListener);

        adapter = new MovieAdapter(this);

        rvMovieGridList.setAdapter(adapter);

        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_LOADING_KEY, BaseConfig.DISCOVER_MOVIES_URL);
        bundle.putInt(MOVIE_PAGE_KEY, PAGE_NUM);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(MOVIE_LOADING_ID, bundle, this);

    }

    @Override
    public void onMovieItemClick(int clickedItemIndex, MovieDetails movieDetails) {

            //MovieDetails sendData = movie.getMovieDetails().get(clickedItemIndex);

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
                POP_ITEM_INDEX = 0;
                setPopMenuCheckedAndCallUrl(item, BaseConfig.DISCOVER_MOVIES_URL);
                return true;
            case R.id.toprated:
                POP_ITEM_INDEX = 1;
                setPopMenuCheckedAndCallUrl(item, BaseConfig.TOPRATED_BASE_URL);
                return true;
            case R.id.popular:
                POP_ITEM_INDEX = 2;
                setPopMenuCheckedAndCallUrl(item, BaseConfig.POPULAR_BASE_URL);
                return true;
            default:
                return false;
        }
    }

    private void setPopMenuCheckedAndCallUrl(MenuItem item, String url) {
        item.setChecked(true);
        PAGE_NUM = 1;
        loadNewList(url, PAGE_NUM);
    }

    private void loadNewList(String url, int pageNum) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_LOADING_KEY, url);
        bundle.putInt(MOVIE_PAGE_KEY, pageNum);
        getLoaderManager().restartLoader(MOVIE_LOADING_ID, bundle, this);
    }

    @Override
    public Loader<Movie> onCreateLoader(int id, final Bundle bundle) {

        return new AsyncTaskLoader<Movie>(this) {

            Movie movieChache = null;

            @Override
            public Movie loadInBackground() {
                String baseUrl = bundle.getString(MOVIE_LOADING_KEY); //strings[0];
                int pageNum = bundle.getInt(MOVIE_PAGE_KEY);

                URL searchUrl;
                String jsonResponse;

                Movie movie = null;

                searchUrl = NetworkUtils.buildUrl(baseUrl, pageNum);

                try {
                    jsonResponse = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                    movie = JsonUtils.processMovieOverviewJsonData(jsonResponse);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return movie;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(null != movieChache) {

                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(Movie data) {
                movieChache = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie movieObj) {
//        if(null != movie)
//            movie = null;

        if (null != movieObj) {
            movie = movieObj;
            adapter.newData(movieObj.getMovieDetails());
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

}
