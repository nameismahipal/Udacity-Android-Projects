package www.androidcitizen.com.popularmoviesone.data.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import www.androidcitizen.com.popularmoviesone.R;
import www.androidcitizen.com.popularmoviesone.data.model.VideoResultsItem;
import www.androidcitizen.com.popularmoviesone.databinding.VideosListItemBinding;

import static www.androidcitizen.com.popularmoviesone.config.Constants.*;

/**
 * Created by Mahi on 04/08/18.
 * www.androidcitizen.com
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosItemViewHolder> {

    private List<VideoResultsItem> videoResultsItems = null;

    //private VideosListItemBinding videosListItemBinding;
    private VideoClickListener videoClickListener;

    public interface VideoClickListener {
        void onVideoItemClick(int iClickedIndex, String videoKey);
    }

    public VideosAdapter(VideoClickListener videoClickListener) {
        this.videoClickListener = videoClickListener;
    }

    @NonNull
    @Override
    public VideosItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        VideosListItemBinding videosListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.videos_list_item, parent, false);


        return new VideosItemViewHolder(videosListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosItemViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if(null == videoResultsItems) {
            return 0;
        } else {
            return videoResultsItems.size();
        }
    }

    class VideosItemViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

        private final VideosListItemBinding videoItemBinding;

        public VideosItemViewHolder(VideosListItemBinding videoItemView) {
            super(videoItemView.getRoot());
            this.videoItemBinding = videoItemView;
            videoItemBinding.setTrailerImage.setOnClickListener(this);
        }

        void onBind(int iIndex) {

            String youPath = YOUTUBE_THUMBNAIL_BASE_URL + videoResultsItems.get(iIndex).getKey() + YOUTUBE_THUMBNAIL_END_URL;

//            GlideApp.with(context)
//                    .load(youPath)
//                    .centerCrop()
//                    .into(videoItemBinding.setTrailerImage);

            Picasso.get()
                    .load(youPath)
                    //.placeholder("http://via.placeholder.com/350x150")
                    //.error(R.drawable.user_placeholder_error)
                    .into(videoItemBinding.setTrailerImage);
        }

        @Override
        public void onClick(View v) {
            int iClickIndex = getAdapterPosition();
            videoClickListener.onVideoItemClick(iClickIndex, videoResultsItems.get(iClickIndex).getKey());

        }
    }


    public void newData(List<VideoResultsItem> reviewItems) {
        if(null == reviewItems) {
            videoResultsItems = null;
        } else {
            videoResultsItems = reviewItems;
            notifyDataSetChanged();
        }
    }


    public void clearAll() {
        while (videoResultsItems.size() > 0) {
            remove(get(0));
        }
    }

    private VideoResultsItem get(int index) {

        return videoResultsItems.get(index);
    }

    public void remove(VideoResultsItem reviewItem) {
        int index = videoResultsItems.indexOf(reviewItem);
        videoResultsItems.remove(index);
        notifyItemRemoved(index);
    }

    public List<VideoResultsItem> getVideoResults() {
        return videoResultsItems;
    }
}
