import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class: MockControllerTest will test
 * - sorting and filtering logic.
 * - Login and logout method calls
 * - get all titles
 * - delete recipe
 * - insert recipe
 * 
 */
class MockControllerTest {
    private MockController controller;

    @BeforeEach
    void setUp() {
        controller = new MockController();
    }

    // ---------------------- Tests for handleFilterBoxClick----------------
    @Test
    void testFilterBoxClick_ValidFilter() {
        List<MockRecipeCard> filtered = controller.handleFilterBoxClick("MealType1");
        assertTrue(filtered.stream().allMatch(card -> card.getMealType().equals("MealType1")));
        assertEquals(3, filtered.size());
    }

    @Test
    void testFilterBoxClick_InvalidFilter() {
        List<MockRecipeCard> filtered = controller.handleFilterBoxClick("InvalidType");
        assertTrue(filtered.isEmpty());
    }

    // --------------------Tests for SortingBoxClick---------------------------
    @Test
    void testSortingBoxClick_Alphabetical() {
        List<MockRecipeCard> sorted = controller.handleSortingBoxClick("Alphabetically");
        assertEquals("Recipe 1", sorted.get(0).getTitle());
        assertEquals("Recipe 2", sorted.get(1).getTitle());
        assertEquals("Recipe 3", sorted.get(2).getTitle());
        assertEquals("Recipe 4", sorted.get(3).getTitle());
        assertEquals("Recipe 5", sorted.get(4).getTitle());

        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).getTitle().compareToIgnoreCase(sorted.get(i + 1).getTitle()) <= 0);
        }
    }

    @Test
    void testSortingBoxClick_NewestToOldest() {
        List<MockRecipeCard> sorted = controller.handleSortingBoxClick("Newest to Oldest");
        assertEquals("Recipe 5", sorted.get(0).getTitle());
        assertEquals("Recipe 4", sorted.get(1).getTitle());
        assertEquals("Recipe 3", sorted.get(2).getTitle());
        assertEquals("Recipe 2", sorted.get(3).getTitle());
        assertEquals("Recipe 1", sorted.get(4).getTitle());

        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).getTime().compareTo(sorted.get(i + 1).getTime()) >= 0);
        }
    }

    // ---------------Tests for handleLogoutButtonClick------------------------

    @Test
    void testLogoutButtonClick_ClearsRecipeCards() {
        controller.handleLogoutButtonClick();
        assertTrue(controller.getMockRecipeCards().isEmpty());
    }

    @Test
    void testLogoutButtonClick_UpdatesLoggedOutStatus() {
        controller.handleLogoutButtonClick();
        assertTrue(controller.getLoggedOutStatus());
    }

    // ---------------- Tests for handleLoginButtonClick-----------------------
    @Test
    void testLoginButtonClick_ValidCredentials() {
        boolean loginResult = controller.handleLoginButtonClick("validUser", "validPass");
        assertTrue(loginResult);
    }

    @Test
    void testLoginButtonClick_InvalidCredentials() {
        boolean loginResult = controller.handleLoginButtonClick("invalidUser", "invalidPass");
        assertFalse(loginResult);
    }

    // ------------------------GetTitles--------------------------------------

    @Test
    void testGetAllTitles() {
        MockController controller = new MockController();
        List<String> titles = controller.getAllTitles();
        assertEquals(titles.size(), 5);
        assertTrue(titles.contains("Recipe 2"));
        assertTrue(titles.contains("Recipe 3"));
        assertTrue(titles.contains("Recipe 4"));
    }
     // ------------------------Delete--------------------------------------
    @Test
    void testDeleteRecipe() {
        assertTrue(controller.deleteRecipe("Recipe 1"));
        assertFalse(controller.getMockRecipeCards()
                .contains(new MockRecipeCard("Recipe 1", "MealType1", "2023-01-01T00:00:00Z")));
    }

    @Test
    void testDeleteNonExistingRecipe() {
        assertFalse(controller.deleteRecipe("Non-Existing Recipe"));
        assertEquals(5, controller.getMockRecipeCards().size()); 
    }
        // -----------------------Insert--------------------------------------
    @Test
    void testInsertRecipe() {
        MockRecipeCard newCard = new MockRecipeCard("Yummy", "Lunch", "2023-01-03T00:00:00Z");
        assertTrue(controller.insertRecipe(newCard));
        assertTrue(controller.getMockRecipeCards().contains(newCard));
    }

    @Test
    void testInsertExistingRecipe() {
       
        assertFalse(controller.insertRecipe(controller.getMockRecipeCards().get(0)));
         
        assertEquals(5, controller.getMockRecipeCards().size());
    }

}