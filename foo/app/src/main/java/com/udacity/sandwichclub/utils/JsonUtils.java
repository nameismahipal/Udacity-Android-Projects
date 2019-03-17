package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException{

        final String NAME           = "name";
        final String MAIN_NAME      = "mainName";
        final String ALSO_KNOWNAS   = "alsoKnownAs";
        final String ORIGIN_PLACE   = "placeOfOrigin";
        final String DESCRIPTION    = "description";
        final String IMAGE          = "image";
        final String INGREDIENTS    = "ingredients";

        Sandwich sandwich = new Sandwich();

        JSONObject jsonObject = new JSONObject(json);

        if(null != jsonObject) {

            JSONObject nameObject = jsonObject.optJSONObject(NAME);
            String name = nameObject.optString(MAIN_NAME);
            if(!TextUtils.isEmpty(name)) sandwich.setMainName(name);

            JSONArray psudoNameArray = nameObject.optJSONArray(ALSO_KNOWNAS);
            if (null != psudoNameArray) sandwich.setAlsoKnownAs(parseJsonStringArray(psudoNameArray));

            String placeOfOrigin = jsonObject.optString(ORIGIN_PLACE);
            if(TextUtils.isEmpty(placeOfOrigin)){
                placeOfOrigin = "Unknown";
            } else {
                sandwich.setPlaceOfOrigin(placeOfOrigin);
            }

            String description = jsonObject.optString(DESCRIPTION);
            if(!TextUtils.isEmpty(description)) {
                sandwich.setDescription(description);
            } else {
                sandwich.setDescription("NA");
            }

            String image = jsonObject.optString(IMAGE);
            if(null != image) sandwich.setImage(image);

            JSONArray ingrediants = jsonObject.optJSONArray(INGREDIENTS);
            if(null != ingrediants) sandwich.setIngredients(parseJsonStringArray(ingrediants));
        }
        else {
            sandwich = null;
        }

        return sandwich;
    }


    private static List<String> parseJsonStringArray (JSONArray jsonArrayInput) throws JSONException{
        List<String> names = new ArrayList<>();

        for(int i = 0; i< jsonArrayInput.length(); i++) {
            names.add(jsonArrayInput.optString(i));
        }

        return names;
    }
}
