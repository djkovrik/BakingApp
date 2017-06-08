package com.sedsoftware.bakingapp.features.recipestep;

import com.sedsoftware.bakingapp.BasePresenter;
import com.sedsoftware.bakingapp.BaseView;
import com.sedsoftware.bakingapp.data.model.Step;
import java.util.List;

public class RecipeStepContract {

  interface View extends BaseView<Presenter> {

    void showStepsInViewpager(List<Step> steps);

    void showErrorMessage();
  }

  interface Presenter extends BasePresenter {

    void loadStepsToAdapter();
  }
}
