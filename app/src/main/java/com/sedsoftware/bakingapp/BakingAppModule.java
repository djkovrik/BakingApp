package com.sedsoftware.bakingapp;

import android.content.Context;
import com.sedsoftware.bakingapp.data.source.remote.RecipeService;
import com.sedsoftware.bakingapp.data.source.remote.ServiceFactory;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class BakingAppModule {

  private final Context context;

  public BakingAppModule(Context context) {
    this.context = context;
  }

  @Provides
  @ApplicationContext
  Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  RecipeService provideRecipeService() {
    return ServiceFactory.createFrom(RecipeService.class, RecipeService.ENDPOINT);
  }
}
