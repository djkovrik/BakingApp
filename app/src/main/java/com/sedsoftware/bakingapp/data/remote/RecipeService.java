package com.sedsoftware.bakingapp.data.remote;

import com.sedsoftware.bakingapp.data.model.Recipe;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;

public interface RecipeService {

  String ENDPOINT = "https://go.udacity.com";

  @GET("/android-baking-app-json")
  Observable<List<Recipe>> loadRecipesFromServer();

}
