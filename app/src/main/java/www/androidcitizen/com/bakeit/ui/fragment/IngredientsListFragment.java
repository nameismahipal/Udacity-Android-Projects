package www.androidcitizen.com.bakeit.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.adapter.IngredientAdapter;
import www.androidcitizen.com.bakeit.data.model.Ingredient;

import static www.androidcitizen.com.bakeit.util.Constants.*;


public class IngredientsListFragment extends Fragment {

    private IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredients = null;
    private Context context;

    TextView title;
    RecyclerView recipeDetailsList;

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

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_list, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.title);
        recipeDetailsList = view.findViewById(R.id.recipeDetailsList);

        setupViews();

        setupRecyclerView();

        loadBakingData();
    }

    private void setupViews() {
        title.setText(R.string.ingredientsLabel);
        title.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        recipeDetailsList.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
    }

    private void setupRecyclerView() {
        ingredientAdapter = new IngredientAdapter(context);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        recipeDetailsList.setLayoutManager(layoutManager);
        recipeDetailsList.setHasFixedSize(true);
        recipeDetailsList.setItemAnimator(new DefaultItemAnimator());
        recipeDetailsList.setAdapter(ingredientAdapter);
    }

    private void loadBakingData() {
        ingredientAdapter.updateIngredients(ingredients);
    }

}