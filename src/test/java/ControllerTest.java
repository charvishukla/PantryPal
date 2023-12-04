import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Field;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import javafx.event.ActionEvent;


class ControllerTest {
    @InjectMocks
    private Controller controller;
    private View mockView;
    private Model mockModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockModel = mock(Model.class);
        mockView = mock(View.class);
        //List<RecipeCard> l = mock(List.class);
        //controller = new Controller(mockView, mockModel);
    }
    
    
    /* US 1 BDD Scenario 1 - no recipes displayed */
    // @Test
    // public void testNoRecipesDisplayedOnHomepage() {

    //     ActionEvent mockEvent = mock(ActionEvent.class);
    //     List<RecipeCard> recipeCards = new ArrayList<RecipeCard>();

    //     when(mockView.getAppFrame().getRecipeList().getRecipeCards()).thenReturn(recipeCards);

    //     // controller.handleCreateButtonClick(mockEvent);

    //     // assertFalse(this.mockView.getAppFrame().getRecipeList().getRecipeCards().isEmpty());
    // }

    // @Test
    // public void testCreateButton() {
    //     when(mockView.switchScene()).thenReturn(true);
    // }

    /* US 1 BDD Scenario 2 - at least one recipe displayed */
    /*@Test
    public void testRecipesDisplayedOnHomepage() {

        assertFalse(controller.getView().getAppFrame().getRecipeList().getRecipeCards().isEmpty());

    }*/

    /* US 2 BDD Scenario 1 - A new recipe is created */
    // @Test
    // public void testCreateNewRecipe() {
        
    //     RecipeCard recipeCard = mock(RecipeCard.class);

    //     when(this.mockView.getAppFrame().getRecipeList().addRecipeCard(recipeCard)).thenReturn(recipeCard);

    //     controller.handleCreateButtonClick(mockEvent);

    //     int initialSize = this.mockView.getAppFrame().getRecipeList().getRecipeCards().size();

    //     controller.handleCreateButtonClick(mockEvent);

    //     int finalSize = this.mockView.getAppFrame().getRecipeList().getRecipeCards().size();

    //     assertEquals(initialSize + 1, finalSize);
        
    // }
}
