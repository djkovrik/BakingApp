package com.sedsoftware.bakingapp.features.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.sedsoftware.bakingapp.R;

public class IngredientsWidgetProvider extends AppWidgetProvider {

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    for (int appWidgetId : appWidgetIds) {

      RemoteViews views = new RemoteViews(context.getPackageName(),
          R.layout.widget_ingredients_list);

      views.setRemoteAdapter(R.id.widget_list, new Intent(context, IngredientsWidgetService.class));
      views.setEmptyView(R.id.widget_list, R.id.widget_empty);

      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
  }
}
