package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.source.local.Local;
import com.sedsoftware.bakingapp.data.source.remote.Remote;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeRepository implements RecipeDataSource {

  private final RecipeDataSource recipeRemoteDataSource;
  private final RecipeDataSource recipeLocalDataSource;

  @Inject
  public RecipeRepository(@Remote RecipeDataSource recipeRemoteDataSource,
      @Local RecipeDataSource recipeLocalDataSource) {
    this.recipeRemoteDataSource = recipeRemoteDataSource;
    this.recipeLocalDataSource = recipeLocalDataSource;
  }

  @Override
  public Observable<List<Recipe>> getRecipes() {
    return null;
  }

  @Override
  public Observable<Recipe> getRecipe(int recipeId) {
    return null;
  }

  @Override
  public void saveRecipes(List<Recipe> recipes) {

  }
}
