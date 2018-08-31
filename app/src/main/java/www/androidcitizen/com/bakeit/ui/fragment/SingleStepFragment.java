package www.androidcitizen.com.bakeit.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.databinding.FragmentSingleStepBinding;
import www.androidcitizen.com.bakeit.util.Constants;

public class SingleStepFragment extends Fragment {

    FragmentSingleStepBinding singleStepBinding;

    private Step step;

    public SingleStepFragment() {
        // Required empty public constructor
    }

    public static SingleStepFragment newInstance(Step step) {
        SingleStepFragment fragment = new SingleStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.SINGLE_STEP_KEY, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(Constants.SINGLE_STEP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null == singleStepBinding) {
            singleStepBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_step,
                    container, false);
        }

        return singleStepBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        singleStepBinding.stepDescription.setText(step.getDescription());
    }
}
