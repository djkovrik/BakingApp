package com.sedsoftware.bakingapp.features.recipestep;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.sedsoftware.bakingapp.data.model.Step;
import java.util.List;

public class RecipeStepPageAdapter extends FragmentPagerAdapter {

  private List<Step> steps;

  public RecipeStepPageAdapter(FragmentManager fm, List<Step> steps) {
    super(fm);
    setSteps(steps);
  }

  public void setSteps(@NonNull List<Step> steps) {
    this.steps = steps;
    notifyDataSetChanged();
  }

  @Override
  public Fragment getItem(int position) {
    return RecipeStepSinglePageFragment.newInstance(
        steps.get(position).description(),
        steps.get(position).videoURL()
    );
  }

  @Override
  public int getCount() {
    return steps.size();
  }
}
