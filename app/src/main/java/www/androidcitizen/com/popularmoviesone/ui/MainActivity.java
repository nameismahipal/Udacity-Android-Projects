package www.androidcitizen.com.popularmoviesone.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.adapter.MovieAdapter;
import www.androidcitizen.com.popularmoviesone.config.BaseConfig;
import www.androidcitizen.com.popularmoviesone.model.Movie;
import www.androidcitizen.com.popularmoviesone.utilities.JsonUtils;
import www.androidcitizen.com.popularmoviesone.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMovieGridList;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rvMovieGridList = (RecyclerView) findViewById(R.id.rv_movieList);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        rvMovieGridList.setHasFixedSize(true);
        rvMovieGridList.setLayoutManager(layoutManager);

        adapter = new MovieAdapter();

        rvMovieGridList.setAdapter(adapter);

        new MovieAsyncTask().execute(BaseConfig.DISCOVER_MOVIES_URL);

    }

    public class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String baseUrl = strings[0];
            URL searchUrl;
            String jsonResponse;

            List<Movie> movies = null;

            searchUrl = NetworkUtils.buildUrl(baseUrl);

            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                movies = JsonUtils.processJsonData(jsonResponse);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            adapter.newData(movies);
        }
    }

}
