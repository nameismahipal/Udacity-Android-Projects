package www.androidcitizen.com.bakeit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.PrevNextInterface;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.ui.fragment.SingleStepFragment;
import www.androidcitizen.com.bakeit.util.Constants;

public class StepActivity extends AppCompatActivity implements PrevNextInterface{

    private List<Step> steps;
    private static int stepSelectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);

        if(null == savedInstanceState) {
            Intent intent = getIntent();

            if(intent.hasExtra(Constants.STEPS_KEY)){
                stepSelectedIndex = intent.getIntExtra(Constants.STEP_SELECTED_INDEX_KEY, 0);
                steps = intent.getParcelableArrayListExtra(Constants.STEPS_KEY);
                setTitle(steps.get(stepSelectedIndex).getShortDescription());
            }

            if(null != steps) {
                selectNextPrevStep(stepSelectedIndex);
            }

//            SingleStepFragment singleStepFragment = SingleStepFragment.newInstance(steps.get(stepSelectedIndex));
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.singleStepContainer, singleStepFragment)
//                    .commit();
        } else {
            if(savedInstanceState.containsKey(Constants.STEPS_KEY)) {
                steps = savedInstanceState.getParcelableArrayList(Constants.STEPS_KEY);
                stepSelectedIndex = savedInstanceState.getInt(Constants.STEP_SELECTED_INDEX_KEY);
                setTitle(steps.get(stepSelectedIndex).getShortDescription());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void prevButtonClicked() {
        stepSelectedIndex--;
        if(stepSelectedIndex >=0 ) {
            selectNextPrevStep(stepSelectedIndex);
        }
    }

    @Override
    public void nextButtonClicked() {
        stepSelectedIndex++;
        if(stepSelectedIndex <= steps.size()-1) {
            selectNextPrevStep(stepSelectedIndex);
        }
    }

    private void selectNextPrevStep(int iIndex) {

        setTitle(steps.get(iIndex).getShortDescription());

        SingleStepFragment singleStepFragment = new SingleStepFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.SINGLE_STEP_KEY, steps.get(iIndex));
        args.putString(Constants.STEP_NUMBER_STATE_KEY, getResources().getString(R.string.step_nav_state, iIndex, steps.size()-1));
        args.putBoolean(Constants.STEP_PREV_BTN_STATE_KEY, iIndex < 1 );
        args.putBoolean(Constants.STEP_NEXT_BTN_STATE_KEY, iIndex == steps.size()-1);

        singleStepFragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.singleStepContainer, singleStepFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(Constants.STEPS_KEY, (ArrayList<Step>) steps);
        outState.putInt(Constants.STEP_SELECTED_INDEX_KEY, stepSelectedIndex);
    }


}