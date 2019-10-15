package com.tmh.celery.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tmh.celery.R;
import com.tmh.celery.model.Recipe;

import java.util.List;

public class AllRecipesAdapter extends RecyclerView.Adapter<AllRecipesAdapter.RecipeViewHolder>{
    private List<Recipe> recipes;
    private OnItemPressedListener mListener;
    private Activity activity;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView textId;
        public TextView textName;
        public TextView textAuthor;
        public ImageView imageRecipe;
        public RecipeViewHolder(View v) {
            super(v);
            itemView = v;
            textId = itemView.findViewById(R.id.textRecipeId);
            textName = itemView.findViewById(R.id.textRecipeName);
            textAuthor = itemView.findViewById(R.id.textRecipeAuthor);
            imageRecipe = itemView.findViewById(R.id.imageRecipe);
        }
    }

    public AllRecipesAdapter(List<Recipe> recipes, OnItemPressedListener listener, Activity activity) {
        this.recipes = recipes;
        this.mListener = listener;
        this.activity = activity;
    }

    @Override
    public AllRecipesAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
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
        Glide.with(activity)
                .load(recipes.get(position).getImageUrl())
                .into(viewHolder.imageRecipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface OnItemPressedListener {
        void onItemPressed(Recipe recipe);
    }
}
