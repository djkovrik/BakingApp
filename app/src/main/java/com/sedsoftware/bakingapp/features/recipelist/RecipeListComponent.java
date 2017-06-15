package com.sedsoftware.bakingapp.features.recipelist;

import com.sedsoftware.bakingapp.data.source.RecipeRepositoryComponent;
import com.sedsoftware.bakingapp.utils.FragmentScoped;
import dagger.Component;

@FragmentScoped
@Component(dependencies = RecipeRepositoryComponent.class, modules = RecipeListPresenterModule.class)
interface RecipeListComponent {

  void inject(RecipeListActivity recipeListActivity);
}
