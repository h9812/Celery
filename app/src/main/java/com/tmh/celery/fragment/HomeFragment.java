package com.tmh.celery.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tmh.celery.R;
import com.tmh.celery.adapter.AllRecipesAdapter;
import com.tmh.celery.model.Recipe;
import com.tmh.celery.repository.RecipeRepo;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";

    // Data
    private List<Recipe> recipes;

    // Listeners
    private OnFragmentInteractionListener mListener;

    private final RecipeRepo.OnDataChangedListener onRecipeRepoDataChangedListener = new RecipeRepo.OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            updateViews();
        }
    };

    private final AllRecipesAdapter.OnItemPressedListener mOnAdapterItemPressedListener = new AllRecipesAdapter.OnItemPressedListener() {
        @Override
        public void onItemPressed(Recipe recipe) {
            mListener.onItemClicked(recipe);
        }
    };

    private final View.OnClickListener onNewRecipeFabClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onFabClicked();
        }
    };

    // Views
    private FloatingActionButton fabNewRecipe;
    private RecyclerView recyclerAllRecipes;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(List<Recipe> recipes) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable("RECIPES", (Serializable) recipes);
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeFragment newInstance(List<Recipe> recipes, OnFragmentInteractionListener listener) {
        Log.d("Home", "NewInstance!");
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable("RECIPES", (Serializable) recipes);
        fragment.setArguments(args);
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipes = (List<Recipe>) getArguments().getSerializable("RECIPES");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fabNewRecipe = view.findViewById(R.id.fabNewRecipe);
        fabNewRecipe.setOnClickListener(onNewRecipeFabClicked);
        recyclerAllRecipes = view.findViewById(R.id.recyclerAllRecipes);
        updateViews();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RecipeRepo.getInstance().addOnDataChangedListener(onRecipeRepoDataChangedListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        RecipeRepo.getInstance().removeOnDataChangedListener(onRecipeRepoDataChangedListener);
    }

    private void updateViews() {
        AllRecipesAdapter adapter = new AllRecipesAdapter(RecipeRepo.getInstance().getRecipes(), mOnAdapterItemPressedListener, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAllRecipes.setLayoutManager(layoutManager);
        recyclerAllRecipes.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        void onItemClicked(Recipe recipe);
        void onFabClicked();
    }
}
