import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeTest {
    @Mock
    private RecipeCard recipeCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRecipeTitle() {
        List<String> list = Arrays.asList("test");
    }

    @Test
    void testSplitIngredients() {
        List<String> list = Arrays.asList("Title", "Ingredients\n\n\nMore Ingredients");
        String[] result = Helper._splitIngred(list);
        String[] expected = {"Ingredients", "More Ingredients"};
        
        assertEquals(result[0], expected[0]);
        assertEquals(result[1], expected[1]);
    }

    @Test
    void testSplitMealType() {
        List<String> list = Arrays.asList("Title", "MealType");
        String result = Helper._splitMealType(list);
        String expected = "MealType";
        assertEquals(result, expected);
    }

    @Test
    void testMealTypeBDD() {
        List<String> mockListFromDatabase = Arrays.asList("Egg Fired Rice", "Three eggs\n\n\nA touch of oil\nSalt(2g)\n\nOvernight leftover rice", "Step1: Hot pan and Add Oil", "Step2: Fry eggs", "Step3: Add Rice when eggs are cooked", "lunch");
        String result = Helper._splitMealType(mockListFromDatabase);
        String expected = "lunch";
        assertEquals(result, expected);
    }
}
