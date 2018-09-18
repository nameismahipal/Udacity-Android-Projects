package www.androidcitizen.com.bakeit.activity;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.ui.activity.MainActivity;
import www.androidcitizen.com.bakeit.ui.activity.RecipeDetailsActivity;
import www.androidcitizen.com.bakeit.ui.fragment.IngredientsListFragment;
import www.androidcitizen.com.bakeit.ui.fragment.StepsListFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class ActivityTests {

    private IdlingResource idlingResource;

    private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), RecipeDetailsActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Rule
    public ActivityTestRule<RecipeDetailsActivity> recipeDetailsTestRule = new ActivityTestRule<>(RecipeDetailsActivity.class, false, false);

    @Before
    public void registerIdlingResource(){
        idlingResource = activityTestRule.getActivity().getIdlingResource();

        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregisterIdlingResource(){
        if(null != idlingResource) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    @Test
    public void recipeTests() {
        //Views are displayed
        onView(withId(R.id.recipeListFragment)).check(matches(isDisplayed()));

        // Scroll to * position, and verify the displayed name.
        onView(withId(R.id.recipeList)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));

    }

    @Test
    public void recipeIngredientStepTests() {
        onView(withId(R.id.recipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        ViewInteraction ingredientTextView = onView(
                allOf(withId(R.id.title), withText("Ingredients :"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipeListContainer),
                                        0),
                                0),
                        isDisplayed()));
        ingredientTextView.check(matches(withText("Ingredients :")));

        ViewInteraction stepTextView = onView(
                allOf(withId(R.id.title), withText("Steps :"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.stepsListContainer),
                                        0),
                                0),
                        isDisplayed()));
        stepTextView.check(matches(withText("Steps :")));


        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recipeDetailsList),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.stepsListContainer),
                                        0),
                                1), isDisplayed()));

        recyclerView2.perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("1 : Starting prep")).check(matches(isDisplayed()));

        recyclerView2.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.placeholderImage)).check(matches(isDisplayed()));
        onView(withId(R.id.prevStep)).check(matches(isDisplayed()));
        ViewInteraction nextBtn = onView(withId(R.id.nextStep)).check(matches(isDisplayed()));
        nextBtn.perform(click());
        onView(withId(R.id.playerView)).check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
