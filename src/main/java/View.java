
import javafx.application.Application;
// Utils 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
// Event Handling
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
// Padding and Alignment
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class View {
    private AppFrame appframe; // This is a scene
    private CreateFrame createframe;
    private VoiceInputFrame voiceInputFrame;
    private Stage mainStage;
    private Scene currentScene;

    private LoginPage loginPage;
    private CreateAccountPage createAccountPage;

    public View(Stage primaryStage) {
        this.appframe = new AppFrame();
        this.loginPage = new LoginPage();
        this.createframe = new CreateFrame();
        this.voiceInputFrame = new VoiceInputFrame();
        this.loginPage = new LoginPage();
        this.createAccountPage = new CreateAccountPage();
        this.mainStage = primaryStage;
        // the appframe scene needs to be accessed after the login page 
        //this.currentScene = new Scene(this.appframe, 1280, 720);
        this.currentScene = new Scene(this.loginPage, 1280, 720);


        mainStage.setScene(currentScene);
        mainStage.setTitle("PantryPal");
        mainStage.setResizable(true);
        mainStage.show();
    }


    public CreateAccountPage getCreateAccountPage() {
        return this.createAccountPage;
    }

    public LoginPage getLoginPage(){
        return this.loginPage;
    }

    public AppFrame getAppFrame() {
        return this.appframe;
    }

    public void returnToAppFrame() {
        this.appframe = new AppFrame(); // Create a new instance of AppFrame
        this.currentScene.setRoot(this.appframe); // Set the new instance as the root
        mainStage.show();
    }

    public CreateFrame getCreateFrame() {
        return this.createframe;
    }

    public VoiceInputFrame getVoiceInputFrame() {
        return this.voiceInputFrame;
    }

    public void switchScene(Parent node) {
        // Check if the node is already part of a scene
        if (node.getScene() != null) {
            // If it is, use the existing scene
            this.currentScene = node.getScene();
        } else {
            // Otherwise, create a new scene
            this.currentScene = new Scene(node, 1280, 720);
        }
        mainStage.setScene(currentScene);
        mainStage.show();
    }

}

class CreateAccountPage extends HBox { 
    private Label firstNameLabel;
    private TextField firstName;
    private Label lastNameLabel;
    private TextField lastName;
    private Label emailLabel;
    private TextField emailAddress; 
    private Label passwordLabel; 
    private TextField password; 
    private Label passwordLabel2;
    private TextField password2; 
    private Label phoneLabel; 
    private TextField phone;
    private Button createAccount; 
    Button loginPageButton;
    
