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
import www.androidcitizen.com.bakeit.data.custominterface.RecipeClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Ingredient;
import www.androidcitizen.com.bakeit.data.model.Recipe;
import www.androidcitizen.com.bakeit.databinding.FragmentRecipeListBinding;
import www.androidcitizen.com.bakeit.databinding.IngredientItemViewBinding;
import www.androidcitizen.com.bakeit.databinding.RecipeItemViewBinding;

/**
 * Created by Mahi on 27/08/18.
 * www.androidcitizen.com
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private IngredientItemViewBinding itemViewBinding;

    List<Ingredient> ingredients = null;

    Context context;

    public IngredientAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.ingredient_item_view, viewGroup, false);

        return new IngredientViewHolder(itemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder recipeViewHolder, int iPosition) {

        recipeViewHolder.onBind(iPosition);
    }

    @Override
    public int getItemCount() {
        if (null != ingredients)
            return ingredients.size();
        else
            return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private IngredientItemViewBinding itemViewBinding;

        public IngredientViewHolder(@NonNull IngredientItemViewBinding ingredientItemBind) {
            super(ingredientItemBind.getRoot());
            this.itemViewBinding = ingredientItemBind;
        }

        void onBind(int iPosition) {
            itemViewBinding.quantity.setText(String.valueOf(ingredients.get(iPosition).getQuantity()));
            itemViewBinding.measure.setText(ingredients.get(iPosition).getMeasure());
            itemViewBinding.ingredient.setText(context.getString(R.string.ingredient, ingredients.get(iPosition).getIngredient()));
        }

    }

    public void updateIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
}
