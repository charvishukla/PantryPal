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

    @BeforeEach
    void setUp() {
        Model mockModel = mock(Model.class);
        View mockView = mock(View.class);
        controller = new Controller(mockView, mockModel);
    }
    
    
    // /* US 1 BDD Scenario 1 - no recipes displayed */
    // @Test
    // public void testNoRecipesDisplayedOnHomepage() {

    //     ActionEvent mockEvent = mock(ActionEvent.class);

    //     controller.handleCreateButtonClick(mockEvent);

    //     assertFalse(this.view.getAppFrame().getRecipeList().getRecipeCards().isEmpty());
    // }

    /* US 1 BDD Scenario 2 - at least one recipe displayed */
    /*@Test
    public void testRecipesDisplayedOnHomepage() {

        assertFalse(controller.getView().getAppFrame().getRecipeList().getRecipeCards().isEmpty());

    }*/

    /* US 2 BDD Scenario 1 - A new recipe is created */
    /*@Test
    public void testCreateNewRecipe() {
        ActionEvent newRecipeButtonEvent = mock(ActionEvent.class);

        controller.handleCreateButtonClick(newRecipeButtonEvent);

        
    }*/
}
