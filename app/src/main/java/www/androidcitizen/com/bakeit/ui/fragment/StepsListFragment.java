package www.androidcitizen.com.bakeit.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.adapter.StepsListAdapter;
import www.androidcitizen.com.bakeit.data.custominterface.StepClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Step;

import static www.androidcitizen.com.bakeit.util.Constants.STEPS_KEY;


public class StepsListFragment extends Fragment {

    private StepsListAdapter stepsAdapter;
    private List<Step> steps = null;
    private Context context;
    private StepClickListenerInterface stepClickListenerInterface;

    RecyclerView recipeDetailsList;

    public StepsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            this.context = context;
        }

        try {
            stepClickListenerInterface = (StepClickListenerInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
            " must implement onStepSelected Interface");
        }

    }

    public static StepsListFragment newInstance(List<Step> steps) {

        StepsListFragment fragment = new StepsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_KEY, (ArrayList<Step>) steps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.steps = getArguments().getParcelableArrayList(STEPS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_list, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViews(view);

        setupRecyclerView();

        loadBakingData();
    }

    private void setupViews(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        recipeDetailsList = (RecyclerView) view.findViewById(R.id.recipeDetailsList);

        tvTitle.setText(R.string.stepsLabel);
        tvTitle.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
    }

    private void setupRecyclerView() {
        stepsAdapter = new StepsListAdapter(context, stepClickListenerInterface);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        recipeDetailsList.setLayoutManager(layoutManager);
        recipeDetailsList.setHasFixedSize(true);
        recipeDetailsList.setItemAnimator(new DefaultItemAnimator());
        recipeDetailsList.setAdapter(stepsAdapter);
    }

    private void loadBakingData() {
        stepsAdapter.updateSteps(steps);
    }

}