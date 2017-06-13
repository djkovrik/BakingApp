package com.sedsoftware.bakingapp.features.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sedsoftware.bakingapp.BakingAppModule;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.source.local.prefs.PreferencesHelper;
import com.sedsoftware.bakingapp.data.source.local.prefs.PreferencesModule;
import javax.inject.Inject;

public class IngredientsWidgetConfigurationActivity extends AppCompatActivity {

  @Inject
  PreferencesHelper preferencesHelper;

  int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

  public IngredientsWidgetConfigurationActivity() {
    super();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setResult(RESULT_CANCELED);
    setContentView(R.layout.widget_configuration_activity);

    DaggerIngredientsWidgetComponent.builder()
        .bakingAppModule(new BakingAppModule(getApplicationContext()))
        .preferencesModule(new PreferencesModule())
        .build()
        .inject(this);

    Intent intent = getIntent();
    Bundle extras = intent.getExtras();

    if (extras != null) {
      mAppWidgetId = extras.getInt(
          AppWidgetManager.EXTRA_APPWIDGET_ID,
          AppWidgetManager.INVALID_APPWIDGET_ID);

      if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
        finish();
      }
    }
    finish();
  }
}
