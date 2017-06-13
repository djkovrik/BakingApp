package com.sedsoftware.bakingapp.features.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientsWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new IngredientsWidgetFactory(getApplicationContext());
  }
}
