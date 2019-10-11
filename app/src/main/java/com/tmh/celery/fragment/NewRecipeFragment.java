package com.tmh.celery.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tmh.celery.R;
import com.tmh.celery.model.Recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewRecipeFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onRecipeShared(Recipe recipe);
    }

    // Child views
    private FloatingActionButton fabShareRecipe;
    private LinearLayout linearIngredientsContainer;
    private LinearLayout linearDirectionsContainer;
    private LinearLayout linearNotesContainer;
    private Button buttonAddIngredient;
    private Button buttonAddDirection;
    private Button buttonAddNote;

    // Line ids
    private List<Integer> listIngredientLineIds = new ArrayList<>();
    private List<Integer> listDirectionLineIds = new ArrayList<>();
    private List<Integer> listNoteLineIds = new ArrayList<>();

    // EditTexts
    private List<EditText> listEditIngredients = new ArrayList<>();
    private List<EditText> listEditIngredientAmounts = new ArrayList<>();
    private List<EditText> listEditDirections = new ArrayList<>();
    private List<EditText> listEditNotes = new ArrayList<>();

    // Model data holder
    private String name;
    private String description;
    private List<String> listIngredients = new ArrayList<>();
    private List<String> listIngredientAmounts = new ArrayList<>();
    private List<String> listDirections = new ArrayList<>();
    private List<String> listNotes = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public NewRecipeFragment() {
        // Required empty public constructor
    }

    public static NewRecipeFragment newInstance(OnFragmentInteractionListener listener) {
        NewRecipeFragment fragment = new NewRecipeFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_recipe, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        fabShareRecipe = view.findViewById(R.id.fabShareRecipe);
        fabShareRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRecipe();
            }
        });


        linearIngredientsContainer = view.findViewById(R.id.linearIngredientsContainer);
        buttonAddIngredient = view.findViewById(R.id.buttonAddIngredient);
        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });
        addIngredient();
        addIngredient();
        addIngredient();

        linearDirectionsContainer = view.findViewById(R.id.linearDirectionsContainer);
        buttonAddDirection = view.findViewById(R.id.buttonAddDirection);
        buttonAddDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDirection();
            }
        });
        addDirection();
        addDirection();
        addDirection();

        linearNotesContainer = view.findViewById(R.id.linearNotesContainer);
        buttonAddNote = view.findViewById(R.id.buttonAddNote);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        addNote();
        addNote();
        addNote();



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

    private boolean isInputValid() {
        return true;
    }

    private void shareRecipe() {
//            String recipeId = new String();
            name = ((EditText) getView().findViewById(R.id.editRecipeName)).getText().toString();
        description = ((EditText) getView().findViewById(R.id.editRecipeDescription)).getText().toString();

        listIngredients.clear();
        listIngredientAmounts.clear();
        listDirections.clear();
        listNotes.clear();

        for(EditText editText: listEditIngredients) {
            listIngredients.add(editText.getText().toString());
        }

        for(EditText editText: listEditIngredientAmounts) {
            listIngredientAmounts.add(editText.getText().toString());
        }

        for(EditText editText: listEditDirections) {
            listDirections.add(editText.getText().toString());
        }

        for(EditText editText: listEditNotes) {
            listNotes.add(editText.getText().toString());
        }


        if(isInputValid()) {
            Recipe newRecipe = new Recipe(
                    "",
                    name,
                    "",
                    description,
                    listDirections,
                    listIngredients,
                    listIngredientAmounts,
                    listNotes
            );
            mListener.onRecipeShared(newRecipe);
        }
    }

    private void addIngredient() {
        final int lineId = View.generateViewId();
        LinearLayout line = new LinearLayout(getActivity());
        line.setId(lineId);
        line.setOrientation(LinearLayout.HORIZONTAL);
        line.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        final EditText editIngredient = new EditText(getActivity());
        editIngredient.setHint(R.string.edit_hint_ingredient);
        line.addView(editIngredient);

        final EditText editIngredientAmount = new EditText(getActivity());
        editIngredientAmount.setHint(R.string.edit_hint_ingredient_amount);
        line.addView(editIngredientAmount);

        final Button buttonDelete = new Button(getActivity());
        buttonDelete.setText(R.string.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIngredient(lineId);
            }
        });
        buttonDelete.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        line.addView(buttonDelete);

        listEditIngredients.add(editIngredient);
        listEditIngredientAmounts.add(editIngredientAmount);
        listIngredientLineIds.add(lineId);

        linearIngredientsContainer.addView(line);
    }

    private void deleteIngredient(int lineId) {
        int index = listIngredientLineIds.indexOf(lineId);

        listIngredientLineIds.remove(index);
        listEditIngredients.remove(index);
        listEditIngredientAmounts.remove(index);

        linearIngredientsContainer.removeView(getView().findViewById(lineId));
    }

    private void addDirection() {
        final int lineId = View.generateViewId();
        LinearLayout line = new LinearLayout(getActivity());
        line.setId(lineId);
        line.setOrientation(LinearLayout.HORIZONTAL);
        line.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        final EditText editDirection = new EditText(getActivity());
        line.addView(editDirection);

        final Button buttonDelete = new Button(getActivity());
        buttonDelete.setText(R.string.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDirection(lineId);
            }
        });
        buttonDelete.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        line.addView(buttonDelete);

        listEditDirections.add(editDirection);
        listDirectionLineIds.add(lineId);

        linearDirectionsContainer.addView(line);
    }

    private void deleteDirection(int lineId) {
        int index = listDirectionLineIds.indexOf(lineId);

        listDirectionLineIds.remove(index);
        listEditDirections.remove(index);

        linearDirectionsContainer.removeView(getView().findViewById(lineId));
    }

    private void addNote() {
        final int lineId = View.generateViewId();
        LinearLayout line = new LinearLayout(getActivity());
        line.setId(lineId);
        line.setOrientation(LinearLayout.HORIZONTAL);
        line.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        final EditText editNote = new EditText(getActivity());
        line.addView(editNote);

        final Button buttonDelete = new Button(getActivity());
        buttonDelete.setText(R.string.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(lineId);
            }
        });
        buttonDelete.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        line.addView(buttonDelete);

        listEditNotes.add(editNote);
        listNoteLineIds.add(lineId);

        linearNotesContainer.addView(line);
    }

    private void deleteNote(int lineId) {
        int index = listNoteLineIds.indexOf(lineId);

        listNoteLineIds.remove(index);
        listEditNotes.remove(index);

        linearNotesContainer.removeView(getView().findViewById(lineId));
    }
}
