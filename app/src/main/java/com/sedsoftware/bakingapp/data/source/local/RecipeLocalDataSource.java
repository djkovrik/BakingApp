package com.sedsoftware.bakingapp.data.source.local;

import android.support.annotation.NonNull;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.source.RecipeDataSource;
import com.squareup.sqlbrite.BriteDatabase;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeLocalDataSource implements RecipeDataSource {

  private final BriteDatabase databaseHelper;

  @Inject
  public RecipeLocalDataSource(BriteDatabase briteDatabase) {
    this.databaseHelper = briteDatabase;
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
