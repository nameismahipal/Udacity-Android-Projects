package www.androidcitizen.com.popularmoviesone.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mahi on 06/08/18.
 * www.androidcitizen.com
 *
 * Used http://www.jsonschema2pojo.org/ tool for Code creation.
 *
 * Parcelable code generated using http://www.parcelabler.com
 *
 */

public class Videos {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<VideoResultsItem> videoItems = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoResultsItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(List<VideoResultsItem> results) {
        this.videoItems = results;
    }

}