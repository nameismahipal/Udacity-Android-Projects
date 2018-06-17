package www.androidcitizen.com.popularmoviesone.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.adapter.MovieAdapter;
import www.androidcitizen.com.popularmoviesone.config.BaseConfig;
import www.androidcitizen.com.popularmoviesone.model.Movie;
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.utilities.JsonUtils;
import www.androidcitizen.com.popularmoviesone.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieClickListener {

    private RecyclerView rvMovieGridList;
    private static MovieAdapter adapter;

//    private static List<MovieDetails> movieDetails = null;
    private static Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BaseConfig.setMovieOverviewActivity();

        rvMovieGridList = (RecyclerView) findViewById(R.id.rv_movieList);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvMovieGridList.setHasFixedSize(true);
        rvMovieGridList.setLayoutManager(layoutManager);

        adapter = new MovieAdapter(this);

        rvMovieGridList.setAdapter(adapter);

        new MovieAsyncTask().execute(BaseConfig.DISCOVER_MOVIES_URL);

    }

    private static class MovieAsyncTask extends AsyncTask<String, Void, Movie> {

        @Override
        protected Movie doInBackground(String... strings) {
            String baseUrl = strings[0];
            URL searchUrl;
            String jsonResponse;

            // List<Movie> movies = null;

            Movie movie = null;

            searchUrl = NetworkUtils.buildUrl(baseUrl);

            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                movie = JsonUtils.processMovieOverviewJsonData(jsonResponse);

                } catch (IOException e) {
                e.printStackTrace();
            }

            return movie;
        }

        @Override
        protected void onPostExecute(Movie movieObj) {
            if(null != movie)
                movie = null;

            if (null != movieObj) {
                movie = movieObj;
                adapter.newData(movieObj.getMovieDetails());
            }
        }
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {

        if(null != movie.getMovieDetails()) {

            MovieDetails sendData = movie.getMovieDetails().get(clickedItemIndex);

            Intent movieDetailActivity = new Intent(this, MovieDetailActivity.class);
            movieDetailActivity.putExtra("movieDetailsdata", sendData);
            startActivity(movieDetailActivity);

//            movieDetailActivity.putExtra(, )movie.getMovieDetails().get(clickedItemIndex)
////            movieDetailActivity.putExtra(BaseConfig.MOVIE_ID, movie.get(clickedItemIndex).getMovieId());
//            startActivity(movieDetailActivity);
        }

    }

}
