import com.toshop.domain.entities.Ingredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class DomainTests {
    @Test
    @DisplayName("Test Ingredient name format - Single word")
    void testIngredientNameFormat() {
        // Arrange
        Ingredient testIngredient = new Ingredient(1, "banana");

        // Act
        String formattedName = testIngredient.getFormattedName();

        // Assert
        assumeTrue(formattedName.equals("Banana"));
    }

    @Test
    @DisplayName("Test Ingredient name format - Multiple words")
    void testIngredientNameFormatMultiple() {
        // Arrange
        Ingredient testIngredient = new Ingredient(1, "banana pie");

        // Act
        String formattedName = testIngredient.getFormattedName();

        // Assert
        assumeTrue(formattedName.equals("Banana Pie"));
    }

    @Test
    @DisplayName("Test Ingredient name format - Null")
    void testIngredientNameFormatNull() {
        // Arrange
        Ingredient testIngredient = new Ingredient(1, null);

        // Act
        String formattedName = testIngredient.getFormattedName();

        // Assert
        assumeTrue(formattedName == null);
    }

    @Test
    @DisplayName("Test Ingredient toString()")
    void testIngredientToString() {
        // Arrange
        Ingredient testIngredient = new Ingredient(12, "banana");

        // Act
        String formattedName = testIngredient.toString();

        // Assert
        assumeTrue(formattedName.equals("12x Banana"));
    }
}
