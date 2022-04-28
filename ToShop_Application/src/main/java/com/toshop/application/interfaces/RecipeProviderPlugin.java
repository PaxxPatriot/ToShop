package com.toshop.application.interfaces;

import com.toshop.domain.entities.Recipe;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface RecipeProviderPlugin {
    CompletableFuture<Collection<Recipe>> searchRecipes(String query);
    CompletableFuture<Recipe> getDetailedRecipe(String id);
}