    CreateAccountPage(){
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setStyle("-fx-background-color: #E5F4E3;");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        Label appTitle = new Label("PantryPal");
        appTitle.setFont(Font.font("Arial", FontWeight.BOLD, 64));
        appTitle.setPadding(new Insets(0, 20, 0, 0));
        appTitle.setMaxWidth(Double.MAX_VALUE);
        appTitle.setAlignment(Pos.CENTER);
        appTitle.setPrefWidth(500);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-radius: 5px;");

        String labelStyle = "-fx-font-size: 18;";
        Label createAccountLabel = new Label("Create New Account");
        createAccountLabel.setStyle("-fx-font-size: 26; -fx-font-weight: bold;");
        firstNameLabel = new Label("First Name: "); 
        firstNameLabel.setStyle(labelStyle);
        firstName = new TextField();
        
        lastNameLabel = new Label("Last Name: "); 
        lastNameLabel.setStyle(labelStyle);
        lastName = new TextField(); 
        
        emailLabel = new Label("Email Address: "); 
        emailLabel.setStyle(labelStyle);
        emailAddress = new TextField(); 

        phoneLabel = new Label("Mobile: ");
        phoneLabel.setStyle(labelStyle);
        phone = new TextField(); 
        
        passwordLabel = new Label("Enter Password: "); 
        passwordLabel.setStyle(labelStyle);
        password = new TextField(); 
        
        passwordLabel2 = new Label("Re-enter Password: "); 
        passwordLabel2.setStyle(labelStyle);
        password2 = new TextField(); 

        createAccount = new Button("Create Account");
        createAccount.setStyle("-fx-background-color: #5DA9E9 ; -fx-border-color: #CCCCCC;");
        
        String buttonStyle = "-fx-text-fill: #5DA9E9; -fx-underline: true; -fx-background-color: transparent; -fx-border-color: transparent;";
        loginPageButton = new Button("Aleady have an account?");
        loginPageButton.setStyle(buttonStyle);

        gridPane.add(createAccountLabel, 0, 0); 
        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(firstName, 1, 1);
        gridPane.add(lastNameLabel, 0, 2);
        gridPane.add(lastName, 1, 2);
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailAddress, 1, 3);
        gridPane.add(phoneLabel, 0, 4);
        gridPane.add(phone, 1, 4);
        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(password, 1, 5);
        gridPane.add(passwordLabel2, 0, 6);
        gridPane.add(password2, 1, 6);
        gridPane.add(createAccount, 1, 7); 
        GridPane.setMargin(createAccount, new Insets(10, 0, 0, 0));
        gridPane.add(loginPageButton, 1, 8); 

    
        // GridPane.setHalignment(createAccount, HPos.RIGHT);
        // GridPane.setMargin(createAccount, new Insets(10, 0, 0, 0));

       
        // GridPane.setHalignment(loginPageButton, HPos.CENTER);
        // GridPane.setMargin(loginPageButton, new Insets(10, 0, 0, 0));

       
        HBox.setHgrow(gridPane, Priority.ALWAYS);

        
        this.getChildren().addAll(appTitle, gridPane);
    }

    public void setLoginPageButtonAction(EventHandler<ActionEvent> eventHandler) {
        loginPageButton.setOnAction(eventHandler);
    }


}


class LoginPage extends HBox { 
    private Label loginLabel;
    private Label usernameLabel; 
    private TextField usernameTextField; 
    private Label passwordLabel;
    private TextField passwordTextField; 
    Button loginButton;
    Button forgotPasswordButton; 
    Button createAccountButton;

    LoginPage(){
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setStyle("-fx-background-color: #E5F4E3;");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        Label appTitle = new Label("PantryPal");
        appTitle.setFont(Font.font("Arial", FontWeight.BOLD, 64));
        appTitle.setPadding(new Insets(0, 20, 0, 0));
        appTitle.setMaxWidth(Double.MAX_VALUE);
        appTitle.setAlignment(Pos.CENTER);
        appTitle.setPrefWidth(500);
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-radius: 5px;");

        loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 26; -fx-font-weight: bold;");
        usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 18;");
        usernameTextField = new TextField();
        passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 18;");
        passwordTextField = new TextField();
        loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #5DA9E9 ; -fx-border-color: #CCCCCC;");
        forgotPasswordButton = new Button("Forgot Password?");
        createAccountButton = new Button("Don't have an account?");

        String buttonStyle = "-fx-text-fill: #5DA9E9; -fx-underline: true; -fx-background-color: transparent; -fx-border-color: transparent;";
        forgotPasswordButton.setStyle(buttonStyle);
        createAccountButton.setStyle(buttonStyle);
        
        gridPane.add(loginLabel, 1, 0);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameTextField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordTextField, 1, 2);
        gridPane.add(loginButton, 1, 3);
        GridPane.setMargin(loginButton, new Insets(10, 0, 0, 0));

        gridPane.add(forgotPasswordButton, 0, 4);
        gridPane.add(createAccountButton, 1, 4);
        
