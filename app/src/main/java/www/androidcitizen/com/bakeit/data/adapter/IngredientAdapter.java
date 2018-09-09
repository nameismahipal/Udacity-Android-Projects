package www.androidcitizen.com.bakeit.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.model.Ingredient;

/**
 * Created by Mahi on 27/08/18.
 * www.androidcitizen.com
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private List<Ingredient> ingredients = null;

    private final Context context;

    public IngredientAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ingredient_item_view, viewGroup, false);

        return new IngredientViewHolder(rootView);
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

        TextView quantity;
        TextView measure;
        TextView ingredient;

        IngredientViewHolder(@NonNull View view) {
            super(view);
            quantity = view.findViewById(R.id.quantity);
            measure = view.findViewById(R.id.measure);
            ingredient = view.findViewById(R.id.ingredient);
        }

        void onBind(int iPosition) {
            quantity.setText(String.valueOf(ingredients.get(iPosition).getQuantity()));
            measure.setText(ingredients.get(iPosition).getMeasure());
            ingredient.setText(context.getString(R.string.ingredient, ingredients.get(iPosition).getIngredient()));
        }

    }

    public void updateIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
}
