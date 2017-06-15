package com.sedsoftware.bakingapp.features.recipedetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.model.Step;
import java.util.List;
import java.util.Locale;

class RecipeDetailsAdapter extends
    RecyclerView.Adapter<RecipeDetailsAdapter.StepViewHolder> {

  private List<Step> stepsList;
  final OnStepClickListener recipeClickListener;

  int currentPos;

  RecipeDetailsAdapter(List<Step> steps, OnStepClickListener listener) {
    setSteps(steps);
    recipeClickListener = listener;
  }

  @Override
  public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_recipe_details_item, parent, false);

    return new StepViewHolder(view);
  }

  @Override
  public void onBindViewHolder(StepViewHolder holder, int position) {
    holder.bindTo(stepsList.get(position), position);
  }

  @Override
  public int getItemCount() {
    return stepsList.size();
  }

  @Override
  public long getItemId(int position) {
    return stepsList.get(position).id();
  }

  void refreshStepsList(List<Step> steps) {
    setSteps(steps);
    notifyDataSetChanged();
  }

  private void setSteps(@NonNull List<Step> steps) {
    stepsList = steps;
  }

  class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.list_step_layout)
    RelativeLayout stepItemLayout;
    @BindView(R.id.list_step_description)
    TextView stepDescription;
    @BindView(R.id.list_step_video_icon)
    ImageView videoIcon;
    @BindColor(R.color.colorGrayBackground)
    int normalItemBackground;
    @BindColor(R.color.colorPrimaryLight)
    int currentItemBackground;

    private int currentId;

    StepViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(this);
    }

    void bindTo(@NonNull Step step, int bindPosition) {

      currentId = step.id();

      String description = step.shortDescription();
      stepDescription.setText(String.format(Locale.US, "%d. %s", currentId, description));

      String video = step.videoURL();

      if (video.isEmpty()) {
        videoIcon.setVisibility(View.INVISIBLE);
      } else {
        videoIcon.setVisibility(View.VISIBLE);
      }

      if (currentPos == bindPosition) {
        stepItemLayout.setBackgroundColor(currentItemBackground);
      } else {
        stepItemLayout.setBackgroundColor(normalItemBackground);
      }
    }

    @Override
    public void onClick(View v) {
      currentPos = currentId;
      recipeClickListener.stepClicked(currentId);
      notifyDataSetChanged();
    }
  }

  interface OnStepClickListener {

    void stepClicked(int stepId);
  }
}

