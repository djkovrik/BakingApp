package com.sedsoftware.bakingapp.features.recipedetails;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.model.Step;
import com.sedsoftware.bakingapp.features.recipestep.RecipeStepActivity;
import com.sedsoftware.bakingapp.features.recipestep.RecipeStepSinglePageFragment;
import com.sedsoftware.bakingapp.utils.FragmentUtils;
import com.sedsoftware.bakingapp.utils.StringUtils;
import com.sedsoftware.bakingapp.utils.TextViewUtils;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsContract.View {

  @BindView(R.id.recipe_details_ingredients)
  TextView recipeDetailsIngredients;
  @BindView(R.id.recipe_details_steps)
  RecyclerView recipeDetailsRecyclerView;

  @BindBool(R.bool.two_pane_mode)
  boolean twoPaneMode;
  @BindString(R.string.loading_data_error)
  String errorMessage;
  @BindString(R.string.recipe_details_ingredients_header)
  String ingredientsListHeader;

  Unbinder unbinder;

  private RecipeDetailsContract.Presenter recipeDetailsPresenter;
  private RecipeDetailsAdapter recipeDetailsAdapter;
  private int recipeId;

  public RecipeDetailsFragment() {
  }

  public static RecipeDetailsFragment newInstance(int recipeId) {
    Bundle arguments = new Bundle();
    arguments.putInt(RecipeDetailsActivity.EXTRA_RECIPE_ID, recipeId);
    RecipeDetailsFragment fragment = new RecipeDetailsFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    recipeId = getArguments().getInt(RecipeDetailsActivity.EXTRA_RECIPE_ID);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
    unbinder = ButterKnife.bind(this, view);

    recipeDetailsAdapter = new RecipeDetailsAdapter(new ArrayList<>(0),
        stepId -> recipeDetailsPresenter.openStepDetails(stepId));

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recipeDetailsRecyclerView.setLayoutManager(layoutManager);
    recipeDetailsRecyclerView.setHasFixedSize(true);
    recipeDetailsRecyclerView.setAdapter(recipeDetailsAdapter);

    recipeDetailsRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    if (twoPaneMode) {
      recipeDetailsPresenter.fetchStepData(0);
    }

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    recipeDetailsPresenter.subscribe();
  }

  @Override
  public void onPause() {
    super.onPause();
    recipeDetailsPresenter.unsubscribe();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void setPresenter(RecipeDetailsContract.Presenter presenter) {
    this.recipeDetailsPresenter = presenter;
  }

  @Override
  public void showIngredientsList(List<Ingredient> ingredients) {

    StringBuilder sb = new StringBuilder();
    sb.append(ingredientsListHeader);

    for (Ingredient ingredient : ingredients) {

      String name = ingredient.ingredient();
      float quantity = ingredient.quantity();
      String measure = ingredient.measure();

      sb.append("\n");
      sb.append(StringUtils.formatIngdedient(getContext(), name, quantity, measure));
    }

    TextViewUtils.setTextWithSpan(recipeDetailsIngredients, sb.toString(), ingredientsListHeader,
        new StyleSpan(Typeface.BOLD));
  }

  @Override
  public void showStepsList(List<Step> steps) {
    recipeDetailsAdapter.refreshStepsList(steps);
  }

  @Override
  public void showErrorMessage() {
    // User should not see this
    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showRecipeNameInActivityTitle(String recipeName) {
    getActivity().setTitle(recipeName);
  }

  @Override
  public void showStepDetails(int stepId) {

    if (twoPaneMode) {
      recipeDetailsPresenter.fetchStepData(stepId);
    } else {
      startActivity(RecipeStepActivity.prepareIntent(getContext(), recipeId, stepId));
    }
  }

  @Override
  public void refreshStepContainerFragment(String desc, String videoUrl, String imageUrl) {

    RecipeStepSinglePageFragment fragment =
        RecipeStepSinglePageFragment.newInstance(desc, videoUrl, imageUrl);

    FragmentUtils.replaceFragmentIn(
        getChildFragmentManager(),
        fragment,
        R.id.recipe_step_container);
  }
}
