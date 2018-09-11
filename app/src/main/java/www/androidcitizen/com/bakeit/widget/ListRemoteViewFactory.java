package www.androidcitizen.com.bakeit.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.architecturecomponents.RecipeRepository;
import www.androidcitizen.com.bakeit.architecturecomponents.room.RecipeEntity;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.util.Constants;

import static www.androidcitizen.com.bakeit.util.Constants.WIDGET_RECIPE_ID_INDEX;
import static www.androidcitizen.com.bakeit.util.Constants.WIDGET_RECIPE_NAME;

/**
 * Created by Mahi on 10/09/18.
 * www.androidcitizen.com
 */

public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredients;
    private RecipeRepository recipeRepository;
    private static int recipeId;

    public ListRemoteViewFactory(Context context) {
        this.context = context;
        this.recipeRepository = new RecipeRepository(context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

            int iRecipeCount = readDataBaseUpdateSharedPrefAndUpdateViews();

            if (0 == iRecipeCount) return;

            ingredients = recipeRepository.fetchIngredientsFromRecipe(recipeId);
    }

    private int readDataBaseUpdateSharedPrefAndUpdateViews() {

        // DB Fetch
        List<RecipeEntity> recipeEntities = recipeRepository.fetchRecipes();

        int iRecipeCount = recipeEntities.size();

        if(0 == iRecipeCount) return 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.WIDGET_SHARED_PREF_FILE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        int idIndex = sharedPreferences.getInt(Constants.WIDGET_RECIPE_ID_INDEX, 0);

        String recipeName = recipeEntities.get(idIndex).getName();

        editor.putInt(WIDGET_RECIPE_ID_INDEX, idIndex);
        editor.putString(WIDGET_RECIPE_NAME, recipeName);

        editor.apply();

        recipeId = recipeEntities.get(idIndex).getId();

        return iRecipeCount;
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(null == ingredients)
            return 0;
        else
            return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_list_item_view);

        String quantity = String.valueOf(ingredients.get(position).getQuantity());
        String measure  = ingredients.get(position).getMeasure();
        String ingredient = ingredients.get(position).getIngredient();

        view.setTextViewText(R.id.quantity, quantity);
        view.setTextViewText(R.id.measure, measure);
        view.setTextViewText(R.id.ingredient, ingredient);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
