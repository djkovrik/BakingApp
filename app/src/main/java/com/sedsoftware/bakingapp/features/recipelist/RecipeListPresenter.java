package com.sedsoftware.bakingapp.features.recipelist;

import com.sedsoftware.bakingapp.data.source.RecipeRepository;
import com.sedsoftware.bakingapp.features.recipelist.RecipeListContract.View;
import javax.inject.Inject;

public class RecipeListPresenter implements RecipeListContract.Presenter {

  private final RecipeRepository recipeRepository;
  private final RecipeListContract.View recipesView;

  @Inject
  public RecipeListPresenter(RecipeRepository recipeRepository,
      View recipesView) {
    this.recipeRepository = recipeRepository;
    this.recipesView = recipesView;
  }

  @Inject
  void setupListeners() {
    recipesView.setPresenter(this);
  }

  @Override
  public void start() {

  }

  @Override
  public void loadRecipes(boolean forceReloadFromServer) {

  }
}
