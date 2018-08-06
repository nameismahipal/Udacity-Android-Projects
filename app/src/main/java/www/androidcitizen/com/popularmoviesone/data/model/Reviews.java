package www.androidcitizen.com.popularmoviesone.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mahi on 04/08/18.
 * www.androidcitizen.com
 *
 * Used http://www.jsonschema2pojo.org/ tool for Code creation.
 *
 * Parcelable code generated using http://www.parcelabler.com
 *
 */

public class Reviews {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("page")
        @Expose
        private int page;
        @SerializedName("results")
        @Expose
        private List<ReviewResultsItem> reviewItems = null;
        @SerializedName("total_pages")
        @Expose
        private int totalPages;
        @SerializedName("total_results")
        @Expose
        private int totalResults;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<ReviewResultsItem> getReviewItems() {
            return reviewItems;
        }

        public void setReviewItems(List<ReviewResultsItem> reviewItems) {
            this.reviewItems = reviewItems;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }
}
