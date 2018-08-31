package www.androidcitizen.com.bakeit.data.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.StepClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.databinding.StepsItemViewBinding;

/**
 * Created by Mahi on 27/08/18.
 * www.androidcitizen.com
 */

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsViewHolder>{

    private StepsItemViewBinding itemViewBinding;

    List<Step> steps = null;

    Context context;
    StepClickListenerInterface stepClickListenerInterface;

    public StepsListAdapter(Context context, StepClickListenerInterface stepClickListenerInterface) {
        this.context = context;
        this.stepClickListenerInterface = stepClickListenerInterface;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.steps_item_view, viewGroup, false);

        return new StepsViewHolder(itemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder recipeViewHolder, int iPosition) {

        recipeViewHolder.onBind(iPosition);
    }

    @Override
    public int getItemCount() {
        if (null != steps)
            return steps.size();
        else
            return 0;
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private StepsItemViewBinding itemViewBinding;

        public StepsViewHolder(@NonNull StepsItemViewBinding recipeItemBind) {
            super(recipeItemBind.getRoot());
            this.itemViewBinding = recipeItemBind;
            this.itemViewBinding.stepView.setOnClickListener(this);
        }

        void onBind(int iPosition) {
            itemViewBinding.stepText.setText(context.getString(R.string.step,
                    steps.get(iPosition).getId(), steps.get(iPosition).getShortDescription()));

            if(0 == iPosition % 2) {
                itemViewBinding.stepView.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
            }

            if(isVideoPresent(iPosition)){
                itemViewBinding.stepImage.setImageResource(R.drawable.ic_video);
            } else {
                itemViewBinding.stepImage.setImageResource(R.drawable.ic_notes);
            }
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            stepClickListenerInterface.onStepSelected(steps.get(index));
        }
    }

    public void updateSteps(List<Step> newSteps){
        steps = newSteps;
        notifyDataSetChanged();
    }

    private boolean isVideoPresent(int iIndex){
        return steps.get(iIndex).getVideoURL().isEmpty();
    }

}
