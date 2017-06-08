package com.sedsoftware.bakingapp.features.recipedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Step;
import java.util.List;
import timber.log.Timber;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsContract.View  {

  private RecipeDetailsContract.Presenter recipeDetailsPresenter;

  public RecipeDetailsFragment() {
  }

  public static RecipeDetailsFragment newInstance() {
    return new RecipeDetailsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_recipe_details, container, false);
  }

  @Override
  public void onResume() {
    super.onResume();
    recipeDetailsPresenter.subscribe();
  }

  @Override
  public void onPause() {
    super.onPause();
    recipeDetailsPresenter.unsubscribe();
  }

  @Override
  public void setPresenter(RecipeDetailsContract.Presenter presenter) {
    this.recipeDetailsPresenter = presenter;
  }

  @Override
  public void showIngredientsList(List<Ingredient> ingredients) {
    for (Ingredient ingredient : ingredients) {
      Timber.d("+++ INGREDIENT: " + ingredient.ingredient());
    }
  }

  @Override
  public void showStepsList(List<Step> steps) {
    for (Step step : steps) {
      Timber.d("--- Step: " + step.shortDescription());
    }
  }

  @Override
  public void showErrorMessage() {
    // TODO(2) Implement this (perhaps not used)
  }

  @Override
  public void showStepDetails(int stepId) {
    Toast.makeText(getContext(), "Step: " + String.valueOf(stepId), Toast.LENGTH_SHORT).show();
  }
}
