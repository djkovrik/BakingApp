package com.sedsoftware.bakingapp.data.source.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "recipes.db";
  public static final int DATABASE_VERSION = 1;

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(StepPersistenceContract.SQL_QUERY_CREATE);
    db.execSQL(IngredientPersistenceContract.SQL_QUERY_CREATE);
    db.execSQL(RecipePersistenceContract.SQL_QUERY_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    db.execSQL("DROP TABLE IF EXISTS " + RecipePersistenceContract.RecipeEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + IngredientPersistenceContract.IngredientEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + StepPersistenceContract.StepEntry.TABLE_NAME);
    onCreate(db);
  }
}
