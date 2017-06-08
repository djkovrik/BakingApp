package com.sedsoftware.bakingapp.features.recipestep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sedsoftware.bakingapp.BakingApp;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.utils.ActivityUtils;
import javax.inject.Inject;
import timber.log.Timber;

public class RecipeStepActivity extends AppCompatActivity {

  public static final String EXTRA_RECIPE_ID = "RECIPE_ID";
  private static final int DEFAULT_RECIPE_ID = 1;

  public static final String EXTRA_STEP_ID = "STEP_ID";
  private static final int DEFAULT_STEP_ID = 0;

  @Inject
  RecipeStepPresenter recipeStepPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_recipe_step);

    int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, DEFAULT_RECIPE_ID);
    int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, DEFAULT_STEP_ID);

    Timber.d("GOT STEP ID: " + stepId);

    RecipeStepFragment recipeStepFragment =
        (RecipeStepFragment) getSupportFragmentManager()
            .findFragmentById(R.id.stepFragmentContainer);

    if (recipeStepFragment == null) {
      recipeStepFragment = RecipeStepFragment.newInstance();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), recipeStepFragment,
          R.id.stepFragmentContainer);
    }

    DaggerRecipeStepComponent.builder()
        .recipeRepositoryComponent(((BakingApp) getApplication()).getRecipeRepositoryComponent())
        .recipeStepPresenterModule(new RecipeStepPresenterModule(recipeStepFragment, recipeId))
        .build()
        .inject(this);
  }
}
