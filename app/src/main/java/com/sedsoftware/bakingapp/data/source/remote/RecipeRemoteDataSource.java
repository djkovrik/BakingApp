package com.sedsoftware.bakingapp.data.source.remote;

import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.source.RecipeDataSource;
import com.sedsoftware.bakingapp.utils.RxUtils;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeRemoteDataSource implements RecipeDataSource {

  private final RecipeService service;

  @Inject
  public RecipeRemoteDataSource(RecipeService service) {
    this.service = service;
  }

  @Override
  public Observable<List<Recipe>> getRecipes() {
    return service
        .loadRecipesFromServer()
        .compose(RxUtils.applySchedulers());
  }

  @Override
  public Observable<Recipe> getRecipe(int recipeId) {
    return service
        .loadRecipesFromServer()
        .compose(RxUtils.applySchedulers())
        .flatMap(Observable::fromIterable)
        .filter(recipe -> recipe.id() == recipeId);
  }

  @Override
  public void saveRecipes(List<Recipe> recipes) {
    // Not required because we won't upload data to the remote server
    throw new UnsupportedOperationException("saveRecipes in RecipeRemoteDataSource not implemented!");
  }
}
