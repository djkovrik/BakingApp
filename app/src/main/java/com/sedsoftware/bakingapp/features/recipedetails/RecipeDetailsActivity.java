package com.sedsoftware.bakingapp.features.recipedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.sedsoftware.bakingapp.BakingApp;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.utils.FragmentUtils;
import javax.inject.Inject;

public class RecipeDetailsActivity extends AppCompatActivity {

  public static final String EXTRA_RECIPE_ID = "RECIPE_ID";
  private static final int DEFAULT_RECIPE_ID = 1;

  @Inject
  RecipeDetailsPresenter recipeDetailsPresenter;

  public static Intent prepareIntent(Context context, int recipeId) {
    Intent intent = new Intent(context, RecipeDetailsActivity.class);
    intent.putExtra(EXTRA_RECIPE_ID, recipeId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_recipe_details);

    setUpActionBar();

    int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, DEFAULT_RECIPE_ID);

    RecipeDetailsFragment recipeDetailsFragment =
        (RecipeDetailsFragment) getSupportFragmentManager()
            .findFragmentById(R.id.detailsFragmentContainer);

    if (recipeDetailsFragment == null) {
      recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipeId);
      FragmentUtils.addFragmentTo(getSupportFragmentManager(), recipeDetailsFragment,
          R.id.detailsFragmentContainer);
    }

    DaggerRecipeDetailsComponent.builder()
        .recipeRepositoryComponent(((BakingApp) getApplication()).getRecipeRepositoryComponent())
        .recipeDetailsPresenterModule(new RecipeDetailsPresenterModule(recipeDetailsFragment, recipeId))
        .build()
        .inject(this);
  }

  private void setUpActionBar() {
    ActionBar supportActionBar = getSupportActionBar();

    if (supportActionBar != null) {
      supportActionBar.setDisplayHomeAsUpEnabled(true);
    }
  }
}
