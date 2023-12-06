import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * MockController is a mock implementation of the Controller class.
 * It is used for unit testing the Controller's functionality without
 * needing actual implementations of dependent classes and UI elements.
 */
public class MockController extends Controller {

    private Boolean loggedout;
    private List<RecipeCard> mockRecipeCards;

    /**
     * Constructor for MockController. Initializes mock recipe cards.
     */
    public MockController() {
        super(null, null); 
        mockRecipeCards = new ArrayList<>();
        mockRecipeCards.add(new RecipeCard("Recipe 1", "MealType1", "2023-01-01T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 2", "MealType2", "2023-01-02T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 3", "MealType1", "2023-01-02T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 4", "MealType3", "2023-01-02T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 5", "MealType1", "2023-01-02T00:00:00Z"));
    }


    /**
     * Mock implementation of handleFilterBoxClick. Filters recipe cards based on the provided filter.
     * 
     * @param clickedFilter The filter to be applied on recipe cards.
     * @return List of filtered RecipeCard objects.
     */
    public List<RecipeCard> handleFilterBoxClick(String clickedFilter) {
        List<RecipeCard> filteredCards = new ArrayList<>();
        for (RecipeCard card : mockRecipeCards) {
            if (card.getMealType().equals(clickedFilter)) {
                filteredCards.add(card);
            }
        }
        System.out.println("RecipeCards successfully filtered using " + clickedFilter + " property");
        return filteredCards;
    }


    /**
     * Mock implementation of handleSortingBoxClick. Sorts recipe cards based on the provided sorting method.
     * 
     * @param sortingMethod The sorting method to be applied (e.g., "Alphabetically").
     *                      This is the sorting method that the user actually selected in the real implementation
     * @return List of sorted RecipeCard objects.
     */
    public List<RecipeCard> handleSortingBoxClick(String sortingMethod) {
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
     * Mock implementation of handleLogoutButtonClick. Clears the recipe cards and sets the logout status.
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


        // Getters for unit testing
        public Boolean getLoggedOutStatus() {
            return loggedout;
        }
    
        public List<RecipeCard> getMockRecipeCards() {
            return mockRecipeCards;
        }



}
