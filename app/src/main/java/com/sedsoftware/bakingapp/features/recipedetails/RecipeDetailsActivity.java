package com.sedsoftware.bakingapp.features.recipedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sedsoftware.bakingapp.BakingApp;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.utils.ActivityUtils;
import javax.inject.Inject;
import timber.log.Timber;

public class RecipeDetailsActivity extends AppCompatActivity {

  public static final String EXTRA_RECIPE_ID = "RECIPE_ID";
  private static final int DEFAULT_RECIPE_ID = 1;

  @Inject
  RecipeDetailsPresenter recipeDetailsPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_recipe_details);

    int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, DEFAULT_RECIPE_ID);

    Timber.d("GOT RECIPE ID: " + recipeId);

    RecipeDetailsFragment recipeDetailsFragment =
        (RecipeDetailsFragment) getSupportFragmentManager()
            .findFragmentById(R.id.detailsFragmentContainer);

    if (recipeDetailsFragment == null) {
      recipeDetailsFragment = RecipeDetailsFragment.newInstance();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), recipeDetailsFragment,
          R.id.detailsFragmentContainer);
    }

    DaggerRecipeDetailsComponent.builder()
        .recipeRepositoryComponent(((BakingApp) getApplication()).getRecipeRepositoryComponent())
        .recipeDetailsPresenterModule(new RecipeDetailsPresenterModule(recipeDetailsFragment, recipeId))
        .build()
        .inject(this);
  }
}
