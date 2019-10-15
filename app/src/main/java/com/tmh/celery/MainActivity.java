package com.tmh.celery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tmh.celery.fragment.HomeFragment;
import com.tmh.celery.fragment.NewRecipeFragment;
import com.tmh.celery.fragment.RecipeDetailFragment;
import com.tmh.celery.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private final static String userId = new String("tranminhhieu");
    private final List<Recipe> recipes = new ArrayList<>();

    private HomeFragment.OnFragmentInteractionListener mOnHomeFragmentInteractionListener = new HomeFragment.OnFragmentInteractionListener() {
        @Override
        public void onItemClicked(Recipe recipe) {
            Log.d(TAG, "Clicked " + recipe.toString());
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootLayout, recipeDetailFragment)
                    .addToBackStack(null)
                    .commit();
        }
        @Override
        public void onFabClicked() {
            NewRecipeFragment newRecipeFragment = NewRecipeFragment.newInstance(mOnNewRecipeFragmentInteractioListener);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootLayout, newRecipeFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

    private NewRecipeFragment.OnFragmentInteractionListener mOnNewRecipeFragmentInteractioListener = new NewRecipeFragment.OnFragmentInteractionListener() {
        @Override
        public void onRecipeShared() {
            HomeFragment homeFragment = HomeFragment.newInstance(recipes, mOnHomeFragmentInteractionListener);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootLayout, homeFragment)
                    .commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeFragment homeFragment = HomeFragment.newInstance(recipes, mOnHomeFragmentInteractionListener);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootLayout, homeFragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
