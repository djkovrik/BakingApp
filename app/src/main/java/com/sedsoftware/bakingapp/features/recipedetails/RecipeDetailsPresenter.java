package com.sedsoftware.bakingapp.features.recipedetails;

import com.sedsoftware.bakingapp.data.source.RecipeRepository;
import com.sedsoftware.bakingapp.features.recipedetails.RecipeDetailsContract.View;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {

  private final RecipeRepository recipeRepository;
  private final RecipeDetailsContract.View detailsView;
  private int recipeId;

  private CompositeDisposable disposableList;

  @Inject
  public RecipeDetailsPresenter(
      RecipeRepository recipeRepository,
      View detailsView, int recipeId) {
    this.recipeRepository = recipeRepository;
    this.detailsView = detailsView;
    this.recipeId = recipeId;

    disposableList = new CompositeDisposable();
  }

  @Inject
  void setupListeners() {
    detailsView.setPresenter(this);
  }

  @Override
  public void subscribe() {
    loadRecipeNameFromRepo();
    loadIngredientsFromRepo();
    loadStepsFromRepo();
  }

  @Override
  public void unsubscribe() {
    disposableList.clear();
  }

  @Override
  public void loadRecipeNameFromRepo() {
    Disposable subscription = recipeRepository
        .getRecipes()
        .flatMap(Observable::fromIterable)
        .filter(recipe -> recipe.id() == recipeId)
        .subscribe(
            // OnNext
            recipe -> detailsView.showRecipeNameInActivityTitle(recipe.name()),
            // OnError
            throwable -> detailsView.showErrorMessage());

    disposableList.add(subscription);
  }

  @Override
  public void loadIngredientsFromRepo() {

    Disposable subscription = recipeRepository
        .getRecipeIngredients(recipeId)
        .subscribe(
            // OnNext
            detailsView::showIngredientsList,
            // OnError
            throwable -> detailsView.showErrorMessage());

    disposableList.add(subscription);
  }

  @Override
  public void loadStepsFromRepo() {

    Disposable subscription = recipeRepository
        .getRecipeSteps(recipeId)
        .subscribe(
            // OnNext
            detailsView::showStepsList,
            // OnError
            throwable -> detailsView.showErrorMessage());

    disposableList.add(subscription);
  }

  @Override
  public void openStepDetails(int stepId) {
    detailsView.showStepDetails(stepId);
  }

  @Override
  public void fetchStepData(int stepId) {

    Disposable subscription = recipeRepository
        .getRecipeSteps(recipeId)
        .flatMap(Observable::fromIterable)
        .filter(step -> step.id() == stepId)
        .subscribe(
            // OnNext
            step ->
                detailsView.refreshStepContainerFragment(
                    step.description(),
                    step.videoURL()),
            // OnError
            throwable -> detailsView.showErrorMessage());

    disposableList.add(subscription);
  }
}
