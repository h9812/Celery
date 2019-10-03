package com.tmh.celery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tmh.celery.fragment.HomeFragment;
import com.tmh.celery.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final List<Recipe> recipes = new ArrayList<>();

        db.collection("recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            recipes.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Recipe recipe = new Recipe(
                                        (String) document.get("name"),
                                        (String) document.get("ownerId"),
                                        (List<String>) document.get("directions"),
                                        (List<String>) document.get("ingredients"),
                                        (List<String>) document.get("ingredientAmounts"),
                                        (List<String>) document.get("notes")
                                );
                                Log.d(TAG, recipe.toString());
                                recipes.add(recipe);
                                HomeFragment homeFragment = HomeFragment.newInstance(recipes);

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.rootLayout, homeFragment)
                                        .commit();
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
