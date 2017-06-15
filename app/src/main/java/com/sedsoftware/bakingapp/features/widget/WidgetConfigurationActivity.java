package com.sedsoftware.bakingapp.features.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.sedsoftware.bakingapp.BakingApp;
import com.sedsoftware.bakingapp.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.Set;
import javax.inject.Inject;
import timber.log.Timber;

public class WidgetConfigurationActivity extends AppCompatActivity {

  @Inject
  WidgetDataHelper widgetDataHelper;

  private CompositeDisposable disposableList;
  private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

  @BindView(R.id.radioGroup)
  RadioGroup namesRadioGroup;

  @BindString(R.string.widget_config_no_data)
  String noDataErrorMessage;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setResult(RESULT_CANCELED);
    setContentView(R.layout.widget_configuration_activity);
    ButterKnife.bind(this);

    disposableList = new CompositeDisposable();

    DaggerWidgetDataHelperComponent.builder()
        .recipeRepositoryComponent(
            ((BakingApp) getApplication()).getRecipeRepositoryComponent())
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

    Set<String> names = widgetDataHelper.getRecipeNamesFromPrefs();

    if (names.size() == 0) {
      Toast.makeText(this, noDataErrorMessage, Toast.LENGTH_SHORT).show();
      finish();
    }

    // Fill the radioGroup
    int currentIndex = 0;

    for (String name : names) {
      AppCompatRadioButton button = new AppCompatRadioButton(this);
      button.setText(name);
      button.setId(currentIndex++);
      setupRadioButtonColor(button);
      namesRadioGroup.addView(button);
    }

    // Check the first item when loaded
    if (namesRadioGroup.getChildCount() > 0) {
      ((AppCompatRadioButton) namesRadioGroup.getChildAt(0)).setChecked(true);
    }
  }

  @OnClick(R.id.button)
  public void onOkButtonClick() {

    int checkedItemId = namesRadioGroup.getCheckedRadioButtonId();
    String recipeName = ((AppCompatRadioButton) namesRadioGroup
        .getChildAt(checkedItemId)).getText().toString();

    widgetDataHelper.saveChosenRecipeName(mAppWidgetId, recipeName);

    Context context = getApplicationContext();
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

    Disposable subscription = widgetDataHelper
        .getIngredientsList(recipeName)
        .subscribe(
            // OnNext
            ingredients ->
                WidgetProvider
                    .updateAppWidgetContent(context, appWidgetManager, mAppWidgetId, recipeName,
                        ingredients),
            // OnError
            throwable ->
                Timber.d("Error: unable to populate widget data."));

    disposableList.add(subscription);

    Intent resultValue = new Intent();
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    setResult(RESULT_OK, resultValue);
    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposableList.clear();
  }

  private void setupRadioButtonColor(AppCompatRadioButton button) {

    int color = ContextCompat.getColor(this, R.color.colorPrimary);

    ColorStateList colorStateList = new ColorStateList(
        new int[][]{
            new int[]{-android.R.attr.state_checked},
            new int[]{android.R.attr.state_checked}
        },
        new int[]{
            color, color
        }
    );
    button.setSupportButtonTintList(colorStateList);
  }
}
