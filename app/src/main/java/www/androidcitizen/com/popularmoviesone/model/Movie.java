package www.androidcitizen.com.popularmoviesone.model;

import www.androidcitizen.com.popularmoviesone.config.BaseConfig;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com
 */

public class Movie {

    private String  title;
    private int     movieId;
    private String  posterPath;
    private float   voterAvg;

    public Movie(String title, int movieId, String posterPath, float voterAvg) {
        this.title = title;
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.voterAvg = voterAvg;
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
        return BaseConfig.POSTER_IMAGE_URL_PATH + posterPath;
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
}
