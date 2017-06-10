package com.sedsoftware.bakingapp.features.recipestep;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sedsoftware.bakingapp.BakingApp;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.utils.FragmentUtils;
import javax.inject.Inject;

public class RecipeStepActivity extends AppCompatActivity {

  public static final String EXTRA_RECIPE_ID = "RECIPE_ID";
  private static final int DEFAULT_RECIPE_ID = 1;

  public static final String EXTRA_STEP_ID = "STEP_ID";
  private static final int DEFAULT_STEP_ID = 0;

  @Inject
  RecipeStepPresenter recipeStepPresenter;

  public static Intent prepareIntent(Context context, int recipeId, int stepId) {
    Intent intent = new Intent(context, RecipeStepActivity.class);
    intent.putExtra(EXTRA_RECIPE_ID, recipeId);
    intent.putExtra(EXTRA_STEP_ID, stepId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_recipe_step);

    int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, DEFAULT_RECIPE_ID);
    int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, DEFAULT_STEP_ID);

    RecipeStepFragment recipeStepFragment =
        (RecipeStepFragment) getSupportFragmentManager()
            .findFragmentById(R.id.stepFragmentContainer);

    if (recipeStepFragment == null) {
      recipeStepFragment = RecipeStepFragment.newInstance(stepId);
      FragmentUtils.addFragmentTo(getSupportFragmentManager(), recipeStepFragment,
          R.id.stepFragmentContainer);
    }

    DaggerRecipeStepComponent.builder()
        .recipeRepositoryComponent(((BakingApp) getApplication()).getRecipeRepositoryComponent())
        .recipeStepPresenterModule(new RecipeStepPresenterModule(recipeStepFragment, recipeId))
        .build()
        .inject(this);
  }
}
