package com.sedsoftware.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import com.sedsoftware.bakingapp.features.recipelist.RecipeListActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListActivityIntentTest {

  private static final String EXTRA_RECIPE_ID_KEY = "RECIPE_ID";
  private static final int EXTRA_RECIPE_ID_VALUE = 1;

  @Rule
  public IntentsTestRule<RecipeListActivity> intentsTestRule
      = new IntentsTestRule<>(RecipeListActivity.class);

  @Test
  public void clickOnRecyclerViewItem_runsRecipeDetailsActivityIntent() {

    onView(ViewMatchers.withId(R.id.recipe_list_recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    intended(
        hasExtra(EXTRA_RECIPE_ID_KEY, EXTRA_RECIPE_ID_VALUE)
    );
  }

}
