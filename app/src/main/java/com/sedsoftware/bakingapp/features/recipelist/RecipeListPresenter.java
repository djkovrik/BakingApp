package com.sedsoftware.bakingapp.features.recipelist;

import com.sedsoftware.bakingapp.data.source.RecipeRepository;
import com.sedsoftware.bakingapp.features.recipelist.RecipeListContract.View;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class RecipeListPresenter implements RecipeListContract.Presenter {

  private final RecipeRepository recipeRepository;
  private final RecipeListContract.View recipesView;
  private CompositeDisposable disposableList;

  @Inject
  public RecipeListPresenter(RecipeRepository recipeRepository,
      View recipesView) {
    this.recipeRepository = recipeRepository;
    this.recipesView = recipesView;

    disposableList = new CompositeDisposable();
  }

  @Inject
  void setupListeners() {
    recipesView.setPresenter(this);
  }

  @Override
  public void subscribe() {
    loadRecipesFromRepo(false);
  }

  @Override
  public void unsubscribe() {
    disposableList.clear();
  }

  @Override
  public void loadRecipesFromRepo(boolean forcedSync) {
    loadRecipesFromRepo(forcedSync, true);
  }

  private void loadRecipesFromRepo(boolean forcedSync, boolean showLoadingUI) {

    recipesView.showLoadingIndicator(showLoadingUI);

    if (forcedSync) {
      recipeRepository.syncRecipes();
    }

    disposableList.clear();

    Disposable subscription = recipeRepository
        .getRecipes()
        .subscribe(
            // OnNext
            recipesView::showRecipeList,
            // OnError
            throwable -> recipesView.showErrorMessage(),
            //OnComplete
            () -> recipesView.showLoadingIndicator(false));

    disposableList.add(subscription);
  }
}
