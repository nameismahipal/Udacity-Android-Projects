package www.androidcitizen.com.bakeit.data.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.databinding.RecipeItemViewBinding;

/**
 * Created by Mahi on 27/08/18.
 * www.androidcitizen.com
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private RecipeItemViewBinding itemViewBinding;

    List<Recipe> recipes = null;

    Context context;

    public RecipeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.recipe_item_view, viewGroup, false);

        return new RecipeViewHolder(itemViewBinding);
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

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private RecipeItemViewBinding itemViewBinding;

        public RecipeViewHolder(@NonNull RecipeItemViewBinding recipeItemBind) {
            super(recipeItemBind.getRoot());
            this.itemViewBinding = recipeItemBind;
        }

        void onBind(int iPosition) {
            itemViewBinding.recipeName.setText(recipes.get(iPosition).getName());
            itemViewBinding.recipeServings.setText(context.getString(R.string.servings, recipes.get(iPosition).getServings()));
        }


    }

    public void updateRecipes(List<Recipe> newRecipes){
        recipes = newRecipes;
        notifyDataSetChanged();
    }

    public void clearData(){
        while (recipes.size() > 0) {
            removeItem(recipes.get(0));
        }
    }

    private void removeItem(Recipe recipe) {
        int index = recipes.indexOf(recipe);
        recipes.remove(index);
        notifyItemChanged(index);
    }
}
