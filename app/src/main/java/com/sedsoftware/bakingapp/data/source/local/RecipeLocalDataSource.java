package com.sedsoftware.bakingapp.data.source.local;

import android.database.Cursor;
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
import java.util.ArrayList;
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
  public Observable<List<Recipe>> getRecipes() {

    List<Recipe> results = new ArrayList<>();

    databaseHelper.createQuery(RecipeEntry.TABLE_NAME,
        DbUtils.getSelectAllQuery(RecipeEntry.TABLE_NAME))
        .subscribe(query -> {

          Cursor recipe = query.run();

          while (recipe != null && recipe.moveToNext()) {
            int id = recipe.getInt(recipe.getColumnIndexOrThrow(RecipeEntry.COLUMN_RECIPE_ID));
            String name = recipe.getString(recipe.getColumnIndexOrThrow(RecipeEntry.COLUMN_NAME));
            int servings = recipe.getInt(recipe.getColumnIndexOrThrow(RecipeEntry.COLUMN_SERVINGS));
            String image = recipe.getString(recipe.getColumnIndexOrThrow(RecipeEntry.COLUMN_IMAGE));

            rx.Observable
                .zip(getIngredientsForRecipe(id), getStepsForRecipe(id), (ingredients, steps)
                    -> Recipe.builder()
                    .id(id)
                    .name(name)
                    .ingredients(ingredients)
                    .steps(steps)
                    .servings(servings)
                    .image(image)
                    .build())
                .subscribe(results::add);
          }
        });

    // returns RxJava2 Observable
    return Observable.just(results);
  }

  private rx.Observable<List<Ingredient>> getIngredientsForRecipe(int recipeId) {

    return databaseHelper
        .createQuery(IngredientEntry.TABLE_NAME,
            DbUtils.getSelectByIdQuery(IngredientEntry.TABLE_NAME,
                IngredientEntry.COLUMN_RECIPE_ID),
            String.valueOf(recipeId))
        .mapToOne(DbUtils::ingredientsFromCursor);
  }

  private rx.Observable<List<Step>> getStepsForRecipe(int recipeId) {

    return databaseHelper
        .createQuery(StepEntry.TABLE_NAME,
            DbUtils.getSelectByIdQuery(StepEntry.TABLE_NAME,
                StepEntry.COLUMN_RECIPE_ID),
            String.valueOf(recipeId))
        .mapToOne(DbUtils::stepsFromCursor);
  }

  private void deleteAllRecipes() {
    databaseHelper.delete(RecipeEntry.TABLE_NAME, null);
    databaseHelper.delete(StepEntry.TABLE_NAME, null);
    databaseHelper.delete(IngredientEntry.TABLE_NAME, null);
  }
}
