package com.sedsoftware.bakingapp.data.source.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sedsoftware.bakingapp.data.source.local.db.IngredientPersistenceContract.IngredientEntry;
import com.sedsoftware.bakingapp.data.source.local.db.RecipePersistenceContract.RecipeEntry;
import com.sedsoftware.bakingapp.data.source.local.db.StepPersistenceContract.StepEntry;

public class DbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "recipes.db";
  private static final int DATABASE_VERSION = 1;

  DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(IngredientPersistenceContract.SQL_QUERY_CREATE);
    db.execSQL(StepPersistenceContract.SQL_QUERY_CREATE);
    db.execSQL(RecipePersistenceContract.SQL_QUERY_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    db.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + StepEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME);
    onCreate(db);
  }
}
