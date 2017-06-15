package com.sedsoftware.bakingapp.features.recipestep;

import dagger.Module;
import dagger.Provides;

@Module
class RecipeStepPresenterModule {

  private final RecipeStepContract.View view;
  private final int recipeId;

  RecipeStepPresenterModule(RecipeStepContract.View view, int recipeId) {
    this.view = view;
    this.recipeId = recipeId;
  }

  @Provides
  RecipeStepContract.View provideRecipeStepContractView() {
    return view;
  }

  @Provides
  int provideRecipeId() {
    return recipeId;
  }
}
