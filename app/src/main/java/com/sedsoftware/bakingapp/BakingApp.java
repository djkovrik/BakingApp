package com.sedsoftware.bakingapp;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.sedsoftware.bakingapp.data.source.DaggerRecipeRepositoryComponent;
import com.sedsoftware.bakingapp.data.source.RecipeRepositoryComponent;
import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;

public class BakingApp extends Application {

  private RecipeRepositoryComponent recipeRepositoryComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // We should not init our app in this process.
      return;
    }
    LeakCanary.install(this);
    // Normal app init code below.

    if (BuildConfig.DEBUG) {
      Timber.uprootAll();
      Timber.plant(new Timber.DebugTree());

      Stetho.initializeWithDefaults(this);
    }

    recipeRepositoryComponent = DaggerRecipeRepositoryComponent.builder()
        .bakingAppModule(new BakingAppModule(getApplicationContext()))
        .build();
  }

  public RecipeRepositoryComponent getRecipeRepositoryComponent() {
    return recipeRepositoryComponent;
  }
}
