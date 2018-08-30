package www.androidcitizen.com.bakeit.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.ui.fragment.IngredientsListFragment;
import www.androidcitizen.com.bakeit.util.Constants;

public class RecipeDetailsActivity extends AppCompatActivity {

    List<Ingredient> ingredients;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(null == savedInstanceState) {
            Intent intent = getIntent();
            if(intent.hasExtra(Constants.RECIPE_KEY)) {
                recipe = intent.getParcelableExtra(Constants.RECIPE_KEY);
                ingredients = recipe.getIngredients();
            }

            if (null != ingredients) {
                IngredientsListFragment ingredientsFragment = IngredientsListFragment.newInstance(ingredients);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipeListContainer, ingredientsFragment)
                        .commit();
            }

        }
    }
}
