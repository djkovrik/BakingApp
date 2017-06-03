package com.sedsoftware.bakingapp.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.source.RecipeDataSource;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeLocalDataSource implements RecipeDataSource {

  @Inject
  public RecipeLocalDataSource(Context context) {
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

  @Override
  public void saveRecipe(@NonNull Recipe recipe) {

  }
}
