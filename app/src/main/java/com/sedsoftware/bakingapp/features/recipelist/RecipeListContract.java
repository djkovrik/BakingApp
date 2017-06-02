package com.sedsoftware.bakingapp.features.recipelist;

import com.sedsoftware.bakingapp.BasePresenter;
import com.sedsoftware.bakingapp.BaseView;
import com.sedsoftware.bakingapp.data.model.Recipe;
import java.util.List;

public interface RecipeListContract {

  interface View extends BaseView<Presenter> {

    void showRecipeList(List<Recipe> recipeList);
  }

  interface Presenter extends BasePresenter {

    void loadRecipes(boolean forceReloadFromServer);
  }
}
