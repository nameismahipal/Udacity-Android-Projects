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
import www.androidcitizen.com.bakeit.data.custominterface.StepClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.ui.fragment.IngredientsListFragment;
import www.androidcitizen.com.bakeit.ui.fragment.SingleStepFragment;
import www.androidcitizen.com.bakeit.ui.fragment.StepsListFragment;
import www.androidcitizen.com.bakeit.util.Constants;

public class RecipeDetailsActivity extends AppCompatActivity implements StepClickListenerInterface{

    List<Step> steps;
    List<Ingredient> ingredients;
    boolean tabletMode;

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
                onStepSelected(0);
            }

        }
    }

    @Override
    public void onStepSelected(int iIndex) {

        if(getResources().getBoolean(R.bool.is_tablet)) {
            Log.e("RecipeDetailsActivity", "onStepSelected step size: " + steps.size());

            SingleStepFragment singleStepFragment = SingleStepFragment.newInstance(steps.get(iIndex));

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
}
