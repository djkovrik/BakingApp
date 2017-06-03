package com.sedsoftware.bakingapp.data.source.local.db;

import android.content.Context;
import android.support.annotation.NonNull;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import rx.Scheduler;
import rx.schedulers.Schedulers;

// RxJava1 stuff used here because of SqlBrite
@Module
public class DbModule {

  @Singleton
  @Provides
  @NonNull
  BriteDatabase provideBriteDatabase(SqlBrite sqlBrite, DbHelper dbHelper, Scheduler scheduler) {
    return sqlBrite.wrapDatabaseHelper(dbHelper, scheduler);
  }

  @Singleton
  @Provides
  @NonNull
  SqlBrite provideSqlBrite() {
    return new SqlBrite.Builder().build();
  }

  @Singleton
  @Provides
  @NonNull
  DbHelper provideDbHelper(Context context) {
    return new DbHelper(context);
  }

  @Provides
  @NonNull
  Scheduler provideScheduler() {
    return Schedulers.io();
  }
}
