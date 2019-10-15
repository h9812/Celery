package com.tmh.celery.repository;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tmh.celery.model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

public class RecipeRepo {
    // Settings
    private final static String COLLECTION_RECIPES = "test_recipes";
    private final static String STORAGE_IMAGES = "test_images";
    private final static String DEFAULT_OWNERID = "tranminhhieu";
    private final static String TAG = "RecipeRepo";

    // Singleton implementation
    public interface OnDataChangedListener {
        void onDataChanged();
    }

    public interface OnRecipeUploadedListener {
        void onRecipeUploaded();
    }

    private static RecipeRepo instance = null;

    public synchronized static RecipeRepo getInstance() {
        if(instance == null) {
            instance = new RecipeRepo();
        }
        return  instance;
    }

    // Data, views
    private final List<Recipe> recipes = new ArrayList<>();
    private final List<OnDataChangedListener> onDataChangedListeners = new ArrayList<OnDataChangedListener>();
    private final List<OnRecipeUploadedListener> onRecipeUploadedListeners = new ArrayList<OnRecipeUploadedListener>();

    // Firebase references
    private FirebaseFirestore database;
    private FirebaseStorage storage;

    // Constructor
    private RecipeRepo() {
        storage = FirebaseStorage.getInstance();

        database = FirebaseFirestore.getInstance();

        database.collection(COLLECTION_RECIPES)
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
                                        (List<String>) document.get("notes"),
                                        (String) document.get("imageUrl")
                                );
                                recipes.add(recipe);
                            }
                            notifyDataChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        database.collection(COLLECTION_RECIPES)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                recipes.clear();
                for (QueryDocumentSnapshot document : value) {
                    Recipe recipe = new Recipe(
                            document.getId(),
                            (String) document.get("name"),
                            (String) document.get("ownerId"),
                            (String) document.get("description"),
                            (List<String>) document.get("directions"),
                            (List<String>) document.get("ingredients"),
                            (List<String>) document.get("ingredientAmounts"),
                            (List<String>) document.get("notes"),
                            (String) document.get("imageUrl")
                    );
                    recipes.add(recipe);
                }
                notifyDataChanged();
            }
        });
    }

    private void notifyDataChanged() {
        for(OnDataChangedListener listener : onDataChangedListeners) {
            listener.onDataChanged();
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public List<Recipe> getRecipeByUser(String userId) {
        return recipes;
    }

    public void uploadRecipe(HashMap<String, Object> recipeData, final byte[] imageData) {
        recipeData.put("ownerId", DEFAULT_OWNERID);
        database.collection("recipes")
                .add(recipeData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        uploadRecipeImage(documentReference.getId(), imageData);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void uploadRecipeImage(final String recipeId, byte[] imageData) {

        StorageReference storageRef = storage.getReference();
        final StorageReference imageReference = storageRef.child(STORAGE_IMAGES + "/" + recipeId +".jpg");

        UploadTask uploadTask = imageReference.putBytes(imageData);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return imageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    updateImageUrl(recipeId, downloadUri.toString());
                } else {

                }
            }
        });
    }

    private void updateImageUrl(String recipeId, String url) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("imageUrl", url);
        database.collection(COLLECTION_RECIPES).document(recipeId)
                .set(data, SetOptions.merge());
        notifyRecipeUploaded();
    }

    private void notifyRecipeUploaded() {
        for(OnRecipeUploadedListener listener : onRecipeUploadedListeners) {
            listener.onRecipeUploaded();
        }
    }

    public void addOnDataChangedListener(OnDataChangedListener listener) {
        if(listener != null) {
            onDataChangedListeners.add(listener);
        }
    }

    public void removeOnDataChangedListener(OnDataChangedListener listener) {
        if(listener != null) {
            onDataChangedListeners.remove(listener);
        }
    }

    public void addOnRecipeUpdatedListener(OnRecipeUploadedListener listener) {
        if(listener != null) {
            onRecipeUploadedListeners.add(listener);
        }
    }

    public void removeOnRecipeUploadedListener(OnRecipeUploadedListener listener) {
        if(listener != null) {
            onRecipeUploadedListeners.remove(listener);
        }
    }


}
