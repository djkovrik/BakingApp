package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.data.source.local.Local;
import com.sedsoftware.bakingapp.data.source.local.RecipeLocalDataSource;
import com.sedsoftware.bakingapp.data.source.remote.RecipeRemoteDataSource;
import com.sedsoftware.bakingapp.data.source.remote.RecipeService;
import com.sedsoftware.bakingapp.data.source.remote.Remote;
import com.squareup.sqlbrite.BriteDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
class RecipeRepositoryModule {

  @Singleton
  @Provides
  @Local
  RecipeDataSource provideRecipeLocalDataSource(BriteDatabase briteDatabase) {
    return new RecipeLocalDataSource(briteDatabase);
  }

  @Singleton
  @Provides
  @Remote
  RecipeDataSource provideRecipeRemoteDataSource(RecipeService service) {
    return new RecipeRemoteDataSource(service);
  }
}
