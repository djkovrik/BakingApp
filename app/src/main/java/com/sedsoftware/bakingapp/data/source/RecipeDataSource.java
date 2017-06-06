package com.sedsoftware.bakingapp.data.source;

import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.model.Step;
import io.reactivex.Observable;
import java.util.List;

public interface RecipeDataSource {

  Observable<List<Recipe>> getRecipes();

  Observable<List<Ingredient>> getRecipeIngredients(int recipeId);

  Observable<List<Step>> getRecipeSteps(int recipeId);

  void saveRecipes(List<Recipe> recipes);
}
