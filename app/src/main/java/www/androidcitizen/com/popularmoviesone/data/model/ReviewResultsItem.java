package www.androidcitizen.com.popularmoviesone.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mahi on 04/08/18.
 * www.androidcitizen.com
 *
 * Used http://www.jsonschema2pojo.org/ tool for Code creation.
 *
 * Parcelable code generated using http://www.parcelabler.com
 */

public class ReviewResultsItem implements Parcelable {
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    protected ReviewResultsItem(Parcel in) {
        author = in.readString();
        content = in.readString();
        id = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(id);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ReviewResultsItem> CREATOR = new Parcelable.Creator<ReviewResultsItem>() {
        @Override
        public ReviewResultsItem createFromParcel(Parcel in) {
            return new ReviewResultsItem(in);
        }

        @Override
        public ReviewResultsItem[] newArray(int size) {
            return new ReviewResultsItem[size];
        }
    };
}