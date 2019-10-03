package com.tmh.celery.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Recipe implements Serializable {
    private String name;
    private String ownerId;
    private List<String> directions;
    private List<String> ingredients;
    private List<String> ingredientAmounts;
    private List<String> notes;

    public Recipe(String name, String ownerId, List<String> directions, List<String> ingredients, List<String> ingredientAmounts, List<String> notes) {
        this.name = name;
        this.ownerId = ownerId;
        this.directions = directions;
        this.ingredients = ingredients;
        this.ingredientAmounts = ingredientAmounts;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredientAmounts() {
        return ingredientAmounts;
    }

    public void setIngredientAmounts(List<String> ingredientAmounts) {
        this.ingredientAmounts = ingredientAmounts;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", directions=" + directions +
                ", ingredients=" + ingredients +
                ", ingredientAmounts=" + ingredientAmounts +
                ", notes=" + notes +
                '}';
    }
}
