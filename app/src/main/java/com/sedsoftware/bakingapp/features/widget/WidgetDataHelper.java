package com.sedsoftware.bakingapp.features.widget;

import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.source.RecipeRepository;
import io.reactivex.Observable;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import timber.log.Timber;

public class WidgetDataHelper {

  private final RecipeRepository recipeRepository;

  @Inject
  public WidgetDataHelper(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  Set<String> getRecipeNamesFromPrefs() {
    return recipeRepository.getPreferencesHelper().getRecipeNamesList();
  }

  void deleteRecipeFromPrefs(int widgetId) {
    Timber.d("Deleting recipe [" + widgetId + "]");
    recipeRepository.getPreferencesHelper().deleteRecipeName(widgetId);
  }

  void saveChosenRecipeName(int mAppWidgetId, String name) {
    recipeRepository.getPreferencesHelper().saveChosenRecipeName(mAppWidgetId, name);
  }

  Observable<List<Ingredient>> getIngredientsList(String recipeName) {
    return recipeRepository.getRecipeIngredients(recipeName);
  }
}
