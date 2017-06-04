package com.sedsoftware.bakingapp.data.source.local.prefs;

import android.content.Context;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class PreferencesModule {

  @Singleton
  @Provides
  @NonNull
  PreferencesHelper providePreferencesHelper(Context context) {
    return new PreferencesHelper(context);
  }

}
