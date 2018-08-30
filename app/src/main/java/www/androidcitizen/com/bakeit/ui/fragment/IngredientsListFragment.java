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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.adapter.IngredientAdapter;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.databinding.FragmentIngredientsListBinding;

import static www.androidcitizen.com.bakeit.util.Constants.*;


public class IngredientsListFragment extends Fragment {

    FragmentIngredientsListBinding ingredientsListBinding;
    IngredientAdapter ingredientAdapter;
    List<Ingredient> ingredients = null;
    Context context;

    public IngredientsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            this.context = context;
        }
    }

    public static IngredientsListFragment newInstance(List<Ingredient> ingredients) {

        IngredientsListFragment fragment = new IngredientsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(INGREDIENTS_KEY, (ArrayList<Ingredient>) ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.ingredients = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null == ingredientsListBinding) {

            ingredientsListBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_ingredients_list, container, false);
        }

        return ingredientsListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();

        loadBakingData();
    }

    private void setupRecyclerView() {
        ingredientAdapter = new IngredientAdapter(context);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        ingredientsListBinding.ingredientsList.setLayoutManager(layoutManager);
        ingredientsListBinding.ingredientsList.setHasFixedSize(true);
        ingredientsListBinding.ingredientsList.setItemAnimator(new DefaultItemAnimator());
        ingredientsListBinding.ingredientsList.setAdapter(ingredientAdapter);
    }

    private void loadBakingData() {
        ingredientAdapter.updateIngredients(ingredients);
    }

}