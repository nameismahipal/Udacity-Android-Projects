package www.androidcitizen.com.bakeit.architecturecomponents;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.androidcitizen.com.bakeit.architecturecomponents.room.AppDatabase;
import www.androidcitizen.com.bakeit.architecturecomponents.room.RecipeEntity;
import www.androidcitizen.com.bakeit.architecturecomponents.room.RecipeEntityDao;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.data.remote.BakingInterface;
import www.androidcitizen.com.bakeit.ui.fragment.RecipeListFragment;

/**
 * Created by Mahi on 09/09/18.
 * www.androidcitizen.com
 */

public class RecipeRepository {

    private static final String TAG = RecipeRepository.class.getSimpleName();

    private RecipeEntityDao recipeEntityDao;

    public static interface RecipeRepoCallback {
        void onResponse(List<Recipe> recipes);
        void onFailure(Throwable t);
    }

    public RecipeRepository(Context context) {
        AppDatabase db = AppDatabase.getsInstance(context);
        recipeEntityDao = db.recipeEntityDao();
    }

    public void insertRecipes(List<Recipe> recipes) {

        List<RecipeEntity> recipeEntities = new ArrayList<>();

        for(Recipe recipe : recipes) {
            Gson gson = new Gson();
            String ingredientJson = gson.toJson(recipe.getIngredients());

            RecipeEntity recipeEntity = new RecipeEntity(
                    recipe.getId(),
                    recipe.getName(),
                    ingredientJson);

            recipeEntities.add(recipeEntity);
        }

        recipeEntityDao.insertRecipes(recipeEntities);
    }

    public RecipeEntity fetchRecipe(int recipeId) {

         return recipeEntityDao.getRecipe(recipeId);

    }

    public List<RecipeEntity> fetchRecipes() {

        return recipeEntityDao.getAllRecipes();

    }

    public List<Ingredient> fetchIngredientsFromRecipe(int recipeId) {

        RecipeEntity recipeEntity = recipeEntityDao.getRecipe(recipeId);

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Ingredient>>(){}.getType();

        List<Ingredient> ingredients = gson.fromJson(recipeEntity.getIngredientsJson(), listType);

        return ingredients;
    }

    public void fetchBakingDataFromServer(final RecipeRepoCallback repoCallback) {

        Call<List<Recipe>> recipesCall = BakingInterface.getBakingService().getRecipes();

        recipesCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    if (null != response.body()) {

                        // Insert to DB.
                        insertRecipes(response.body());

                        repoCallback.onResponse(response.body());
                    }
                } else {
                    Log.e(TAG, "response code = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                repoCallback.onFailure(t);
            }
        });
    }


}
