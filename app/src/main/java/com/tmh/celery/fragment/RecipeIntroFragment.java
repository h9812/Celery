package com.tmh.celery.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmh.celery.R;
import com.tmh.celery.model.Recipe;

public class RecipeIntroFragment extends Fragment {
    private Recipe recipe;

    private OnFragmentInteractionListener mListener;

    public RecipeIntroFragment() {
        // Required empty public constructor
    }

    public static RecipeIntroFragment newInstance(Recipe recipe) {
        RecipeIntroFragment fragment = new RecipeIntroFragment();
        Bundle args = new Bundle();
        args.putSerializable("RECIPE", recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable("RECIPE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_intro, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView textIntro = view.findViewById(R.id.textIntro);
        textIntro.setText(
                recipe.getId() + "\n" +
                recipe.getName() + "\n" +
                recipe.getOwnerId() + "\n" +
                recipe.getDescription()
        );
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
