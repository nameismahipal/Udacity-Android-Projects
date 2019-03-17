package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Mahi on 21/09/18.
 * www.androidcitizen.com
 */

@RunWith(AndroidJUnit4.class)
public class AsyncTaskNonStringTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void nonEmptyStringTest() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        JokesAsyncTask task = new JokesAsyncTask(
                new JokesAsyncTask.JokeCallback() {
                    @Override
                    public void onResponse(String response) {
                        assertNotNull(response);
                        signal.countDown();
                    }
                }
        );

        task.execute();
        signal.await();

    }

}