package www.androidcitizen.com.popularmoviesone.model;

import www.androidcitizen.com.popularmoviesone.config.BaseConfig;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class MovieDetails {
    private String  title;
    private int     movieId;
    private String  posterPath;
    private float   voterAvg;

    private String  backdropPath;
    private String  overView;
    private String  releaseDate;

    public MovieDetails(String title, int movieId, String posterPath, float voterAvg,
                 String backdropPath, String overView, String releaseDate) {
        this.title          = title;
        this.movieId        = movieId;
        this.posterPath     = posterPath;
        this.voterAvg       = voterAvg;
        this.backdropPath   = backdropPath;
        this.overView       = overView;
        this.releaseDate    = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getPosterPath() {
        return BaseConfig.PORT_POSTER_IMAGE_URL_PATH + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getVoterAvg() {
        return voterAvg;
    }

    public void setVoterAvg(float voterAvg) {
        this.voterAvg = voterAvg;
    }

    public String getBackdropPath() {
        return BaseConfig.PORT_BACKDROP_IMAGE_URL_PATH + backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
