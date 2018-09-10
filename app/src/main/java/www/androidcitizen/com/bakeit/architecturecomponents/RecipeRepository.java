package www.androidcitizen.com.bakeit.architecturecomponents;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.bakeit.architecturecomponents.room.AppDatabase;
import www.androidcitizen.com.bakeit.architecturecomponents.room.RecipeEntity;
import www.androidcitizen.com.bakeit.architecturecomponents.room.RecipeEntityDao;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;

/**
 * Created by Mahi on 09/09/18.
 * www.androidcitizen.com
 */

public class RecipeRepository {

    RecipeEntityDao recipeEntityDao;

    public RecipeRepository(Context context) {
        AppDatabase db = AppDatabase.getsInstance(context);
        recipeEntityDao = db.recipeEntityDao();
    }

    public List<Ingredient> fetchIngredientsFromRecipe(int recipeId) {

        RecipeEntity recipeEntity = recipeEntityDao.getRecipe(recipeId);

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Ingredient>>(){}.getType();

        List<Ingredient> ingredients = gson.fromJson(recipeEntity.getIngredientsJson(), listType);

        return ingredients;
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

}
