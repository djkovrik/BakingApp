package com.sedsoftware.bakingapp.data.source.local.db;

public class RecipePersistenceContract {

  private RecipePersistenceContract() {
  }

  public static abstract class RecipeEntry {

    public static final String TABLE_NAME = "recipes";

    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SERVINGS = "servings";
    public static final String COLUMN_IMAGE = "image";
  }

  public static final String SQL_QUERY_CREATE =
      "CREATE TABLE " + RecipeEntry.TABLE_NAME + " ("
          + RecipeEntry.COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY,"
          + RecipeEntry.COLUMN_NAME + " TEXT NOT NULL,"
          + RecipeEntry.COLUMN_SERVINGS + " INTEGER NOT NULL,"
          + RecipeEntry.COLUMN_IMAGE + " TEXT NOT NULL,"
          + ")";
}
