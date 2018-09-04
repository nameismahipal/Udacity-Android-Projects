package www.androidcitizen.com.bakeit.ui.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.PrevNextInterface;
import www.androidcitizen.com.bakeit.data.custominterface.StepClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.ui.fragment.IngredientsListFragment;
import www.androidcitizen.com.bakeit.ui.fragment.SingleStepFragment;
import www.androidcitizen.com.bakeit.ui.fragment.StepsListFragment;
import www.androidcitizen.com.bakeit.util.Constants;

public class RecipeDetailsActivity extends AppCompatActivity
        implements StepClickListenerInterface, PrevNextInterface {

    List<Step> steps;
    List<Ingredient> ingredients;
    boolean tabletMode;
    static int iStepIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(null == savedInstanceState) {
            Intent intent = getIntent();
            if(intent.hasExtra(Constants.RECIPE_KEY)) {
                Recipe recipe = intent.getParcelableExtra(Constants.RECIPE_KEY);
                ingredients = recipe.getIngredients();
                steps = recipe.getSteps();

                setTitle(recipe.getName());
            }

            if (null != ingredients) {
                IngredientsListFragment ingredientsFragment = IngredientsListFragment.newInstance(ingredients);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipeListContainer, ingredientsFragment)
                        .commit();
            }

            if (null != steps) {
                StepsListFragment stepsListFragment = StepsListFragment.newInstance(steps);

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.stepsListContainer, stepsListFragment)
                        .commit();
            }

            tabletMode = getResources().getBoolean(R.bool.is_tablet);

            if(tabletMode) {
                iStepIndex = 0;
                onStepSelected(iStepIndex);
            }

        }
    }

    @Override
    public void onStepSelected(int iIndex) {

        if(getResources().getBoolean(R.bool.is_tablet)) {

            iStepIndex = iIndex;

            //SingleStepFragment singleStepFragment = SingleStepFragment.newInstance(steps.get(iIndex));
            SingleStepFragment singleStepFragment = new SingleStepFragment();

            Bundle args = new Bundle();
            args.putParcelable(Constants.SINGLE_STEP_KEY, steps.get(iIndex));
            args.putString(Constants.STEP_NUMBER_STATE_KEY, getResources().getString(R.string.step_nav_state, iIndex, steps.size()));

            args.putBoolean(Constants.STEP_PREV_BTN_STATE_KEY, iIndex < 1 );
            args.putBoolean(Constants.STEP_NEXT_BTN_STATE_KEY, iIndex == steps.size());

            singleStepFragment.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.singleStepContainer, singleStepFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(Constants.STEP_SELECTED_INDEX_KEY, iIndex);
            intent.putParcelableArrayListExtra(Constants.STEPS_KEY, (ArrayList<Step>) steps);
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(Constants.STEPS_KEY, (ArrayList<Step>) steps);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        steps = savedInstanceState.getParcelableArrayList(Constants.STEPS_KEY);
    }

    @Override
    public void prevButtonClicked() {
        //View Hidden in Tablet Mode
    }

    @Override
    public void nextButtonClicked() {
        //View Hidden in Tablet Mode
    }

}
