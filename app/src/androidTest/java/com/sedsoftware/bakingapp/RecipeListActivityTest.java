package com.sedsoftware.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.sedsoftware.bakingapp.features.recipelist.RecipeListActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListActivityTest {

  @Rule
  public ActivityTestRule<RecipeListActivity> activityTestRule =
      new ActivityTestRule<>(RecipeListActivity.class);

  @Test
  public void clickOnRecyclerViewItem_opensRecipeDetailsActivity() {

    onView(ViewMatchers.withId(R.id.recipe_list_recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

    onView(ViewMatchers.withId(R.id.recipe_details_ingredients))
        .check(matches(isDisplayed()));
  }
}
