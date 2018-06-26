package www.androidcitizen.com.popularmoviesone.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.databinding.ActivityMovieDetailBinding;
import www.androidcitizen.com.popularmoviesone.model.MovieDetails;

public class MovieDetailActivity extends AppCompatActivity {

    ActivityMovieDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        MovieDetails movieDetails = getIntent().getParcelableExtra("movieDetailsdata");

        Picasso.get()
                .load(movieDetails.getBackdropPath())
                //.placeholder("http://via.placeholder.com/350x150")
                //.error(R.drawable.user_placeholder_error)
                .into(detailBinding.ivBackgroundImage);

        Picasso.get()
                .load(movieDetails.getPosterPath())
                //.placeholder("http://via.placeholder.com/350x150")
                //.error(R.drawable.user_placeholder_error)
                .into(detailBinding.ivPosterImage);

            detailBinding.tvTitle.setText(movieDetails.getTitle());
            detailBinding.ratingBar.setRating(movieDetails.getVoteAverage()/2);
            detailBinding.tvReleaseDate.setText(movieDetails.getReleaseDate());
            detailBinding.tvOverView.setText(movieDetails.getOverview());
    }

}
