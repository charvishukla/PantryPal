

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage; // Might need to open new stage (new window)
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField; // Might not need this
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class View {
    private AppFrame appframe; //This is a scene
    private CreateFrame createframe;
    private VoiceInputFrame voiceInputFrame;
    private Stage mainStage;
    private Scene currentScene;
    // Add buttons here

    public View(Stage primaryStage) {
        this.appframe = new AppFrame();
        this.createframe = new CreateFrame();
        this.voiceInputFrame = new VoiceInputFrame();
        
        this.mainStage = primaryStage;
        this.currentScene = new Scene(this.voiceInputFrame, 1280, 720);

        mainStage.setScene(currentScene);    // this is the size of the home screen 
        mainStage.setTitle("PantryPal");
        mainStage.setResizable(true);
        mainStage.show();
    }

    public AppFrame getAppFrame() {
        return this.appframe;
    }

    public CreateFrame getCreateFrame() {
        return this.createframe;
    }

    public VoiceInputFrame getVoiceInputFrame(){
        return this.voiceInputFrame;
    }
    
    public void switchScene(Parent node) {
        this.currentScene = new Scene(node, 1280, 720);
        mainStage.setScene(currentScene);
        mainStage.show();
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
    private Button createButton;
    Footer() {
        this.setPrefSize(1280, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        createButton = new Button("Create new recipe");
        createButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(createButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getCreateButton() {
        return createButton;
    }

    public void setCreateButtonAction(EventHandler<ActionEvent> eventHandler) {
        createButton.setOnAction(eventHandler);
    }
}

class DetailFooter extends HBox {

    private Button saveButton;
    private Button backButton;
    
    DetailFooter() {
        this.setPrefSize(1280, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        backButton = new Button("Back");
        backButton.setStyle(defaultButtonStyle);

        saveButton = new Button("Save");
        saveButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(backButton, saveButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setBackButtonAction(EventHandler<ActionEvent> eventHandler) {
        backButton.setOnAction(eventHandler);
    }
}

class RecipeList extends VBox {
    RecipeList() {
        // Do we need setPrefSize? Maybe not?
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #80d838");
        //Add a testing recipe to recipelist 
        Recipe test1 = new Recipe();
        test1.setRecipeName("test1");
        this.getChildren().add(test1);
        Recipe test2 = new Recipe();
        test2.setRecipeName("test2");
        this.getChildren().add(test2);
    }

    public void setAllTitleAction(EventHandler<MouseEvent> eventHandler) {
        for(int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                ((Recipe) this.getChildren().get(i)).setTitleAction(eventHandler);;
            }
        }
    }
}

class RecipeDetails extends VBox {
    RecipeDetails() {
        // Do we need setPrefSize? Maybe not?
        // this.setPrefHeight(100);
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #40d838");

        //Add a testing recipe to recipeDetails
        TextField text = new TextField();
        text.setPrefHeight(200);
        this.getChildren().add(text);

    }

    public void setAllTitleAction(EventHandler<MouseEvent> eventHandler) {
        for(int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                ((Recipe) this.getChildren().get(i)).setTitleAction(eventHandler);;
            }
        }
    }
}


class Recipe extends HBox {

    //private Button detail;
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

        //detail = new Button("Detail");
        //detail.setPrefSize(100, 20);
        //detail.setAlignment(Pos.CENTER_RIGHT);
        //this.getChildren().add(detail);
    }

    public void setRecipeName(String name){
        this.recipeTitle.setText(name);
    }

    public Label getTitle() {
        return this.recipeTitle;
    }    

    public void setTitleAction(EventHandler<MouseEvent> eventHandler) {
        recipeTitle.setOnMouseClicked(eventHandler);
    }
}


class AppFrame extends BorderPane {
    private Header header;
    private RecipeList recipeList;
    private RecipeDetails recipeDetails;
    private Footer footer;
    private DetailFooter detailFooter;

    AppFrame() {
        // Initialise the header Object
        header = new Header();

        // Create a recipeList Object to hold the recipes
        recipeList = new RecipeList();
        recipeDetails = new RecipeDetails();
        
        // Initialise the Footer Object
        footer = new Footer();
        detailFooter = new DetailFooter();

        // TODO: Add a Scroller to the Recipe List
        ScrollPane scroller = new ScrollPane(recipeList); // Wrap the task list in a ScrollPane
        scroller.setFitToWidth(true); // Set the width to match the width of the recipeList
        scroller.setFitToHeight(true); // Set the height to match the height of the recipeList

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scroller);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);
    }
    
    public Header getHeader() {
        return this.header;
    }

    public RecipeList geRecipeList(){
        return this.recipeList;
    }

    public Footer getFooter() {
        return this.footer;
    }

    public DetailFooter getDetailFooter(){
        return this.detailFooter;
    }

    public AppFrame showList(){
        ScrollPane scroller = new ScrollPane(recipeList);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        this.setCenter(scroller);
        this.setBottom(footer);
        return this;
    }

    public AppFrame showDetail(){
        ScrollPane scroller = new ScrollPane(recipeDetails);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        this.setCenter(scroller);
        this.setBottom(detailFooter);
        return this;
    }
}


class CreateFrame extends TilePane {
    Button breakfastButton;
    Button lunchButton;
    Button dinnerButton;
    
    CreateFrame() {
        this.setOrientation(Orientation.HORIZONTAL);
        this.setHgap(30);
        this.setPrefColumns(3);
        Button breakfastButton = new Button("Breakfast");
        Button lunchButton = new Button("Lunch");
        Button dinnerButton = new Button("Dinner");

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(breakfastButton, lunchButton, dinnerButton);
    }
}



class VoiceInputFrame extends BorderPane {

    private Button pressToSpeak;

    public VoiceInputFrame(){
        pressToSpeak = new Button("Press to speak.");
        pressToSpeak.setPrefSize(200,200);

        this.setCenter(pressToSpeak);
    }

    public void setClicked(EventHandler<MouseEvent> eventHandler) {
        pressToSpeak.setOnMousePressed(eventHandler);
    }

    public void setReleased(EventHandler<MouseEvent> eventHandler){
        pressToSpeak.setOnMouseReleased(eventHandler);
    }

    public Button getPressButton(){
        return pressToSpeak;
    }

}