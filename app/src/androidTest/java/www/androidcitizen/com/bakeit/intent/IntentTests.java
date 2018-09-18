package www.androidcitizen.com.bakeit.intent;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.ui.activity.MainActivity;
import www.androidcitizen.com.bakeit.ui.activity.RecipeDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by Mahi on 17/09/18.
 * www.androidcitizen.com
 */

@RunWith(AndroidJUnit4.class)
public class IntentTests {

    private IdlingResource idlingResource;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        idlingResource = intentsTestRule.getActivity().getIdlingResource();

        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregisterIdlingResource(){
        if(null != idlingResource) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    // Code taken from Intent Stubbing Code Example, Udacity Lesson 8 (Espresso)
    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void checkIntentRecipeDetailActivity() {
        onView(withId(R.id.recipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasComponent(RecipeDetailsActivity.class.getName()));
    }

}
