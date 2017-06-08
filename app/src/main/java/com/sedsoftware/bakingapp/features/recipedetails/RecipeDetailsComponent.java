package com.sedsoftware.bakingapp.features.recipedetails;

import com.sedsoftware.bakingapp.data.source.RecipeRepositoryComponent;
import com.sedsoftware.bakingapp.utils.FragmentScoped;
import dagger.Component;

@FragmentScoped
@Component(dependencies = RecipeRepositoryComponent.class, modules = RecipeDetailsPresenterModule.class)
public interface RecipeDetailsComponent {

  void inject(RecipeDetailsActivity recipeDetailsActivity);
}
