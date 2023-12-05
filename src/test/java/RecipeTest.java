// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;
// import static org.junit.jupiter.api.Assertions.*;

// import java.util.List;
// import java.util.ArrayList;
// import java.util.Arrays;

// public class RecipeTest {
//     @Mock
//     private RecipeCard recipeCard;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

<<<<<<< Updated upstream
//     @Test
//     void testRecipeTitle() {
//         List<String> list = Arrays.asList("test");
//     }
=======
    /*@Test
    void testRecipeTitle() {
        List<String> list = Arrays.asList("test");
    }*/

    @Test
    void testRecipeTitle() {
        List<String> list = Arrays.asList("Pancakes", "Buttermilk\n\n\nFlour");
        String result = Helper._splitTitle(list);
        String expected = "Pancakes\n";
        assertEquals(result, expected);
    }
>>>>>>> Stashed changes

//     @Test
//     void testSplitIngredients() {
//         List<String> list = Arrays.asList("Title", "Ingredients\n\n\nMore Ingredients");
//         String[] result = Helper._splitIngred(list);
//         String[] expected = {"Ingredients", "More Ingredients"};
        
//         assertEquals(result[0], expected[0]);
//         assertEquals(result[1], expected[1]);
//     }

//     @Test
//     void testSplitMealType() {
//         List<String> list = Arrays.asList("Title", "MealType");
//         String result = Helper._splitMealType(list);
//         String expected = "MealType";
//         assertEquals(result, expected);
//     }

<<<<<<< Updated upstream
//     @Test
//     void testMealTypeBDD() {
//         List<String> mockListFromDatabase = Arrays.asList("Egg Fired Rice", "Three eggs\n\n\nA touch of oil\nSalt(2g)\n\nOvernight leftover rice", "Step1: Hot pan and Add Oil", "Step2: Fry eggs", "Step3: Add Rice when eggs are cooked", "lunch");
//         String result = Helper._splitMealType(mockListFromDatabase);
//         String expected = "lunch";
//         assertEquals(result, expected);
//     }
// }
=======
    @Test
    void testMealTypeBDD() {
        List<String> mockListFromDatabase = Arrays.asList("Egg Fired Rice", "Three eggs\n\n\nA touch of oil\nSalt(2g)\n\nOvernight leftover rice", "Step1: Hot pan and Add Oil", "Step2: Fry eggs", "Step3: Add Rice when eggs are cooked", "lunch");
        String result = Helper._splitMealType(mockListFromDatabase);
        String expected = "lunch";
        assertEquals(result, expected);
    }

    /*
     * BDD Scenario: Identify MealType as "breakfast" when retrieving breakfast recipe
     * Given: Caitlin has a recipe for Breakfast
     * When: Caitlin retrieves the recipe details
     * Then: The meal type should be listed as "breakfast"
     */
    @Test
    void testBreakfastBDD() {
        List<String> mockListFromDatabase = Arrays.asList("Omelette", "Three eggs\n\n\nA touch of oil\nSalt(2g)\n\nWhisk eggs and pour into hot pan", "Step1: Heat pan", "Step2: Cook omelette", "Step3: Serve hot", "breakfast");
        String result = Helper._splitMealType(mockListFromDatabase);
        String expected = "breakfast";
        assertEquals(result, expected);
    }

    /*
     * BDD Scenario: Identify MealType as "dinner" when retrieving dinner recipe
     * Given: Caitlin has a recipe for dinner
     * When: Caitlin retrieves the recipe details
     * Then: The meal type should be listed as "dinner"
     */
    @Test
    void testDinnerBDD() {
        List<String> mockListFromDatabase = Arrays.asList("Grilled Chicken", "Chicken breast\n\n\nOlive oil\nSalt and pepper\n\nMarinate chicken and grill", "Step1: Marinate chicken", "Step2: Grill chicken", "Step3: Serve hot", "dinner");
        String result = Helper._splitMealType(mockListFromDatabase);
        String expected = "dinner";
        assertEquals(result, expected);
    }
}
>>>>>>> Stashed changes
