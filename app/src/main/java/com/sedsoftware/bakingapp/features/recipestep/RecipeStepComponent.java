package com.sedsoftware.bakingapp.features.recipestep;

import com.sedsoftware.bakingapp.data.source.RecipeRepositoryComponent;
import com.sedsoftware.bakingapp.utils.FragmentScoped;
import dagger.Component;

@FragmentScoped
@Component(dependencies = RecipeRepositoryComponent.class, modules = RecipeStepPresenterModule.class)
public interface RecipeStepComponent {

  void inject(RecipeStepActivity recipeStepActivity);
}
