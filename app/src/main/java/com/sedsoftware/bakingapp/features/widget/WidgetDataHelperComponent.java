package com.sedsoftware.bakingapp.features.widget;

import com.sedsoftware.bakingapp.BakingAppModule;
import com.sedsoftware.bakingapp.data.source.RecipeRepositoryComponent;
import com.sedsoftware.bakingapp.utils.ProviderScoped;
import dagger.Component;

@ProviderScoped
@Component(dependencies = RecipeRepositoryComponent.class, modules = BakingAppModule.class)
interface WidgetDataHelperComponent {

  void inject(WidgetProvider provider);
  void inject(WidgetConfigurationActivity activity);
}
