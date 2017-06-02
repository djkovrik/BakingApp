package com.sedsoftware.bakingapp.features.recipelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sedsoftware.bakingapp.BakingApp;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.utils.ActivityUtils;
import javax.inject.Inject;

public class RecipeListActivity extends AppCompatActivity {

  @Inject
  RecipeListPresenter recipeListPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_list);

    RecipeListFragment recipeListFragment =
        (RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

    if (recipeListFragment == null) {
      recipeListFragment = RecipeListFragment.newInstance();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), recipeListFragment,
          R.id.fragmentContainer);
    }

    DaggerRecipeListComponent.builder()
        .recipeRepositoryComponent(((BakingApp) getApplication()).getRecipeRepositoryComponent())
        .recipeListPresenterModule(new RecipeListPresenterModule(recipeListFragment))
        .build()
        .inject(this);
  }
}
