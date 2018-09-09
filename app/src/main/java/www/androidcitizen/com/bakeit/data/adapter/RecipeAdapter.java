package www.androidcitizen.com.bakeit.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.RecipeClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Recipe;

/**
 * Created by Mahi on 27/08/18.
 * www.androidcitizen.com
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private List<Recipe> recipes = null;

    private final Context context;
    private final RecipeClickListenerInterface recipeListenerInterface;

    public RecipeAdapter(Context context, RecipeClickListenerInterface recipeListenerInterface) {
        this.context = context;
        this.recipeListenerInterface = recipeListenerInterface;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_item_view, viewGroup, false);

        return new RecipeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int iPosition) {

        recipeViewHolder.onBind(iPosition);
    }

    @Override
    public int getItemCount() {
        if (null != recipes)
            return recipes.size();
        else
            return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        MaterialCardView cardView;
        TextView recipeName;
        TextView recipeServings;

        RecipeViewHolder(@NonNull View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            recipeName = view.findViewById(R.id.recipeName);
            recipeServings = view.findViewById(R.id.recipeServings);

            cardView.setOnClickListener(this);
        }

        void onBind(int iPosition) {
            recipeName.setText(recipes.get(iPosition).getName());
            recipeServings.setText(context.getString(R.string.servings, recipes.get(iPosition).getServings()));
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            recipeListenerInterface.onRecipeSelected(recipes.get(index));
        }
    }

    public void updateRecipes(List<Recipe> newRecipes){
        recipes = newRecipes;
        notifyDataSetChanged();
    }

}
