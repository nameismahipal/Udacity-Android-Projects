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

            JSONObject nameObject = jsonObject.getJSONObject(NAME);
            String name = nameObject.getString(MAIN_NAME);
            if(null != name) sandwich.setMainName(name);

            JSONArray psudoNameArray = nameObject.getJSONArray(ALSO_KNOWNAS);
            sandwich.setAlsoKnownAs(parseJsonStringArray(psudoNameArray));

            String placeOfOrigin = jsonObject.getString(ORIGIN_PLACE);
            if(placeOfOrigin.equals("") || TextUtils.isEmpty(placeOfOrigin)){
                placeOfOrigin = "Unknown";
            }
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            String description = jsonObject.getString(DESCRIPTION);
            if(null != description) sandwich.setDescription(description);

            String image = jsonObject.getString(IMAGE);
            if(null != image) sandwich.setImage(image);

            JSONArray ingrediants = jsonObject.getJSONArray(INGREDIENTS);
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
            names.add(jsonArrayInput.getString(i));
        }

        return names;
    }
}
