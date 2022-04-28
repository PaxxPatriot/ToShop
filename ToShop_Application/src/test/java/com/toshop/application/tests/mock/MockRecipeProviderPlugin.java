package com.toshop.application.tests.mock;

import com.toshop.application.interfaces.RecipeProviderPlugin;
import com.toshop.domain.entities.Recipe;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class MockRecipeProviderPlugin implements RecipeProviderPlugin {
    @Override
    public CompletableFuture<Collection<Recipe>> searchRecipes(String query) {
        return null;
    }

    @Override
    public CompletableFuture<Recipe> getDetailedRecipe(String id) {
        return null;
    }
}
