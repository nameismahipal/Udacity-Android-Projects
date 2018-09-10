package www.androidcitizen.com.bakeit.architecturecomponents.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;

/**
 * Created by Mahi on 08/09/18.
 * www.androidcitizen.com
 */

@Dao
public interface RecipeEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(RecipeEntity recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<RecipeEntity> recipes);

    @Query("SELECT * FROM recipeentity WHERE id=:id")
    RecipeEntity getRecipe(int id);

    @Query("SELECT * FROM recipeentity")
    List<RecipeEntity> getAllRecipes();
}
