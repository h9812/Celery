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

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textAuthor;
        public RecipeViewHolder(View v) {
            super(v);
            textName = v.findViewById(R.id.textRecipeName);
            textAuthor = v.findViewById(R.id.textRecipeAuthor);
        }
    }

    public AllRecipesAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public AllRecipesAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_recipes_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder viewHolder, int position) {
        viewHolder.textName.setText(recipes.get(position).getName());
        viewHolder.textAuthor.setText(recipes.get(position).getOwnerId());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
