package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.model.Step;
import com.sedsoftware.bakingapp.data.source.local.Local;
import com.sedsoftware.bakingapp.data.source.local.prefs.PreferencesHelper;
import com.sedsoftware.bakingapp.data.source.remote.Remote;
import com.sedsoftware.bakingapp.utils.RxUtils;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeRepository implements RecipeDataSource {

  final RecipeDataSource recipeRemoteDataSource;
  final RecipeDataSource recipeLocalDataSource;
  final PreferencesHelper preferencesHelper;

  @Inject
  public RecipeRepository(
      @Remote RecipeDataSource recipeRemoteDataSource,
      @Local RecipeDataSource recipeLocalDataSource,
      PreferencesHelper preferencesHelper) {
    this.recipeRemoteDataSource = recipeRemoteDataSource;
    this.recipeLocalDataSource = recipeLocalDataSource;
    this.preferencesHelper = preferencesHelper;
  }

  @Override
  public Observable<List<Recipe>> getRecipes() {

    if (!preferencesHelper.isRecipeListSynced()) {
      return recipeRemoteDataSource
          .getRecipes()
          .compose(RxUtils.applySchedulers())
          .doOnNext(this::saveRecipes);
    } else {
      return recipeLocalDataSource
          .getRecipes()
          .compose(RxUtils.applySchedulers());
    }
  }

  @Override
  public Observable<List<Ingredient>> getRecipeIngredients(int recipeId) {
    return recipeLocalDataSource.getRecipeIngredients(recipeId);
  }

  @Override
  public Observable<List<Step>> getRecipeSteps(int recipeId) {
    return recipeLocalDataSource.getRecipeSteps(recipeId);
  }

  @Override
  public void saveRecipes(List<Recipe> recipes) {
    recipeLocalDataSource.saveRecipes(recipes);
  }

  public void markRepoAsSynced(boolean synced) {
    preferencesHelper.setRecipeListSynced(synced);
  }
}
