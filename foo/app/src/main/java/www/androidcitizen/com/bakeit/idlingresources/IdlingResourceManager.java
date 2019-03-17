package www.androidcitizen.com.bakeit.idlingresources;

import android.support.test.espresso.idling.CountingIdlingResource;

/**
 * Created by Mahi on 17/09/18.
 * www.androidcitizen.com
 */

public interface IdlingResourceManager {
    CountingIdlingResource getIdlingResource();
    void incrementIdlingResource();
    void decrementIdlingResource();
}
