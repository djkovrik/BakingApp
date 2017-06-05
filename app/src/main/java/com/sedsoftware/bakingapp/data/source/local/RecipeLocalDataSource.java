package com.sedsoftware.bakingapp.data.source.local;

import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.data.model.Step;
import com.sedsoftware.bakingapp.data.source.RecipeDataSource;
import com.sedsoftware.bakingapp.data.source.local.db.IngredientPersistenceContract.IngredientEntry;
import com.sedsoftware.bakingapp.data.source.local.db.RecipePersistenceContract.RecipeEntry;
import com.sedsoftware.bakingapp.data.source.local.db.StepPersistenceContract.StepEntry;
import com.sedsoftware.bakingapp.utils.DbUtils;
import com.squareup.sqlbrite.BriteDatabase;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeLocalDataSource implements RecipeDataSource {

  private final BriteDatabase databaseHelper;

  @Inject
  public RecipeLocalDataSource(BriteDatabase briteDatabase) {
    this.databaseHelper = briteDatabase;
  }

  @Override
  public Observable<List<Recipe>> getRecipes() {

    rx.Observable<List<Recipe>> listObservable = databaseHelper
        .createQuery(RecipeEntry.TABLE_NAME, DbUtils.getSelectAllQuery(RecipeEntry.TABLE_NAME))
        .mapToOne(DbUtils::recipesFromCursor);

    return RxJavaInterop.toV2Observable(listObservable);
  }

  @Override
  public Observable<List<Ingredient>> getRecipeIngredients(int recipeId) {

    rx.Observable<List<Ingredient>> listObservable = databaseHelper
        .createQuery(IngredientEntry.TABLE_NAME,
            DbUtils.getSelectByIdQuery(IngredientEntry.TABLE_NAME,
                IngredientEntry.COLUMN_RECIPE_ID),
            String.valueOf(recipeId))
        .mapToOne(DbUtils::ingredientsFromCursor);

    return RxJavaInterop.toV2Observable(listObservable);
  }

  @Override
  public Observable<List<Step>> getRecipeSteps(int recipeId) {

    rx.Observable<List<Step>> listObservable = databaseHelper
        .createQuery(StepEntry.TABLE_NAME,
            DbUtils.getSelectByIdQuery(StepEntry.TABLE_NAME,
                StepEntry.COLUMN_RECIPE_ID),
            String.valueOf(recipeId))
        .mapToOne(DbUtils::stepsFromCursor);

    return RxJavaInterop.toV2Observable(listObservable);
  }

  @Override
  public void saveRecipes(List<Recipe> recipes) {

    BriteDatabase.Transaction transaction = databaseHelper.newTransaction();

    try {
      deleteAllRecipes();

      for (Recipe recipe : recipes) {

        int id = recipe.id();

        for (Ingredient ingredient : recipe.ingredients()) {
          databaseHelper.insert(IngredientEntry.TABLE_NAME,
              DbUtils.ingredientToContentValues(ingredient, id));
        }

        for (Step step : recipe.steps()) {
          databaseHelper.insert(StepEntry.TABLE_NAME,
              DbUtils.stepToContentValues(step, id));
        }

        databaseHelper.insert(RecipeEntry.TABLE_NAME,
            DbUtils.recipeToContentValues(recipe));
      }

      transaction.markSuccessful();

    } finally {
      transaction.end();
    }
  }

  @Override
  public void syncRecipes() {
    // Not implemented because sync handled by main repository
    throw new UnsupportedOperationException("syncRecipes in RemoteDataSource is not implemented!");
  }

  private void deleteAllRecipes() {
    databaseHelper.delete(RecipeEntry.TABLE_NAME, null);
    databaseHelper.delete(StepEntry.TABLE_NAME, null);
    databaseHelper.delete(IngredientEntry.TABLE_NAME, null);
  }
}
