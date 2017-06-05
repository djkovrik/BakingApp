package com.sedsoftware.bakingapp.data.source.remote;

import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.model.Step;
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
  public Observable<List<Ingredient>> getRecipeIngredients(int recipeId) {
    throw new UnsupportedOperationException("getRecipeIngredients in RemoteDataSource is not implemented!");
  }

  @Override
  public Observable<List<Step>> getRecipeSteps(int recipeId) {
    throw new UnsupportedOperationException("getRecipeSteps in RemoteDataSource is not implemented!");
  }

  @Override
  public void saveRecipes(List<Recipe> recipes) {
    throw new UnsupportedOperationException("saveRecipes in RemoteDataSource is not implemented!");
  }

  @Override
  public void syncRecipes() {
    throw new UnsupportedOperationException("syncRecipes in RemoteDataSource is not implemented!");
  }
}
