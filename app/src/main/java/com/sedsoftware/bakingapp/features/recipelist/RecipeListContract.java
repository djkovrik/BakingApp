package com.sedsoftware.bakingapp.features.recipelist;

import com.sedsoftware.bakingapp.BasePresenter;
import com.sedsoftware.bakingapp.BaseView;
import com.sedsoftware.bakingapp.data.idlingresource.RecipesIdlingResource;
import com.sedsoftware.bakingapp.data.model.Recipe;
import java.util.List;

public interface RecipeListContract {

  interface View extends BaseView<Presenter> {

    void showRecipeList(List<Recipe> recipeList);

    void showLoadingIndicator(boolean show);

    void showCompletedMessage();

    void showErrorMessage();

    void showRecipeDetails(int recipeId);
  }

  interface Presenter extends BasePresenter {

    void loadRecipesFromRepo(boolean forcedSync, RecipesIdlingResource resource);

    void openRecipeDetails(int recipeId);
  }
}