        HBox.setHgrow( gridPane, Priority.ALWAYS);
        this.getChildren().addAll(appTitle, gridPane);
    }

    public void setLoginButtonAction(EventHandler<ActionEvent> eventHandler) {
        loginButton.setOnAction(eventHandler);
    }

    public void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        createAccountButton.setOnAction(eventHandler); 
    }

    public void setForgotPasswordButtonOnAction(EventHandler<ActionEvent> eventHandler) {
        forgotPasswordButton.setOnAction(eventHandler);
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
        homeButton.setStyle(
                "-fx-padding: 10 20 10 20; -fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 30px; -fx-font-family: 'Verdana'; -fx-text-fill: #5DA9E9 !important;");

        this.profileButton = new Button("My Profile");
        profileButton.setStyle(
                "-fx-padding: 10 20 10 20; -fx-font-family: 'Verdana'; -fx-background-color: transparent; -fx-border-color: transparent; fx-text-fill: 616161; -fx-translate-y: 8;");

        this.savedRecipesButton = new Button("Saved");
        savedRecipesButton.setStyle(
                "-fx-padding: 10 20 10 20; -fx-font-family: 'Verdana';  -fx-background-color: transparent; -fx-border-color: transparent; fx-text-fill: 616161; -fx-translate-y: 8;");
        // A Region is used as a "spacer"
        // occupies all available space between the buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // add all childeren
        this.getChildren().addAll(homeButton, spacer, savedRecipesButton, profileButton);
    }

    public void setProfileButtonOnAction (EventHandler<ActionEvent> eventHandler) {
        profileButton.setOnAction(eventHandler);
        
    }

}

class Footer extends HBox {
    private Button createButton;

    Footer() {
        this.setPrefSize(1280, 90);
        this.setStyle("-fx-background-color: #FFFFFF;");
        this.setSpacing(15);
        

        createButton = new Button("Create a New Recipe");
        createButton.setStyle("-fx-background-color: #ADD8E6;  -fx-font-family: 'Verdana';  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0); -fx-padding: 7px; -fx-border-color: #D5D5D5; -fx-border-width: 0.5px; -fx-border-radius: 7.5px; -fx-background-radius: 7.5px;");

        createButton.setOnMousePressed(e -> {
            createButton.setScaleX(0.95);
            createButton.setScaleY(0.95);
        });
        createButton.setOnMouseReleased(e -> {
            createButton.setScaleX(1.0);
            createButton.setScaleY(1.0);
        });
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
    private Button deleteButton;

    DetailFooter() {
        this.setPrefSize(1280, 90);
        this.setStyle("-fx-background-color: #FFFFFF; ");
        this.setSpacing(15);
        this.setPadding(new Insets(20, 35, 10, 35));

        backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #ADD8E6;  -fx-font-family: 'Verdana';  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0); -fx-padding: 7px; -fx-border-color: #D5D5D5; -fx-border-width: 0.5px; -fx-border-radius: 7.5px; -fx-background-radius: 7.5px;");
        backButton.setOnMousePressed(e -> {
            backButton.setScaleX(0.95);
            backButton.setScaleY(0.95);
        });
        backButton.setOnMouseReleased(e -> {
            backButton.setScaleX(1.0);
            backButton.setScaleY(1.0);
        });
        saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #ADD8E6;  -fx-font-family: 'Verdana';  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0); -fx-padding: 7px; -fx-border-color: #D5D5D5; -fx-border-width: 0.5px; -fx-border-radius: 7.5px; -fx-background-radius: 7.5px;");
        saveButton.setOnMousePressed(e -> {
            saveButton.setScaleX(0.95);
            saveButton.setScaleY(0.95);
        });
        saveButton.setOnMouseReleased(e -> {
            saveButton.setScaleX(1.0);
            saveButton.setScaleY(1.0);
        });
        
        deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ADD8E6;  -fx-font-family: 'Verdana';  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0); -fx-padding: 7px; -fx-border-color: #D5D5D5; -fx-border-width: 0.5px; -fx-border-radius: 7.5px; -fx-background-radius: 7.5px;");
        deleteButton.setOnMousePressed(e -> {
            deleteButton.setScaleX(0.95);
            deleteButton.setScaleY(0.95);
        });
        deleteButton.setOnMouseReleased(e -> {
            deleteButton.setScaleX(1.0);
            deleteButton.setScaleY(1.0);
        });
        
        this.getChildren().addAll(backButton, saveButton, deleteButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setBackButtonAction(EventHandler<ActionEvent> eventHandler) {
        backButton.setOnAction(eventHandler);
    }

    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {
        deleteButton.setOnAction(eventHandler);
    }

    public void setSaveButtonAction(EventHandler<ActionEvent> eventHandler) {
        saveButton.setOnAction(eventHandler);
        
    }
}

/**
 * Class: Recipe List
 * Extends GridPane to display recipes in a grid layout
 * Each recipe is added to the grid, with a maximum of 4 recipes per row
 */
class RecipeList extends GridPane {
    private final int maxColumn = 4; // we will have 4 recipes per column

    public RecipeList() {
        this.setHgap(15); // horizontal gap in the grid
        this.setVgap(15); // vertical gap in the grid
        this.setStyle("-fx-background-color:#E5F4E3");
        this.setPadding(new Insets(25));
    }

    public void addRecipeCard(RecipeCard card) {
        for (int i = 1; i <= getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i-1);
            this.setRowIndex(currentCard, i / maxColumn);
            this.setColumnIndex(currentCard, i % maxColumn);
        }

        this.add(card, 0, 0);
    }

    public void deleteRecipeCard(RecipeCard card) {
        int index = getRecipeCards().size();
        int row = index / maxColumn;
        int column = index % maxColumn;

        for (int i = 0; i < getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i);
            if (card.getRecipeTitle().equals(currentCard.getRecipeTitle())) {
                this.getChildren().remove(currentCard);
            }
        }

        // Update indices
        for (int i = 0; i < getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i);
            this.setRowIndex(currentCard, i / maxColumn);
            this.setColumnIndex(currentCard, i % maxColumn);
        }
    }
    

    public void deleteRecipeCardByTitle(String title) {
        int index = getRecipeCards().size();
        int row = index / maxColumn;
        int column = index % maxColumn;

        for (int i = 0; i < getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i);
            if (title.equals(currentCard.getRecipeTitle())) {
                this.getChildren().remove(currentCard);
            }
        }

        // Update indices
        for (int i = 0; i < getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i);
            this.setRowIndex(currentCard, i / maxColumn);
            this.setColumnIndex(currentCard, i % maxColumn);
        }
    }
    
    public boolean checkRecipeExists(String title) {
        int index = getRecipeCards().size();
        int row = index / maxColumn;
        int column = index % maxColumn; 

        for (int i = 0; i < getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i);
            if (title.equals(currentCard.getRecipeTitle())) {
                return true;
            }
        }
        return false;
    }

    public List<RecipeCard> getRecipeCards() {
        // Assuming all children of RecipeList are RecipeCards
        return getChildren().stream()
                .filter(node -> node instanceof RecipeCard)
                .map(node -> (RecipeCard) node)
                .collect(Collectors.toList());
    }

}



