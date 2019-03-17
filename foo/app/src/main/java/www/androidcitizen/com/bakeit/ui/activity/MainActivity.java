package www.androidcitizen.com.bakeit.ui.activity;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.annotation.Nullable;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.RecipeClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.idlingresources.IdlingResourceManager;
import www.androidcitizen.com.bakeit.util.Constants;

public class MainActivity extends AppCompatActivity implements
        RecipeClickListenerInterface,
        IdlingResourceManager {

    @Nullable
    private CountingIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constants.RECIPE_KEY, recipe);
        startActivity(intent);
    }

    @VisibleForTesting
    @Override
    public CountingIdlingResource getIdlingResource() {
        if(null == idlingResource) {
            idlingResource = new CountingIdlingResource(Constants.TEST_IDLING_RESOURCE);
        }
        return idlingResource;
    }

    @Override
    public void incrementIdlingResource() {
        getIdlingResource().increment();
    }

    @Override
    public void decrementIdlingResource() {
        getIdlingResource().decrement();
    }
}