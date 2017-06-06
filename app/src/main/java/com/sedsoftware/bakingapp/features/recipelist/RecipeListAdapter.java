package com.sedsoftware.bakingapp.features.recipelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.model.Recipe;
import java.util.List;

class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

  private List<Recipe> recipeList;

  RecipeListAdapter(List<Recipe> recipes) {
    setRecipes(recipes);
  }

  @Override
  public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_recipe_list_item, parent, false);

    return new RecipeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecipeViewHolder holder, int position) {
    holder.bindTo(recipeList.get(position));
  }

  @Override
  public int getItemCount() {
    return recipeList.size();
  }

  @Override
  public long getItemId(int position) {
    return recipeList.get(position).id();
  }

  void refreshRecipeList(List<Recipe> recipes) {
    setRecipes(recipes);
    notifyDataSetChanged();
  }

  private void setRecipes(@NonNull List<Recipe> recipes) {
    recipeList = recipes;
  }

  class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_recipe_name)
    TextView recipeName;

    RecipeViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bindTo(@NonNull Recipe recipe) {
      String name = recipe.name();
      recipeName.setText(name);
    }
  }
}