class RecipeCard extends VBox {
    private String recipeTitle;
    private Button detailsButton;
    private RecipeDetailPage recipeDetailPage;

    public RecipeCard(String title) {
        this.recipeTitle = title;
        // make labels for title and description
        Label titleLabel = new Label(recipeTitle);
        //Label descriptionLabel = new Label(recipeDescription);

        // card styles
        this.setPrefSize(300, 200);
        this.setPadding(new Insets(15));
        this.setSpacing(10);
        this.setStyle(
                "-fx-border-color: transparent;  -fx-background-color: #FFFFFF;  -fx-font-family: 'Verdana';  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // details button
        detailsButton = new Button("Details");

        detailsButton.setStyle(
                "-fx-background-color: #ADD8E6;  -fx-font-family: 'Verdana';  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0); -fx-padding: 7px; -fx-border-color: #D5D5D5; -fx-border-width: 0.5px; -fx-border-radius: 7.5px; -fx-background-radius: 7.5px;");

        detailsButton.setOnMousePressed(e -> {
            detailsButton.setScaleX(0.95);
            detailsButton.setScaleY(0.95);
        });
        detailsButton.setOnMouseReleased(e -> {
            detailsButton.setScaleX(1.0);
            detailsButton.setScaleY(1.0);
        });
        detailsButton.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().addAll(titleLabel, detailsButton);
    }

    // public void setDetailsButtonAction(EventHandler<ActionEvent> event) {
    //     detailsButton.setOnAction(e -> 
    //     {Scene newScene = new Scene(recipeDetailPage);
    //     mainStage.setScene(newScene);
    //     mainStage.show();
    //     });
    // }

    public Button getDetailButton(){
        return this.detailsButton;
    }
    
    public String getRecipeTitle(){
        return this.recipeTitle;
    }
    
    public void addRecipeDetail(RecipeDetailPage detailPage){
        this.recipeDetailPage = detailPage;
    }
}




class DetailList extends VBox{
    DetailList() {
        this.setSpacing(5);
        this.setPrefSize(1280, 545);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }
}

/**
 * here, we need to fetch the recipe details from MongoDB
 */
class RecipeDetailPage extends BorderPane {
    private DetailList detailList;
    private Header header; // header
    private DetailFooter detailFooter; // footer
    private int ingredientsSize;

