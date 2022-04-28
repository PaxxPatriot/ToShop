import com.toshop.application.Application;
import com.toshop.application.interfaces.RecipeProviderPlugin;
import com.toshop.domain.entities.Ingredient;
import com.toshop.domain.entities.Recipe;
import com.toshop.plugins.database.SQLiteDatabasePlugin;
import com.toshop.plugins.recipes.spoonacular.SpoonacularRecipeProviderPlugin;
import com.toshop.plugins.ui.SwingUIPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Program {
    public static void main(String[] args) {
        var database = new SQLiteDatabasePlugin("toshop.db");
        var ui = new SwingUIPlugin();
        var recipeProvider = new SpoonacularRecipeProviderPlugin("9e9a94f9481a4a72ae6b689cc6a227eb");
        var testProvider = new RecipeProviderPlugin() {

            List<Recipe> recipes = Arrays.asList(new Recipe("1", "Recipe 1", "https://upload.wikimedia.org/wikipedia/commons/0/0f/Hungarian_goulash_soup.jpg", 1, 45, new Ingredient[]{new Ingredient(1, "eggplant")}, "Blallsadlasdlasldasldsald"));

            @Override
            public CompletableFuture<Collection<Recipe>> searchRecipes(String query) {
                return CompletableFuture.completedFuture(recipes);
            }

            @Override
            public CompletableFuture<Recipe> getDetailedRecipe(String id) {
                var recipe = recipes.stream().filter(r -> r.getId().equals(id)).findFirst();
                if (recipe.isPresent()) {
                    return CompletableFuture.completedFuture(recipe.get());
                } else {
                    return CompletableFuture.completedFuture(null);
                }
            }
        };
        var application = new Application(database, ui, recipeProvider);
        application.run();
    }
}
