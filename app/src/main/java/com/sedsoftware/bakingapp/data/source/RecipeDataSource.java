package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.data.model.Recipe;
import io.reactivex.Observable;
import java.util.List;

public interface RecipeDataSource {

  Observable<List<Recipe>> getRecipes();

  Observable<Recipe> getRecipe(int recipeId);

  void saveRecipes(List<Recipe> recipes);
}
