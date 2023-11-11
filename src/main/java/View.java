import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
// Padding and Alignment
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
// imports to make the HBOX grow 
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; // Might need to open new stage (new window) 




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
        this.currentScene = new Scene(this.appframe, 1280, 720);

        mainStage.setScene(currentScene);   
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
    Button homeButton;
    Button profileButton;
    Button savedRecipesButton;
    Header() {
        // Style the header background
        this.setPrefSize(1280, 85); // Size of the header
        this.setStyle("-fx-background-color: #FFFFFF;"); // Color of the Header
        this.setPadding(new Insets(20, 35, 10, 35));
         
        // Button Styles 
        this.homeButton = new Button("Pantry Pal");
        homeButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 30px; -fx-font-family: 'Verdana'; -fx-text-fill: #5DA9E9 !important;");

        this.profileButton = new Button("My Profile");
        profileButton.setStyle("-fx-padding: 10 20 1 20; -fx-font-family: 'Verdana';   -fx-background-color: transparent; -fx-border-color: transparent; fx-text-fill: 616161; -fx-translate-y: 8;");
        
        this.savedRecipesButton = new Button("Saved");
        savedRecipesButton.setStyle("-fx-padding: 10 20 1 20; -fx-font-family: 'Verdana';   -fx-background-color: transparent; -fx-border-color: transparent; fx-text-fill: 616161; -fx-translate-y: 8;");
        // A Region is used as a "spacer"
        // occupies all available space between the buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // add all childeren
        this.getChildren().addAll(homeButton, spacer, savedRecipesButton, profileButton);
        
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

/**
 * Class: Recipe List
 * Extends GridPane to display recipes in a grid layout
 * Each recipe is added to the grid, with a maximum of 4 recipes per row
 */
class RecipeList extends GridPane {
    private final int maxColumn = 4;                                      // we will have 4 recipes per column
    public RecipeList() {
        this.setHgap(15);  // horizontal gap in the grid
        this.setVgap(15);  // vertical gap in the grid 
        this.setStyle("-fx-background-color:#E5F4E3");
        this.setPadding(new Insets(25));
        addRecipeCard(new RecipeCard("Recipe 1", "Description of Recipe 1"));
        addRecipeCard(new RecipeCard("Recipe 2", "Description of Recipe 2"));
        addRecipeCard(new RecipeCard("Recipe 3", "Description of Recipe 3"));
        addRecipeCard(new RecipeCard("Recipe 4", "Description of Recipe 4"));
        addRecipeCard(new RecipeCard("Recipe 5", "Description of Recipe 5"));
    }

        public void addRecipeCard(RecipeCard card) {
        int index = getChildren().size();
        int row = index / maxColumn;
        int column = index % maxColumn;
        
        this.add(card, column, row);
    }

}

class RecipeCard extends VBox {
    private String recipeTitle;
    private String recipeDescription;
    private Button detailsButton;
    
    public RecipeCard(String title, String description) {
        this.recipeTitle = title;
        this.recipeDescription = description;
        // make labels for title and description 
        Label titleLabel = new Label(recipeTitle);
        Label descriptionLabel = new Label(recipeDescription);

        // card styles
        this.setPrefSize(300, 200);
        this.setPadding(new Insets(15));
        this.setSpacing(10);
        this.setStyle("-fx-border-color: transparent;  -fx-background-color: #FFFFFF;");

        // details button
        detailsButton = new Button("Details");
        detailsButton.setStyle("-fx-background-color: #ADD8E6; -fx-font-weight: bold;");
        
        // Event handler for details button
        detailsButton.setOnAction(event -> showRecipeDetails());

        this.getChildren().addAll(titleLabel, descriptionLabel, detailsButton);    
    }

    private void showRecipeDetails() {
        RecipeDetailPage detailPage = new RecipeDetailPage();
        Scene detailScene = new Scene(detailPage, 1280, 720); // Create a new scene with the detail page
        
        // Get the current stage and set the scene
        Stage currentStage = (Stage) this.getScene().getWindow();
        currentStage.setScene(detailScene);
    }

}

/**
 * // here, we need to fetch the recipe details from MongoDB
 */
class RecipeDetailPage extends TilePane {
    private Label label;
    private Header header;
    private DetailFooter detailFooter;
    
    RecipeDetailPage() {
        header = new Header();
        label = new Label("HIIIII");
        detailFooter = new DetailFooter();
        this.getChildren().addAll(header, label, detailFooter);
    }
}



/**
 * Class: Recipe
 * Store stuff from the Chat GPT response into this object
 * 
 */
class Recipe{
    private String recipeTitle;                                                 // name of the recipe
    private String description;
    private String mealType;                                                    // type of the meal as selected by the user
    private Map< String, String> ingredients;                                   // a map of ingredients and their quantities        
    private List<String> directions;                                            // a list of ingredients 
    
    Recipe(){
        this.recipeTitle = recipeTitle; 
        this.description = description;
        this.mealType = mealType; 
        this.ingredients = ingredients; 
        this.directions = directions;
    }

    // getters and setters for Recipe  
    public String getTitle() {
        return recipeTitle;
    }

    public void setTitle(String title) {
        this.recipeTitle = title;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}   




class AppFrame extends BorderPane {
    private Header header;
    private RecipeList recipeList;
    //private RecipeDetails recipeDetails;
    private Footer footer;
    private DetailFooter detailFooter;

    AppFrame() {
        // Initialise the header Object
        header = new Header();

        // Create a recipeList Object to hold the recipes
        recipeList = new RecipeList();
       // recipeDetails = new RecipeDetails();
        
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
        ScrollPane scroller = new ScrollPane();
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
        this.breakfastButton = new Button("Breakfast");
        this.lunchButton = new Button("Lunch");
        this.dinnerButton = new Button("Dinner");

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(breakfastButton, lunchButton, dinnerButton);
    }

    public Button getBreakfastButton() {
        return this.breakfastButton;
    }

    public Button getLunchButton() {
        return this.lunchButton;
    }

    public Button getDinnerButton() {
        return this.dinnerButton;
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