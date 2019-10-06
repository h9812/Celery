package com.tmh.celery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tmh.celery.R;
import com.tmh.celery.model.Recipe;

import java.util.List;

public class AllRecipesAdapter extends RecyclerView.Adapter<AllRecipesAdapter.RecipeViewHolder>{
    private List<Recipe> recipes;
    private OnItemPressedListener mListener;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView textId;
        public TextView textName;
        public TextView textAuthor;
        public RecipeViewHolder(View v) {
            super(v);
            itemView = v;
            textId = itemView.findViewById(R.id.textRecipeId);
            textName = itemView.findViewById(R.id.textRecipeName);
            textAuthor = itemView.findViewById(R.id.textRecipeAuthor);
        }
    }

    public AllRecipesAdapter(List<Recipe> recipes, OnItemPressedListener listener) {
        this.recipes = recipes;
        this.mListener = listener;
    }

    @Override
    public AllRecipesAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_recipes_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder viewHolder, int position) {
        final Recipe currentRecipe = recipes.get(position);
        viewHolder.textId.setText(currentRecipe.getId());
        viewHolder.textName.setText(currentRecipe.getName());
        viewHolder.textAuthor.setText(currentRecipe.getOwnerId());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemPressed(currentRecipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface OnItemPressedListener {
        void onItemPressed(Recipe recipe);
    }
}
