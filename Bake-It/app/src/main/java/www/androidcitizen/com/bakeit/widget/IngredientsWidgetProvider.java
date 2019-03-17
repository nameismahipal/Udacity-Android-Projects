package www.androidcitizen.com.bakeit.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.ui.activity.MainActivity;

import static www.androidcitizen.com.bakeit.util.Constants.*;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName) {

        // Construct the RemoteViews object
        RemoteViews views = getListViewIngredients(context, recipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeNavigateService.onFindRecipe(context);
    }

    public static void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String recipeName) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName);
        }
    }

    private static RemoteViews getListViewIngredients(Context context, String recipeName) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);

        views.setTextViewText(R.id.widgetRecipeName, recipeName);

        Intent listIntent = new Intent(context, ListViewService.class);
        views.setRemoteAdapter(R.id.widgetlistItems, listIntent);

        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.recipeName, pendingIntent);

        Intent nextBtnIntent = new Intent(context, RecipeNavigateService.class);
        nextBtnIntent.setAction(RecipeNavigateService.SERVICE_RECIPE_NEXT_BUTTON);
        PendingIntent nextBtnPendingIntent = PendingIntent.getService(context, 0, nextBtnIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widgetRightArrow, nextBtnPendingIntent);

        return views;
    }

    @Override
    public void onEnabled(Context context) {

            SharedPreferences sharedPreferences = context.getSharedPreferences(WIDGET_SHARED_PREF_FILE, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(WIDGET_RECIPE_ID_INDEX, 0);
            editor.putString(WIDGET_RECIPE_NAME, "");

            editor.apply();

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        SharedPreferences sharedPreferences = context.getSharedPreferences(WIDGET_SHARED_PREF_FILE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

    }
}