    RecipeDetailPage() {
        this.setStyle("-fx-background-color: #E5F4E3;");

        // Initialize the header and footer
        header = new Header();
        detailFooter = new DetailFooter();
        detailList = new DetailList();
        ScrollPane scroller = new ScrollPane(detailList);
        scroller.setFitToWidth(true); 
        scroller.setFitToHeight(true);
        
        this.setTop(header);
        this.setCenter(scroller);
        this.setBottom(detailFooter);
    }

    RecipeDetailPage(List<String> s){
        this.setStyle("-fx-background-color: #E5F4E3;");
        header = new Header();
        detailFooter = new DetailFooter();
        detailList = new DetailList();
        ScrollPane scroller = new ScrollPane(detailList);
        scroller.setFitToWidth(true); 
        scroller.setFitToHeight(true);

        Label title = new Label(s.get(0) + "\n");
        detailList.getChildren().add(title);

        String tidyIngred = s.get(1).replaceAll("\n+", "\n");
        String[] ingredList = tidyIngred.split("\n");
        ingredientsSize = 0;
        for(String i: ingredList){
            Label ingredients = new Label(i); 
            ingredients.setWrapText(true);
            detailList.getChildren().add(ingredients);
            ingredientsSize += 1;
        }
        detailList.getChildren().add(new Label(" "));
        detailList.getChildren().add(new Label("Steps: "));

        for(int i = 2; i < s.size(); i++) {
            detailList.getChildren().add(new TextField(s.get(i)));
        }

        this.setTop(header);
        this.setBottom(detailFooter);
        this.setCenter(scroller);
    }

    public DetailFooter getDetailFooter() {
        return this.detailFooter;
    }

    public List<String> getSteps(){
        List<String> steps = new ArrayList<>();
        for(int i = 3 + ingredientsSize; i < detailList.getChildren().size(); i++){
            steps.add(((TextField)detailList.getChildren().get(i)).getText());
        }
        return steps;
    }
}


class AppFrame extends BorderPane {
    private Header header;
    private RecipeList recipeList;
    private Footer footer;
    private DetailFooter detailFooter;

    AppFrame() {
        header = new Header(); // Initialise the header Object
        recipeList = new RecipeList(); // Create a recipeList Object to hold the recipes
        footer = new Footer(); // Initialise the Footer Object
        detailFooter = new DetailFooter();

        // Add a Scroller to the Recipe List
        ScrollPane scroller = new ScrollPane(recipeList); // Wrap the task list in a ScrollPane
        scroller.setFitToWidth(true); // Set the width to match the width of the recipeList
        scroller.setFitToHeight(true); // Set the height to match the height of the recipeList

        this.setTop(header); // Add header to the top of the BorderPane
        this.setCenter(scroller); // Add scroller to the centre of the BorderPane
        this.setBottom(footer); // Add footer to the bottom of the BorderPane
    }

    public Header getHeader() {
        return this.header;
    }

    public RecipeList getRecipeList() {
        return this.recipeList;
    }

    public Footer getFooter() {
        return this.footer;
    }

    public DetailFooter getDetailFooter() {
        return this.detailFooter;
    }

    public AppFrame showList() {
        ScrollPane scroller = new ScrollPane(recipeList);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        this.setCenter(scroller);
        this.setBottom(footer);
        return this;
    }

}

class CreateFrame extends BorderPane {
    Button breakfastButton;
    Button lunchButton;
    Button dinnerButton;
    private Button next;
    private String mealType;
    private Button record;

