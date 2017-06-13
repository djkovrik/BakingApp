package com.sedsoftware.bakingapp.data.source.local.prefs;

import android.content.Context;
import android.support.annotation.NonNull;
import com.sedsoftware.bakingapp.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class PreferencesModule {

  @Singleton
  @Provides
  @NonNull
  PreferencesHelper providePreferencesHelper(@ApplicationContext Context context) {
    return new PreferencesHelper(context);
  }

}
