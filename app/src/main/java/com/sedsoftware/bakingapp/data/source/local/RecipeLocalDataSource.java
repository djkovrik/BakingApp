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
    return null;
  }

  @Override
  public Observable<Recipe> getRecipe(int recipeId) {
    return null;
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

    } finally {
      transaction.end();
    }
  }

  @Override
  public void deleteAllRecipes() {
    databaseHelper.delete(RecipeEntry.TABLE_NAME, null);
    databaseHelper.delete(StepEntry.TABLE_NAME, null);
    databaseHelper.delete(IngredientEntry.TABLE_NAME, null);
  }
}
