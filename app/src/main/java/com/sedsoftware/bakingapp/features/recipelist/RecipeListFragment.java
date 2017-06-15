package com.sedsoftware.bakingapp.features.recipelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.model.Recipe;
import com.sedsoftware.bakingapp.features.recipedetails.RecipeDetailsActivity;
import java.util.ArrayList;
import java.util.List;


public class RecipeListFragment extends Fragment implements RecipeListContract.View {

  @BindView(R.id.recipe_list_recycler_view)
  RecyclerView recipeListRecyclerView;
  @BindView(R.id.recipe_list_progress_bar)
  ProgressBar recipeListProgressBar;

  @BindInt(R.integer.grid_column_count)
  int gridColumnCount;
  @BindString(R.string.recipe_list_sync_completed)
  String syncCompletedMessage;
  @BindString(R.string.recipe_list_connection_error)
  String connectionErrorMessage;

  Unbinder unbinder;

  private RecipeListContract.Presenter recipeListPresenter;
  private RecipeListAdapter recipeListAdapter;

  public RecipeListFragment() {
  }

  public static RecipeListFragment newInstance() {
    return new RecipeListFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
    unbinder = ButterKnife.bind(this, view);

    recipeListAdapter = new RecipeListAdapter(
        new ArrayList<>(0),
        recipeId -> recipeListPresenter.openRecipeDetails(recipeId)
    );

    recipeListAdapter.setHasStableIds(true);

    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), gridColumnCount);
    recipeListRecyclerView.setLayoutManager(layoutManager);
    recipeListRecyclerView.setHasFixedSize(true);
    recipeListRecyclerView.setAdapter(recipeListAdapter);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    recipeListPresenter.subscribe();
  }

  @Override
  public void onPause() {
    super.onPause();
    recipeListPresenter.unsubscribe();
  }

  @Override
  public void setPresenter(@NonNull RecipeListContract.Presenter presenter) {
    this.recipeListPresenter = presenter;
  }

  @Override
  public void showRecipeList(List<Recipe> recipeList) {
    recipeListAdapter.refreshRecipeList(recipeList);
  }

  @Override
  public void showLoadingIndicator(boolean show) {
    setViewVisibility(recipeListRecyclerView, !show);
    setViewVisibility(recipeListProgressBar, show);
  }

  @Override
  public void showCompletedMessage() {
    Toast.makeText(getContext(), syncCompletedMessage, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showErrorMessage() {
    Toast.makeText(getContext(), connectionErrorMessage, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showRecipeDetails(int recipeId) {
    startActivity(RecipeDetailsActivity.prepareIntent(getContext(), recipeId));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void setViewVisibility(View view, boolean visible) {
    if (visible) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.INVISIBLE);
    }
  }
}
