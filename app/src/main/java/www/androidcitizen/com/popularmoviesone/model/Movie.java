package www.androidcitizen.com.popularmoviesone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mahi on 13/06/18.
 * www.androidcitizen.com

 Used http://www.jsonschema2pojo.org/ tool for Code creation.
*/

public class Movie {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("results")
    @Expose
    private List<MovieDetails> movieDetails = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieDetails> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(List<MovieDetails> results) {
        this.movieDetails = results;
    }
}
