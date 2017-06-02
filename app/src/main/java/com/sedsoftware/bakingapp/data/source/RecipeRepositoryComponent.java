package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.BakingAppModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {RecipeRepositoryModule.class, BakingAppModule.class})
public interface RecipeRepositoryComponent {

  RecipeRepository getRecipeRepository();
}
