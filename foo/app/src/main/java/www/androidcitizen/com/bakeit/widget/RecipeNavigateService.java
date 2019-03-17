package www.androidcitizen.com.bakeit.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.architecturecomponents.RecipeRepository;
import www.androidcitizen.com.bakeit.architecturecomponents.room.RecipeEntity;
import www.androidcitizen.com.bakeit.util.Constants;

/**
 * Created by Mahi on 11/09/18.
 * www.androidcitizen.com
 */

public class RecipeNavigateService extends IntentService {

    public static final String SERVICE_RECIPE_NEXT_BUTTON = "widget_recipe_next_button";
    public static final String SERVICE_RECIPE_UPDATE_BUTTON = "widget_recipe_update_button";

    public RecipeNavigateService() {
        super("RecipeNavigateService");
    }

    public static void onNextRecipe(Context context) {
        Intent intent = new Intent(context, RecipeNavigateService.class);
        intent.setAction(SERVICE_RECIPE_NEXT_BUTTON);
        context.startService(intent);
    }

    public static void onFindRecipe(Context context) {
        Intent intent = new Intent(context, RecipeNavigateService.class);
        intent.setAction(SERVICE_RECIPE_UPDATE_BUTTON);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(null != intent) {
            String action = intent.getAction();

            if(SERVICE_RECIPE_NEXT_BUTTON.equals(action)) {
                updateSharedPrefRecipeNumber(1);
            } else if(SERVICE_RECIPE_UPDATE_BUTTON.equals(action)) {
                updateSharedPrefRecipeNumber(0);
            }
        }
    }

    private void updateSharedPrefRecipeNumber(int actionValue) {

        RecipeRepository recipeRepository = new RecipeRepository(getApplicationContext());

        List<RecipeEntity> recipeEntities = recipeRepository.fetchRecipes();

        int recipeCount = recipeEntities.size();
        String recipeName = "EMPTY";

        if(recipeCount >= 0) {

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.WIDGET_SHARED_PREF_FILE, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            int idIndex = sharedPreferences.getInt(Constants.WIDGET_RECIPE_ID_INDEX, 0);

            idIndex = idIndex + actionValue;

            if(idIndex > recipeCount-1) {
                // Move the index to 0, if exceeds limit.
                idIndex = 0;
            }

            editor.putInt(Constants.WIDGET_RECIPE_ID_INDEX, idIndex);

            editor.apply();

            int recipeId = recipeEntities.get(idIndex).getId();
            recipeName = recipeId + ". " + recipeEntities.get(idIndex).getName();
        }


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetlistItems);

        //Now update all widgets
        IngredientsWidgetProvider.updateAllAppWidgets(this, appWidgetManager, appWidgetIds, recipeName);
    }
}
