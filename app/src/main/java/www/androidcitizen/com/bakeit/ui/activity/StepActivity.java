package www.androidcitizen.com.bakeit.ui.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.adapter.StepsFragmentPagerAdapter;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.databinding.ActivityStepBinding;
import www.androidcitizen.com.bakeit.ui.fragment.SingleStepFragment;
import www.androidcitizen.com.bakeit.util.Constants;

public class StepActivity extends AppCompatActivity {

    List<Step> steps;
    int stepSelectedIndex;
    ActivityStepBinding stepBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stepBinding = DataBindingUtil.setContentView(this, R.layout.activity_step);


        if(null == savedInstanceState) {
            Intent intent = getIntent();

            if(intent.hasExtra(Constants.STEPS_KEY)){
                stepSelectedIndex = intent.getIntExtra(Constants.STEP_SELECTED_INDEX_KEY, 0);
                steps = intent.getParcelableArrayListExtra(Constants.STEPS_KEY);
                setTitle(steps.get(stepSelectedIndex).getShortDescription());
            }

            SingleStepFragment singleStepFragment = SingleStepFragment.newInstance(steps.get(stepSelectedIndex));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.singleStepContainer, singleStepFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}