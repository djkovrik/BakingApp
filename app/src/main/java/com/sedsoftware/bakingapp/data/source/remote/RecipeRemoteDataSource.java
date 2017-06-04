package com.sedsoftware.bakingapp.data.source.remote;

import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.source.RecipeDataSource;
import com.sedsoftware.bakingapp.utils.RxUtils;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeRemoteDataSource implements RecipeDataSource {

  private final RecipeService service;

  @Inject
  public RecipeRemoteDataSource(RecipeService service) {
    this.service = service;
  }

  @Override
  public Observable<List<Recipe>> getRecipes() {
    return service
        .loadRecipesFromServer()
        .compose(RxUtils.applySchedulers());
  }

  @Override
  public void saveRecipes(List<Recipe> recipes) {
    // Not implemented because we won't save recipes to server
    throw new UnsupportedOperationException("saveRecipes in RemoteDataSource is not implemented!");
  }

  @Override
  public void syncRecipes() {
    // Not implemented because sync handled by main repository
    throw new UnsupportedOperationException("syncRecipes in RemoteDataSource is not implemented!");
  }
}
