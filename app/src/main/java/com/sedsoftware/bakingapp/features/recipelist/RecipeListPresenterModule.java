package com.sedsoftware.bakingapp.features.recipelist;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeListPresenterModule {

  private final RecipeListContract.View view;

  public RecipeListPresenterModule(RecipeListContract.View view) {
    this.view = view;
  }

  @Provides
  RecipeListContract.View provideRecipeListContractView() {
    return view;
  }
}
