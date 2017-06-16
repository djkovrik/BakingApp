package com.sedsoftware.bakingapp;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.sedsoftware.bakingapp.features.recipelist.RecipeListActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListActivityUITest {

  @Rule
  public ActivityTestRule<RecipeListActivity> activityTestRule =
      new ActivityTestRule<>(RecipeListActivity.class);

  private IdlingResource idlingResource;

  @Before
  public void registerIdlingResource() {
    idlingResource = activityTestRule.getActivity().getIdlingResource();
    Espresso.registerIdlingResources(idlingResource);
  }

  @Test
  public void clickOnRefreshMenuItem_showsSuccessfulSyncMessage() {

    // Click on Refresh menu item
    openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
    onView(withText(R.string.action_refresh))
        .perform(click());

    // Check if successful sync message displayed
    onView(withText(R.string.recipe_list_sync_completed)).
        inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).
        check(matches(isDisplayed()));
  }

  @Test
  public void clickOnRecyclerViewItem_opensRecipeDetailsActivity() {

    onView(withId(R.id.recipe_list_recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

    onView(withId(R.id.recipe_details_ingredients))
        .check(matches(isDisplayed()));
  }

  @After
  public void unregisterIdlingResource() {
    if (idlingResource != null) {
      Espresso.unregisterIdlingResources(idlingResource);
    }
  }
}
