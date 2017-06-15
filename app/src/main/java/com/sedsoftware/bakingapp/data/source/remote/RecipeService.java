package com.sedsoftware.bakingapp.data.source.remote;

import com.sedsoftware.bakingapp.data.model.Recipe;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;

public interface RecipeService {

  String ENDPOINT = "https://d17h27t6h515a5.cloudfront.net";

  @GET("/topher/2017/May/59121517_baking/baking.json")
  Observable<List<Recipe>> loadRecipesFromServer();

}
