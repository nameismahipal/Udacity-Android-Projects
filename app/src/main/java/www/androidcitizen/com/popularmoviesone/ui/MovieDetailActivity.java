package www.androidcitizen.com.popularmoviesone.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.config.BaseConfig;
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;
import www.androidcitizen.com.popularmoviesone.utilities.JsonUtils;
import www.androidcitizen.com.popularmoviesone.utilities.NetworkUtils;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView ivPoster;
    private ImageView ivBackground;
    private TextView tvTitle;
    private RatingBar rvRatingbar;
    private TextView tvReleaseDate;
    private TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        BaseConfig.setMovieDetailActivity();

        ivPoster        = findViewById(R.id.iv_posterImage);
        ivBackground    = findViewById(R.id.iv_backgroundImage);
        tvTitle         = findViewById(R.id.tv_title);
        rvRatingbar     = findViewById(R.id.ratingBar);
        tvReleaseDate   = findViewById(R.id.tv_releaseDate);
        tvOverview      = findViewById(R.id.tv_overView);

        Intent readIntent = getIntent();
        int movieId = readIntent.getIntExtra(BaseConfig.MOVIE_ID, -1);

        new MovieDetailsAsyncTask().execute(BaseConfig.MOVIES_BASE_URL+"/"+movieId);

    }

    private class MovieDetailsAsyncTask extends AsyncTask<String, Void, MovieDetails> {

        @Override
        protected MovieDetails doInBackground(String... strings) {
            String baseUrl = strings[0];
            URL searchUrl;
            String jsonResponse;

            MovieDetails movieDetails = null;

            searchUrl = NetworkUtils.buildUrl(baseUrl);

            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                movieDetails = JsonUtils.processMovieDetailsJsonData(jsonResponse);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movieDetails;
        }

        @Override
        protected void onPostExecute(MovieDetails movieDetails) {

            Picasso.get()
                    .load(movieDetails.getBackdropPath())
                    //.placeholder("http://via.placeholder.com/350x150")
                    //.error(R.drawable.user_placeholder_error)
                    .into(ivBackground);

            Picasso.get()
                    .load(movieDetails.getPosterPath())
                    //.placeholder("http://via.placeholder.com/350x150")
                    //.error(R.drawable.user_placeholder_error)
                    .into(ivPoster);

            tvTitle.setText(movieDetails.getTitle());
            rvRatingbar.setRating(movieDetails.getVoterAvg()/2);
            tvReleaseDate.setText(movieDetails.getReleaseDate());
            tvOverview.setText(movieDetails.getOverView());
        }
    }

}
