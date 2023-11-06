package src;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField; // Might not need this
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; // Might need to open new stage (new window)
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.ImageView;


public class View {
    private AppFrame appframe;

    public View() {
        this.appframe = new AppFrame();
    }

    public AppFrame getAppFrame() {
        return this.appframe;
    }
}

class Header extends HBox {
    Header() {
        this.setPrefSize(1280, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class Footer extends HBox {

    private Button addButton;
    
    Footer() {
        this.setPrefSize(1280, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addButton = new Button("Add Recipe");
        addButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(addButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getAddButton() {
        return addButton;
    }
}

class RecipeList extends VBox {
    RecipeList() {
        // Do we need setPrefSize? Maybe not?
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #80d838");
        //Add a testing recipe to recipelist 
        Recipe test = new Recipe();
        test.setRecipeName("test");
        this.getChildren().add(test);
    }
}

class Recipe extends HBox {

    private Button detail;
    private Label recipeTitle; //To made the title interactive it might not be textfield? Maybe a button with no boarder?
    private ImageView imageView;
    

    Recipe() {
        this.setPrefSize(1280, 20);
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;");

        imageView = new ImageView();
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
        
        recipeTitle = new Label();
        recipeTitle.setPrefSize(380, 20); // set size of text field
        recipeTitle.setTextAlignment(TextAlignment.CENTER); // Set alignment of recipe title label
        recipeTitle.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(recipeTitle); // add textlabel to task

        detail = new Button("Detail");
        detail.setPrefSize(100, 20);
        detail.setAlignment(Pos.ri)
        this.getChildren().add(detail);
    }

    public void setRecipeName(String name){
        this.recipeTitle.setText(name);
    }
    
}


class AppFrame extends BorderPane {
    private Header header;
    private Footer footer;
    private RecipeList recipeList;

    AppFrame() {
        // Initialise the header Object
        header = new Header();

        // Create a tasklist Object to hold the tasks
        recipeList = new RecipeList();
        
        // Initialise the Footer Object
        footer = new Footer();

        // TODO: Add a Scroller to the Recipe List
        ScrollPane scroller = new ScrollPane(recipeList); // Wrap the task list in a ScrollPane
        scroller.setFitToWidth(true); // Set the width to match the width of the taskList
        scroller.setFitToHeight(true); // Set the height to match the height of the taskList

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scroller);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);
    }
}