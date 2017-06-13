package com.sedsoftware.bakingapp.features.widget;

import com.sedsoftware.bakingapp.BakingAppModule;
import com.sedsoftware.bakingapp.data.source.local.prefs.PreferencesModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {BakingAppModule.class, PreferencesModule.class})
public interface IngredientsWidgetComponent {

  void inject(IngredientsWidgetConfigurationActivity activity);
}
