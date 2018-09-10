package www.androidcitizen.com.bakeit.architecturecomponents.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mahi on 10/09/18.
 * www.androidcitizen.com
 */

@Entity
public class RecipeEntity {

        @PrimaryKey
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("ingredients")
        @Expose
        private String ingredientsJson;

    public RecipeEntity(int id, String name, String ingredientsJson) {
        this.id = id;
        this.name = name;
        this.ingredientsJson = ingredientsJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredientsJson() {
        return ingredientsJson;
    }

    public void setIngredientsJson(String ingredientsJson) {
        this.ingredientsJson = ingredientsJson;
    }
}