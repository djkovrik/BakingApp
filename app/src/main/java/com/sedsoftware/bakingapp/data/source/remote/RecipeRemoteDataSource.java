package com.sedsoftware.bakingapp.data.source.remote;

import android.support.annotation.NonNull;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.source.RecipeDataSource;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeRemoteDataSource implements RecipeDataSource {

  @Inject
  public RecipeRemoteDataSource(RecipeService service) {
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
  public void saveRecipe(@NonNull Recipe recipe) {

  }

  @Override
  public void refreshRecipeList() {

  }

  @Override
  public void deleteRecipeList() {

  }
}
