package www.androidcitizen.com.bakeit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.RecipeClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.ui.fragment.SingleStepFragment;
import www.androidcitizen.com.bakeit.util.Constants;

public class StepActivity extends AppCompatActivity {

    Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);


        if(null == savedInstanceState) {
            Intent intent = getIntent();

            if(intent.hasExtra(Constants.SINGLE_STEP_KEY)){
                step = intent.getParcelableExtra(Constants.SINGLE_STEP_KEY);
                setTitle(this.getString(R.string.step_number,step.getId()));
            }

            SingleStepFragment singleStepFragment = SingleStepFragment.newInstance(step);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.singleStepFragment, singleStepFragment)
                    .commit();
        }
    }

}