    CreateFrame() {
        this.setPadding(new javafx.geometry.Insets(100));
        //Meal type labels
        Label breakfastButton = new Label("Breakfast");
        Label lunchButton = new Label("Lunch");
        Label dinnerButton = new Label("Dinner");
        breakfastButton.setFont(new Font(20)); 
        lunchButton.setFont(new Font(20)); 
        dinnerButton.setFont(new Font(20));

        HBox hbox = new HBox();
        hbox.setSpacing(100);
        hbox.getChildren().addAll(breakfastButton, lunchButton, dinnerButton);
        hbox.setAlignment(Pos.CENTER);

        //Center prompt
        Label Title = new Label("Please verbally choose a type of meal.");
        Title.setFont(new Font(30));
        this.setTop(Title);
        setAlignment(Title, Pos.CENTER);
        this.setCenter(hbox);

        record = new Button("Record");

         //Next button to get to next scnene.
        next = new Button("Next");
        next.setVisible(false);
        next.setDisable(true);

        //Set up vbox for the bottom part of boaderpane
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.getChildren().addAll(record, next);

        //Insert vox into bottom of borderpane.
        this.setBottom(vbox);
    }

    public Button getRecordButton(){
        return record;
    }

    public void recordPressed(EventHandler<MouseEvent> eventHandler) {
            record.setOnMousePressed(eventHandler);
    }

    public void recordUnpressed(EventHandler<MouseEvent> eventHandler) {
            record.setOnMouseReleased(eventHandler);
    }

    public void nextButton(EventHandler<ActionEvent> eventHandler){
            next.setOnAction(eventHandler);
    }

    public void setMealType(String s){
        this.mealType = s;
    }

    //Will let the next button be visible if a meal type has not been selected.
    public void updateNextButton(){
        if(mealType == null){
            next.setVisible(false);
            next.setDisable(true);
        }
        else{
            next.setVisible(true);
            next.setDisable(false);
        }
    }

     //Returns the meal type the user picks.
    public String getMealType(){
        mealType = mealType.toLowerCase();
        if(mealType.contains("breakfast")){
            mealType = "breakfast";
        }
        else if(mealType.contains("dinner")){
            mealType = "dinner";
        }
        else{
            mealType = "lunch";
        }
        return mealType;
    }
}

class VoiceInputFrame extends BorderPane {

    private Label title;
    private Label prompt;
    private Button record;
    private Button next;
    private String ingredients;


    public VoiceInputFrame(){

        this.setPadding(new javafx.geometry.Insets(100));

        //Setting up the prompt to ask the user to ask for ingredients.
        prompt = new Label("Please list the ingredients you wish to cook with.");
        prompt.setFont(new Font(30));
        this.setCenter(prompt);
        setAlignment(prompt, Pos.CENTER);


        //Record button
        record = new Button("Record");
        //Next button to get to next scnene.
        next = new Button("Next");
        next.setVisible(false);
        next.setDisable(true);

        //Set up vbox for the bottom part of boaderpane
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.getChildren().addAll(record, next);

        this.setBottom(vbox);
        setAlignment(vbox, Pos.CENTER);
    }

    
    public Button getRecordButton(){
        return record;
    }

    public Button getNextButton() {
        return next;
    }

    public void nextButton(EventHandler<ActionEvent> eventHandler){
            next.setOnAction(eventHandler);
    }
    
    public void recordPressed(EventHandler<MouseEvent> eventHandler) {
        record.setOnMousePressed(eventHandler);
    }

    public void recordUnpressed(EventHandler<MouseEvent> eventHandler) {
        record.setOnMouseReleased(eventHandler);
    }

    public void updatePrompt(String s){
        this.prompt.setText(s);
        ingredients = s;
    }

    //This will set the title to indicate you have selcted breakfast, lunch or dinner.
    public void setTitle(String s){
        title = new Label("You have select " + s);
        title.setFont(new Font(30));
        this.setTop(title);
        setAlignment(title, Pos.CENTER);
    }

    //Will let the next button be visible if a meal type has not been selected.
    public void updateNextButton(){
        if(ingredients == null){
            next.setVisible(false);
            next.setDisable(true);
        }
        else{
            next.setVisible(true);
            next.setDisable(false);
        }
    }

    public void setIngredients(String s){
        ingredients = s;
    }
}