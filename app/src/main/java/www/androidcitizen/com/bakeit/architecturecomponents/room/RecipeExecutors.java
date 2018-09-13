package www.androidcitizen.com.bakeit.architecturecomponents.room;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Mahi on 12/09/18.
 * www.androidcitizen.com
 */

public class RecipeExecutors {

    private static final Object LOCK = new Object();
    private static RecipeExecutors executorInstance;
    private final Executor diskIO;
    private final Executor mainThread;

    private RecipeExecutors(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    public static RecipeExecutors getExecutorInstance() {

        if(null == executorInstance) {
            synchronized (LOCK) {
                executorInstance = new RecipeExecutors(Executors.newSingleThreadExecutor(),
                        new MainThreadExecutor());

            }
        }

        return executorInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
