package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.model.Step;
import com.sedsoftware.bakingapp.data.source.local.Local;
import com.sedsoftware.bakingapp.data.source.local.prefs.PreferencesHelper;
import com.sedsoftware.bakingapp.data.source.remote.Remote;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

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
      syncRecipes();
    }

    return recipeLocalDataSource.getRecipes();
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
    throw new UnsupportedOperationException("saveRecipes in RecipeRepository is not implemented!");
  }

  @Override
  public void syncRecipes() {
    recipeRemoteDataSource
        .getRecipes()
        .subscribe(getServerObserver());
  }

  private Observer<List<Recipe>> getServerObserver() {
    return new Observer<List<Recipe>>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Sync started...");
      }

      @Override
      public void onNext(@NonNull List<Recipe> recipeList) {
        recipeLocalDataSource.saveRecipes(recipeList);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Sync failed!");
        preferencesHelper.setRecipeListSynced(false);
      }

      @Override
      public void onComplete() {
        Timber.d("Sync completed.");
        preferencesHelper.setRecipeListSynced(true);
      }
    };
  }
}
