package www.androidcitizen.com.popularmoviesone.ui;

import android.content.Context;
import android.content.Intent;
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

import com.facebook.stetho.Stetho;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.adapter.MovieAdapter;
import www.androidcitizen.com.popularmoviesone.api.MovieInterface;
import www.androidcitizen.com.popularmoviesone.api.MovieService;
import www.androidcitizen.com.popularmoviesone.config.BaseConfig;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMainBinding;
import www.androidcitizen.com.popularmoviesone.model.Movie;
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.pagination.EndlessScrollListener;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieClickListener,
        PopupMenu.OnMenuItemClickListener {

    ActivityMainBinding mainBinding;

    private static MovieAdapter adapter;
    private EndlessScrollListener scrollListener;

    private static int PAGE_NUM = 1;
    private static int TOTAL_PAGES = 1;

    private final static int ALL_MOVIES_INDEX       = 0;
    private final static int TOPRATED_MOVIES_INDEX  = 1;
    private final static int POPULAR_MOVIES_INDEX   = 2;

    private static int MOVIE_FETCH_INDEX = ALL_MOVIES_INDEX;

    private final static String API_KEY = BaseConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Stetho.initializeWithDefaults(this);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        fetchMovies(MOVIE_FETCH_INDEX);

        setupRecycleView();
    }

    private void setupRecycleView(){
        int spanCount = getColumnValue(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);

        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                PAGE_NUM = page;
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

    private void fetchMovies(int movieIndex){

        switch (movieIndex) {

            case ALL_MOVIES_INDEX:      fetchAllMovies(); break;
            case TOPRATED_MOVIES_INDEX: fetchTopRatedMovies(); break;
            case POPULAR_MOVIES_INDEX:  fetchPopularMovies(); break;

        }
    }

    private MovieService initializeMovieService(){
       // MovieService movieService = MovieInterface.getMovieService();
        MovieService movieService = MovieInterface.getMovieInterface().create(MovieService.class);
        return movieService;
    }

    private void fetchAllMovies() {

        Call<Movie> moviesServiceCall = initializeMovieService().getAllMovies(API_KEY, PAGE_NUM);

        serverRequest(moviesServiceCall);
    }

    private void fetchTopRatedMovies() {

        Call<Movie> moviesServiceCall = initializeMovieService().getTopRatedMovies(API_KEY, PAGE_NUM);

        serverRequest(moviesServiceCall);
    }

    private void fetchPopularMovies() {
        Call<Movie> moviesServiceCall = initializeMovieService().getPopularMovies(API_KEY, PAGE_NUM);
        serverRequest(moviesServiceCall);
    }

    private void serverRequest(Call<Movie> moviesSerivceCall){
        moviesSerivceCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                setupAdapterData(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    private void setupAdapterData(Movie movieObj){
        if (null != movieObj) {
            Movie movie = movieObj;
            PAGE_NUM = movie.getPage();
            TOTAL_PAGES = movie.getTotalPages();
            adapter.newData(movieObj.getMovieDetails());
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
                MOVIE_FETCH_INDEX = ALL_MOVIES_INDEX;
                break;
            case R.id.toprated:
                MOVIE_FETCH_INDEX = TOPRATED_MOVIES_INDEX;
                break;
            case R.id.popular:
                MOVIE_FETCH_INDEX = POPULAR_MOVIES_INDEX;
                break;
        }

        clearList();
        PAGE_NUM = 1;
        fetchMovies(MOVIE_FETCH_INDEX);

        return true;
    }

    private void clearList() {
        adapter.clearAll();
        scrollListener.resetState();
    }

}
