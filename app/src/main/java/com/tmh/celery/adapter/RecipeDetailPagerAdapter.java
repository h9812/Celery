package com.tmh.celery.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tmh.celery.fragment.RecipeDirectionsFragment;
import com.tmh.celery.fragment.RecipeIngredientsFragment;
import com.tmh.celery.fragment.RecipeIntroFragment;
import com.tmh.celery.model.Recipe;

public class RecipeDetailPagerAdapter extends FragmentPagerAdapter {
    private int numberOfFragments;
    private Recipe recipe;
    public RecipeDetailPagerAdapter(FragmentManager fragmentManager, int numberOfFragments, Recipe recipe) {
        super(fragmentManager);
        this.numberOfFragments = numberOfFragments;
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RecipeIntroFragment.newInstance(recipe);
            case 1:
                return RecipeIngredientsFragment.newInstance(recipe);
            case 2:
                return RecipeDirectionsFragment.newInstance(recipe);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfFragments;
    }
}
