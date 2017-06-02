package com.sedsoftware.bakingapp.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Ingredient {
  public abstract int quantity();
  public abstract String measure();
  public abstract String ingredient();

  public static Builder builder() {
    return new AutoValue_Ingredient.Builder();
  }

  public static TypeAdapter<Ingredient> typeAdapter(Gson gson) {
    return new AutoValue_Ingredient.GsonTypeAdapter(gson);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder quantity(int quantity);
    public abstract Builder measure(String measure);
    public abstract Builder ingredient(String ingredient);

    public abstract Ingredient build();
  }
}