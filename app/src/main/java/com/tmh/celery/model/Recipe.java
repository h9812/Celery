package com.tmh.celery.model;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private String id;
    private String name;
    private String ownerId;
    private String description;
    private List<String> directions;
    private List<String> ingredients;
    private List<String> ingredientAmounts;
    private List<String> notes;

    public Recipe(String id, String name, String ownerId, String description, List<String> directions, List<String> ingredients, List<String> ingredientAmounts, List<String> notes) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.directions = directions;
        this.ingredients = ingredients;
        this.ingredientAmounts = ingredientAmounts;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", description='" + description + '\'' +
                ", directions=" + directions +
                ", ingredients=" + ingredients +
                ", ingredientAmounts=" + ingredientAmounts +
                ", notes=" + notes +
                '}';
    }
}
