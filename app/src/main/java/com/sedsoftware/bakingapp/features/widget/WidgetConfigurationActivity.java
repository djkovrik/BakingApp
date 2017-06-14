package com.sedsoftware.bakingapp.features.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.sedsoftware.bakingapp.BakingAppModule;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.source.local.prefs.PreferencesHelper;
import java.util.Set;
import javax.inject.Inject;

public class WidgetConfigurationActivity extends AppCompatActivity {

  @Inject
  PreferencesHelper preferencesHelper;

  int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

  @BindView(R.id.radioGroup)
  RadioGroup namesRadioGroup;

  public WidgetConfigurationActivity() {
    super();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setResult(RESULT_CANCELED);
    setContentView(R.layout.widget_configuration_activity);
    ButterKnife.bind(this);

    DaggerWidgetConfigurationActivityComponent.builder()
        .bakingAppModule(new BakingAppModule(getApplicationContext()))
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

    // Fill the radioGroup
    Set<String> names = preferencesHelper.getRecipeNamesList();
    int currentIndex = 0;

    for (String name : names) {
      RadioButton button = new RadioButton(this);
      button.setText(name);
      button.setId(currentIndex++);
      namesRadioGroup.addView(button);
    }

    // Check the first item when loaded
    if (namesRadioGroup.getChildCount() > 0) {
      ((RadioButton) namesRadioGroup.getChildAt(0)).setChecked(true);
    }
  }

  @OnClick(R.id.button)
  public void onOkButtonClick() {
    int checkedItemId = namesRadioGroup.getCheckedRadioButtonId();
    String name = ((RadioButton) namesRadioGroup.getChildAt(checkedItemId)).getText().toString();

    preferencesHelper.saveChosenRecipeName(mAppWidgetId, name);

    // TODO(1) Call Widget update from here

    Intent resultValue = new Intent();
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    setResult(RESULT_OK, resultValue);
    finish();
  }
}
