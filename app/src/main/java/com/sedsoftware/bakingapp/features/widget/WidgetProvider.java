package com.sedsoftware.bakingapp.features.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import com.sedsoftware.bakingapp.BakingApp;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.model.Ingredient;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class WidgetProvider extends AppWidgetProvider {

  @Inject
  WidgetDataHelper dataHelper;

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);

    DaggerWidgetDataHelperComponent.builder()
        .recipeRepositoryComponent(
            ((BakingApp) context.getApplicationContext()).getRecipeRepositoryComponent())
        .build()
        .inject(this);

    for (int appWidgetId : appWidgetIds) {
      dataHelper.deleteRecipeFromPrefs(appWidgetId);
    }
  }

  public static void updateAppWidgetContent(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId, String recipeName, List<Ingredient> ingredients) {

    Timber.d("updateAppWidgetContent call...");
    Timber.d("id: " + appWidgetId + ", name: " + recipeName + "ingredients: " + ingredients.size());

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);
    views.setTextViewText(R.id.widget_recipe_name, recipeName);
    views.removeAllViews(R.id.widget_ingredients_container);

    for (Ingredient ingredient : ingredients) {
      RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
          R.layout.widget_ingredients_list_item);

      ingredientView.setTextViewText(R.id.widget_ingredient_name, ingredient.ingredient());
      views.addView(R.id.widget_ingredients_container, ingredientView);
    }

    appWidgetManager.updateAppWidget(appWidgetId, views);
  }
}
