package com.sedsoftware.bakingapp.data.source;

import android.content.Context;
import com.sedsoftware.bakingapp.data.source.local.Local;
import com.sedsoftware.bakingapp.data.source.local.RecipeLocalDataSource;
import com.sedsoftware.bakingapp.data.source.remote.RecipeRemoteDataSource;
import com.sedsoftware.bakingapp.data.source.remote.Remote;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class RecipeRepositoryModule {

  @Singleton
  @Provides
  @Local
  RecipeDataSource provideRecipeLocalDataSource(Context context) {
    return new RecipeLocalDataSource(context);
  }

  @Singleton
  @Provides
  @Remote
  RecipeDataSource provideRecipeRemoteDataSource() {
    return new RecipeRemoteDataSource();
  }
}
