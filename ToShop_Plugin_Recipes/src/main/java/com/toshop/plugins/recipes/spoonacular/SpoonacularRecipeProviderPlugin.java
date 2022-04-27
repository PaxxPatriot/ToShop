package com.toshop.plugins.recipes.spoonacular;

import com.fasterxml.jackson.databind.*;
import com.toshop.application.interfaces.RecipeProviderPlugin;
import com.toshop.domain.entities.Recipe;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SpoonacularRecipeProviderPlugin implements RecipeProviderPlugin {

    private final String apiKey;
    private final WebTarget webTarget;

    private static JacksonJsonProvider jackson_json_provider = new JacksonJaxbJsonProvider()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public SpoonacularRecipeProviderPlugin(String apiKey) {
        this.apiKey = apiKey;
        this.webTarget = ClientBuilder.newClient().register(jackson_json_provider).target("https://api.spoonacular.com/recipes/");
    }

    @Override
    public CompletableFuture<Collection<Recipe>> searchRecipes(String query) {
        var future = CompletableFuture.supplyAsync(() -> getTarget("complexSearch").queryParam("query", query).queryParam("number", 3).request().get(SearchResult.class));
        var mappedFuture = future.thenApply(searchResult -> (Collection<Recipe>)Arrays.stream(searchResult.results).map(r -> new Recipe(Integer.toString(r.id), r.title, r.image)).collect(Collectors.toList()));
        return mappedFuture;
    }

    @Override
    public CompletableFuture<Recipe> getDetailedRecipe(String id) {
        var future = CompletableFuture.supplyAsync(() -> getTarget(id + "/information").request().get(Recipe.class));
        return future;
    }

    private WebTarget getTarget(String path) {
        return webTarget.path(path).queryParam("apiKey", apiKey);
    }
}
