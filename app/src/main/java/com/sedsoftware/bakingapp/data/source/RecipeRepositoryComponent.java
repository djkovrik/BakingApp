package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.BakingAppModule;
import com.sedsoftware.bakingapp.data.source.local.db.DbModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {RecipeRepositoryModule.class, DbModule.class, BakingAppModule.class})
public interface RecipeRepositoryComponent {

  RecipeRepository getRecipeRepository();
}
