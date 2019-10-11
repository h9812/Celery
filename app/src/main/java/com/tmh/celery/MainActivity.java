package com.tmh.celery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tmh.celery.fragment.HomeFragment;
import com.tmh.celery.fragment.NewRecipeFragment;
import com.tmh.celery.fragment.RecipeDetailFragment;
import com.tmh.celery.model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final static String userId = new String("tranminhhieu");
    private final static String TAG = "MAIN";
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
        public void onRecipeShared(Recipe recipe) {
            recipe.setOwnerId(userId);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("recipes")
                    .add(recipe.getHashMap())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            recipes.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Recipe recipe = new Recipe(
                                        document.getId(),
                                        (String) document.get("name"),
                                        (String) document.get("ownerId"),
                                        (String) document.get("description"),
                                        (List<String>) document.get("directions"),
                                        (List<String>) document.get("ingredients"),
                                        (List<String>) document.get("ingredientAmounts"),
                                        (List<String>) document.get("notes")
                                );
                                recipes.add(recipe);
                                Log.d(TAG, recipe.toString());
                            }

                            HomeFragment homeFragment = HomeFragment.newInstance(recipes, mOnHomeFragmentInteractionListener);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.rootLayout, homeFragment)
                                    .commit();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
