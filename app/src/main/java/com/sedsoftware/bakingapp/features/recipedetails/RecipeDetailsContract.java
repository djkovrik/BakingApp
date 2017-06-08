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

    void showStepDetails(int stepId);
  }

  interface Presenter extends BasePresenter {

    void loadIngredientsFromRepo();

    void loadStepsFromRepo();

    void openStepDetails(int stepId);
  }
}
