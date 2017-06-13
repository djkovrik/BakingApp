package com.sedsoftware.bakingapp.features.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sedsoftware.bakingapp.R;
import timber.log.Timber;

public class IngredientsWidgetConfigurationActivity extends AppCompatActivity {

  int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

  public IngredientsWidgetConfigurationActivity() {
    super();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setResult(RESULT_CANCELED);
    setContentView(R.layout.widget_configuration_activity);

    Timber.d("Activity started.");

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

    Timber.d("Activity finished.");
    finish();
  }
}
