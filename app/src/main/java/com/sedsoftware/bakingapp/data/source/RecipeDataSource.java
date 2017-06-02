package com.sedsoftware.bakingapp.data.source;

import android.support.annotation.NonNull;
import com.sedsoftware.bakingapp.data.model.Recipe;
import io.reactivex.Observable;
import java.util.List;

public interface RecipeDataSource {

  Observable<List<Recipe>> getRecipes();

  Observable<Recipe> getRecipe(int recipeId);

  void saveRecipe(@NonNull Recipe recipe);

  void refreshRecipeList();

  void deleteRecipeList();
}
