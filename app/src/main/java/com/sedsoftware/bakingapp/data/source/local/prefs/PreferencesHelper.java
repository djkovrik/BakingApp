package com.sedsoftware.bakingapp.data.source.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

  private static final String PREFS_FILE_NAME = "baking_app_prefs";
  private static final String PREFERENCE_NAME = "baking_app_synced";

  private final SharedPreferences sharedPreferences;

  @Inject
  public PreferencesHelper(Context context) {
    sharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
  }

  public void setRecipeListSynced(boolean flag) {
    sharedPreferences.edit().putBoolean(PREFERENCE_NAME, flag).apply();
  }

  public boolean isRecipeListSynced() {
    return sharedPreferences.getBoolean(PREFERENCE_NAME, false);
  }
}
