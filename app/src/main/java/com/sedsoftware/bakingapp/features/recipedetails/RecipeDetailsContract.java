package com.sedsoftware.bakingapp.features.recipedetails;

import com.sedsoftware.bakingapp.BasePresenter;
import com.sedsoftware.bakingapp.BaseView;
import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Step;
import java.util.List;

public interface RecipeDetailsContract {

  interface View extends BaseView<Presenter> {

    void showIngredientsList(List<Ingredient> ingredients);

    void showStepsList(List<Step> steps);

    void showErrorMessage();

    void showRecipeNameInActivityTitle(String recipeName);

    void showStepDetails(int stepId);

    void refreshStepContainerFragment(String desc, String videoUrl, String imageUrl);
  }

  interface Presenter extends BasePresenter {

    void loadRecipeNameFromRepo();

    void loadIngredientsFromRepo();

    void loadStepsFromRepo();

    void openStepDetails(int stepId);

    void fetchStepData(int stepId);
  }
}
