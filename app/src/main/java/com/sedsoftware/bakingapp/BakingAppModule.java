package com.sedsoftware.bakingapp;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class BakingAppModule {

  private final Context context;

  public BakingAppModule(Context context) {
    this.context = context;
  }

  @Provides
  Context provideContext() {
    return context;
  }
}
