

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

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


@ExtendWith(ApplicationExtension.class)
public class ViewTest {

    private Stage stage;
    private View view;
    private Parent parentNode;
    private CreateFrame frame;

    @Start
    public void onStart(Stage stage) {
        this.stage = stage;
        this.view = new View(stage);
        parentNode = new Pane();
        view.switchScene(parentNode);
        frame = view.getCreateFrame();
    }

    @Test
    public void testSwitchScene() {
        assertEquals(parentNode, stage.getScene().getRoot());
    }

    @Test
    public void testFooterCreateButtonAction() {
        Footer footer = view.getAppFrame().getFooter();
        Button createButton = footer.getCreateButton();

        ButtonActionListenerMock listener = new ButtonActionListenerMock();
        footer.setCreateButtonAction(listener);

        createButton.fireEvent(new ActionEvent());

        assertTrue(listener.isActionPerformed());
    }
     
    @Test 
    public void testBreakfastisnotNull(){
        assertNotNull(frame.getBreakfastButton(), "Verify, Breakfast Button is not NULL");
    }
    
    @Test 
    public void testLunchisnotNull(){
        assertNotNull(frame.getLunchButton(), "Verify, Lunch Button is not NULL");
    }
     @Test 
    public void testDinnerisnotNull(){
        assertNotNull(frame.getDinnerButton(), "Verify, Dinner Button is not NULL");
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