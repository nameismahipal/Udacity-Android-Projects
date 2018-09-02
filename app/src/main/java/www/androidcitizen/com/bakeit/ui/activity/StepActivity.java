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
import www.androidcitizen.com.bakeit.util.Constants;

public class StepActivity extends AppCompatActivity {

    List<Step> steps;
    int stepSelectedIndex;
    ActivityStepBinding stepBinding;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_step);
        stepBinding = DataBindingUtil.setContentView(this, R.layout.activity_step);

        setSupportActionBar(stepBinding.toolbar);

        if(null == savedInstanceState) {
            Intent intent = getIntent();

            if(intent.hasExtra(Constants.STEPS_KEY)){
                stepSelectedIndex = intent.getIntExtra(Constants.STEP_SELECTED_INDEX_KEY, 0);
                steps = intent.getParcelableArrayListExtra(Constants.STEPS_KEY);
                setTitle(recipeName);
            }

            // Show the Up button in the action bar.
            final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(steps.get(stepSelectedIndex).getShortDescription());
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            StepsFragmentPagerAdapter stepsPagerAdapter = new StepsFragmentPagerAdapter(
                    getSupportFragmentManager(), steps);

            stepBinding.pager.setAdapter(stepsPagerAdapter);
            stepBinding.pager.setCurrentItem(stepSelectedIndex);
            stepBinding.tabs.setupWithViewPager(stepBinding.pager);


            stepBinding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int iPosition) {
                    if(actionBar != null) {
                        actionBar.setTitle(steps.get(iPosition).getShortDescription());
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}