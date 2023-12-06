import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import javafx.stage.Stage;

class MockRecipeCard {
    String title;
    String mealType;
    String time;

    public MockRecipeCard(String title, String mealType, String time) {
        this.title = title;
        this.mealType = mealType;
        this.time = time;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMealType() {
        return this.mealType;
    }

    public String getTime() {
        return this.time;
    }

}

public class MockController {

    private Boolean loggedout = false;
    private List<MockRecipeCard> mockRecipeCards;
    private List<MockRecipeCard> emptyRecipeCard;

    /**
     * Constructor for MockController. Initializes mock recipe cards.
     */
    public MockController() {
        emptyRecipeCard = new ArrayList<>();
        // Initialize mockRecipeCards similar to how Controller would have done.
        mockRecipeCards = new ArrayList<>();
        mockRecipeCards.add(new MockRecipeCard("Recipe 1", "MealType1", "2023-01-01T00:00:00Z"));
        mockRecipeCards.add(new MockRecipeCard("Recipe 2", "MealType2", "2023-02-02T00:00:00Z"));
        mockRecipeCards.add(new MockRecipeCard("Recipe 3", "MealType1", "2023-03-02T00:00:00Z"));
        mockRecipeCards.add(new MockRecipeCard("Recipe 4", "MealType3", "2023-04-02T00:00:00Z"));
        mockRecipeCards.add(new MockRecipeCard("Recipe 5", "MealType1", "2023-05-02T00:00:00Z"));
    }

    /**
     * Mock implementation of handleFilterBoxClick. Filters recipe cards based on
     * the provided filter.
     * 
     * @param clickedFilter The filter to be applied on recipe cards.
     * @return List of filtered RecipeCard objects.
     */
    public List<MockRecipeCard> handleFilterBoxClick(String clickedFilter) {
        List<MockRecipeCard> filteredCards = new ArrayList<>();
        for (MockRecipeCard card : mockRecipeCards) {
            if (card.getMealType().equals(clickedFilter)) {
                filteredCards.add(card);
            }
        }
        System.out.println("RecipeCards successfully filtered using " + clickedFilter + " property");
        return filteredCards;
    }

    /**
     * Mock implementation of handleSortingBoxClick. Sorts recipe cards based on the
     * provided sorting method.
     * 
     * @param sortingMethod The sorting method to be applied (e.g.,
     *                      "Alphabetically").
     *                      This is the sorting method that the user actually
     *                      selected in the real implementation
     * @return List of sorted RecipeCard objects.
     */
    public List<MockRecipeCard> handleSortingBoxClick(String sortingMethod) {
        System.out.println("Sorting RecipeCards based on " + sortingMethod);

        if (sortingMethod.equals("Alphabetically")) {
            Collections.sort(mockRecipeCards, (card1, card2) -> card1.getTitle().compareToIgnoreCase(card2.getTitle()));
        } else if (sortingMethod.equals("Newest to Oldest")) {
            Collections.sort(mockRecipeCards, (card1, card2) -> card2.getTime().compareTo(card1.getTime()));
        } else {
            Collections.sort(mockRecipeCards, (card1, card2) -> card1.getTime().compareTo(card2.getTime()));
        }

        return new ArrayList<>(mockRecipeCards); // Return the sorted list
    }

    /**
     * Mock implementation of handleLogoutButtonClick. Clears the recipe cards and
     * sets the logout status.
     * 
     * @return The logout status after the operation.
     */
    public Boolean handleLogoutButtonClick() {
        mockRecipeCards.clear();
        System.out.println("Logged out and cleared RecipeCards");
        return loggedout = true;
    }

    /**
     * Mock implementation of handleLoginButtonClick. Simulates user login.
     * 
     * @param username The username for login.
     * @param password The password for login.
     * @return true if login is successful, false otherwise.
     */
    public boolean handleLoginButtonClick(String username, String password) {
        System.out.println("Attempting login with username: " + username);
        this.loggedout = loggedout; /// value does not update
        return username.equals("validUser") && password.equals("validPass");
    }

    public List<String> getAllTitles() {
        System.out.println("Getting all titles.");
        List<String> res = new ArrayList<>();
        for (MockRecipeCard card : mockRecipeCards) {
            res.add(card.getTitle());
        }
        return res;
    }

    // mock delete
    public boolean deleteRecipe(String title) {
        System.out.println("Deleting recipe with title: " + title);
        return mockRecipeCards.removeIf(card -> card.getTitle().equals(title));
    }

    public boolean insertRecipe(MockRecipeCard recipeCard) {
        if (mockRecipeCards.contains(recipeCard)) {
            return false;
        }
        System.out.println("Inserting" + recipeCard.getMealType() + "recipe with title: " + recipeCard.getTitle()
                + "at time" + recipeCard.getTime());
        return mockRecipeCards.add(recipeCard);
    }

    // Getters for unit testing
    public Boolean getLoggedOutStatus() {
        return loggedout;
    }

    public List<MockRecipeCard> getMockRecipeCards() {
        return mockRecipeCards;
    }

}
