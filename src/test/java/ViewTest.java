

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;



public class ViewTest {

    @Test
    public void testSwitchScene() {
        Stage stage = new Stage();
        View view = new View(stage);
        
        Parent parentNode = new Pane();
        view.switchScene(parentNode);

        assertEquals(parentNode, stage.getScene().getRoot());
    }

    @Test
    public void testFooterCreateButtonAction() {
        View view = new View(new Stage());
        Footer footer = view.getAppFrame().getFooter();
        Button createButton = footer.getCreateButton();

        ButtonActionListenerMock listener = new ButtonActionListenerMock();
        footer.setCreateButtonAction(listener);

        createButton.fireEvent(new ActionEvent());

        assertTrue(listener.isActionPerformed());
    }
    @Test 
    public void testBreakfastisnotNull(){
        View view = new View(new Stage());
        CreateFrame frame = view.getCreateFrame();
        assertNotNull(frame.breakfastButton, "Verify, Breakfast Button is not NULL");
    }

    @Test 
    public void testLunchisnotNull(){
        View view = new View(new Stage());
        CreateFrame frame = view.getCreateFrame();
        assertNotNull(frame.lunchButton, "Verify, Lunch Button is not NULL");
    }
     @Test 
    public void testDinnerisnotNull(){
        View view = new View(new Stage());
        CreateFrame frame = view.getCreateFrame();
        assertNotNull(frame.dinnerButton, "Verify, Dinner Button is not NULL");
    }


    // add more tests for other functionality
    /*

    private static class StageMock extends Stage {
        private Scene scene;

        @Override
        public void setScene(Scene scene) {
            this.scene = scene;
        }

        public Scene getScene() {
            return scene;
        }
    }
 
    private static class ParentMock extends Parent {
        // Mock the Parent class
    }
    */

    private static class ButtonActionListenerMock implements EventHandler<ActionEvent> {
        private boolean actionPerformed = false;

        @Override
        public void handle(ActionEvent event) {
            actionPerformed = true;
        }

        public boolean isActionPerformed() {
            return actionPerformed;
        }
    }
}