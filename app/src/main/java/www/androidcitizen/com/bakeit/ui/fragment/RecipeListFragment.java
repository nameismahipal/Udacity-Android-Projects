package www.androidcitizen.com.bakeit.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.architecturecomponents.RecipeRepository;
import www.androidcitizen.com.bakeit.data.custominterface.RecipeClickListenerInterface;
import www.androidcitizen.com.bakeit.data.adapter.RecipeAdapter;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.data.remote.BakingInterface;

import java.util.List;

/**
 * Created by Mahi on 27/08/18.
 * www.androidcitizen.com
 */

public class RecipeListFragment extends Fragment {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private RecipeAdapter recipeAdapter;
    private Context context;
    RecyclerView recipeList;

    private RecipeClickListenerInterface recipeClickListenerInterface;

    // TODO: Refactor
    RecipeRepository recipeRepository;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public RecipeListFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.context = context;
        }

        try {
            recipeClickListenerInterface = (RecipeClickListenerInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onRecipeSelected Interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.recipeClickListenerInterface = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recipeList = view.findViewById(R.id.recipeList);

        recipeRepository = new RecipeRepository(context);

        setupRecyclerView();

        loadBakingData();
    }

    private void setupRecyclerView() {
        recipeAdapter = new RecipeAdapter(context, recipeClickListenerInterface);

        if(getResources().getBoolean(R.bool.is_tablet)) {
            GridLayoutManager layoutManager = new GridLayoutManager(context, 3);

            recipeList.setLayoutManager(layoutManager);

        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);

            recipeList.setLayoutManager(layoutManager);
        }

        recipeList.setHasFixedSize(true);
        recipeList.setItemAnimator(new DefaultItemAnimator());
        recipeList.setAdapter(recipeAdapter);
    }


    private void loadBakingData() {

        recipeRepository.fetchBakingDataFromServer(new RecipeRepository.RecipeRepoCallback() {
            @Override
            public void onResponse(List<Recipe> recipes) {
                recipeAdapter.updateRecipes(recipes);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

}
