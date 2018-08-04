package www.androidcitizen.com.popularmoviesone.data.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.data.model.ReviewResultsItem;
import www.androidcitizen.com.popularmoviesone.databinding.ReviewItemBinding;

/**
 * Created by Mahi on 04/08/18.
 * www.androidcitizen.com
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewItemViewHolder> {

    private List<ReviewResultsItem> reviewResultsItems = null;
    Context context;
    public ReviewAdapter(Context context) {
        //this.reviewResultsItems = reviewItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewItemBinding reviewItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.review_item, parent, false);

        return new ReviewItemViewHolder(reviewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if(null == reviewResultsItems) {
            return 0;
        } else {
            return reviewResultsItems.size();
        }
    }

    class ReviewItemViewHolder extends RecyclerView.ViewHolder {

        private final ReviewItemBinding reviewItemBinding;

        public ReviewItemViewHolder(ReviewItemBinding reviewItemView) {
            super(reviewItemView.getRoot());
            this.reviewItemBinding = reviewItemView;
        }

        void onBind(int iIndex) {
            reviewItemBinding.authorName.setText(reviewResultsItems.get(iIndex).getAuthor());
            reviewItemBinding.authorReview.setText(reviewResultsItems.get(iIndex).getContent());
        }
    }


    public void newData(List<ReviewResultsItem> reviewItems) {
        if(null == reviewItems) {
            reviewResultsItems = null;
        } else {
            reviewResultsItems = reviewItems;
            notifyDataSetChanged();
//            for (ReviewResultsItem reviewItem : reviewItems ) {
//                add(reviewItem);
//            }
        }
    }

    private void add(ReviewResultsItem reviewItem) {
        if(null != reviewItem) {
            reviewResultsItems.add(reviewItem);
            notifyItemInserted(reviewResultsItems.size() - 1);
        }
    }

    public void clearAll() {
        while (reviewResultsItems.size() > 0) {
            remove(get(0));
        }
    }

    private ReviewResultsItem get(int index) {
        return reviewResultsItems.get(index);
    }

    public void remove(ReviewResultsItem reviewItem) {
        int index = reviewResultsItems.indexOf(reviewItem);
        reviewResultsItems.remove(index);
        notifyItemRemoved(index);
    }
}
