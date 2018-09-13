package www.androidcitizen.com.bakeit.architecturecomponents.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;


/**
 * Created by Mahi on 08/09/18.
 * www.androidcitizen.com
 *
 * Re-used from Udacity Lesson.
 *
 */

@Database(entities = {RecipeEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();

    private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "recipe_list";

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {

        if(sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public abstract RecipeEntityDao recipeEntityDao();
}
