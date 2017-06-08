package com.sedsoftware.bakingapp.features.recipestep.viewpagerdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.sedsoftware.bakingapp.R;

public class RecipeStepSinglePageFragment extends Fragment {

  public static final String EXTRA_DESCRIPTION_ID = "EXTRA_DESCRIPTION_ID";
  @BindView(R.id.recipe_step_id)
  TextView recipeStepId;
  Unbinder unbinder;

  public static RecipeStepSinglePageFragment newInstance(String description) {
    Bundle arguments = new Bundle();
    arguments.putString(EXTRA_DESCRIPTION_ID, description);
    RecipeStepSinglePageFragment fragment = new RecipeStepSinglePageFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  public RecipeStepSinglePageFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recipe_step_page, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    String currentDescription = getArguments().getString(EXTRA_DESCRIPTION_ID);
    recipeStepId.setText(currentDescription);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
