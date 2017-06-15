package com.sedsoftware.bakingapp.features.recipedetails;

import dagger.Module;
import dagger.Provides;

@Module
class RecipeDetailsPresenterModule {

  private final RecipeDetailsContract.View view;

  private final int recipeId;

  RecipeDetailsPresenterModule(RecipeDetailsContract.View view, int recipeId) {
    this.view = view;
    this.recipeId = recipeId;
  }

  @Provides
  RecipeDetailsContract.View provideRecipeDetailsContractView() {
    return view;
  }

  @Provides
  int provideRecipeId() {
    return recipeId;
  }
}
