import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;


/**
 * 
 */
public class MockController extends Controller {

    private Boolean loggedout;
    private List<RecipeCard> mockRecipeCards;

    public MockController() {
        super(null, null); 
        mockRecipeCards = new ArrayList<>();
        mockRecipeCards.add(new RecipeCard("Recipe 1", "MealType1", "2023-01-01T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 2", "MealType2", "2023-01-02T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 3", "MealType1", "2023-01-02T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 4", "MealType3", "2023-01-02T00:00:00Z"));
        mockRecipeCards.add(new RecipeCard("Recipe 5", "MealType1", "2023-01-02T00:00:00Z"));
    }

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

    public Boolean handleLogoutButtonClick() {
        mockRecipeCards.clear();
        System.out.println("Logged out and cleared RecipeCards");
        return loggedout = true;
    }


    public boolean handleLoginButtonClick(String username, String password) {
        System.out.println("Attempting login with username: " + username);
        this.loggedout = loggedout; /// value does not update
        return username.equals("validUser") && password.equals("validPass");
    }

}
