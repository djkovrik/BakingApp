package com.sedsoftware.bakingapp.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.List;

@AutoValue
public abstract class Recipe {
  public abstract int id();
  public abstract String name();
  public abstract List<Ingredient> ingredients();
  public abstract List<Step> steps();
  public abstract int servings();
  public abstract String image();

  public static Builder builder() {
    return new AutoValue_Recipe.Builder();
  }

  public static TypeAdapter<Recipe> typeAdapter(Gson gson) {
    return new AutoValue_Recipe.GsonTypeAdapter(gson);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder id(int id);
    public abstract Builder name(String name);
    public abstract Builder ingredients(List<Ingredient> ingredients);
    public abstract Builder steps(List<Step> steps);
    public abstract Builder servings(int servings);
    public abstract Builder image(String image);

    public abstract Recipe build();
  }
}
