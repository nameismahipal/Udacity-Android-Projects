package www.androidcitizen.com.bakeit.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.RecipeClickListenerInterface;
import www.androidcitizen.com.bakeit.data.adapter.RecipeAdapter;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.data.remote.BakingInterface;

import www.androidcitizen.com.bakeit.databinding.FragmentRecipeListBinding;

import java.util.List;

/**
 * Created by Mahi on 27/08/18.
 * www.androidcitizen.com
 */

public class RecipeListFragment extends Fragment {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private RecipeAdapter recipeAdapter;
    private FragmentRecipeListBinding bakingListBinding = null;
    private Context context;

    RecipeClickListenerInterface recipeClickListenerInterface;

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
                    + " must implement RecipeClickListenerInterface");
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

        if(null == bakingListBinding) {

            bakingListBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_recipe_list, container, false);
        }

        return bakingListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupRecyclerView();

        loadBakingData();
    }

    private void setupRecyclerView() {
        recipeAdapter = new RecipeAdapter(context, recipeClickListenerInterface);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        bakingListBinding.recipeList.setLayoutManager(layoutManager);
        bakingListBinding.recipeList.setHasFixedSize(true);
        bakingListBinding.recipeList.setItemAnimator(new DefaultItemAnimator());
        bakingListBinding.recipeList.setAdapter(recipeAdapter);
    }

    private void loadBakingData() {

        Call<List<Recipe>> recipesCall = BakingInterface.getBakingService().getRecipes();

        recipesCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    if (null != response.body()) {
                        recipeAdapter.updateRecipes(response.body());
                    }
                } else {
                    Log.e(TAG, "response code = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

}
