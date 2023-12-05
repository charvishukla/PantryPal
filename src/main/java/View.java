
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;

// Utils 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage; // Might need to open new stage (new window) 
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class: View
 * responsible for initializing and switching
 * between different frames and pages within the application.
 */
public class View {

    private AppFrame appframe;
    private CreateFrame createframe;
    private VoiceInputFrame voiceInputFrame;
    private Stage mainStage;
    private Scene currentScene;
    private LoginPage loginPage;
    private CreateAccountPage createAccountPage;

    /**
     * Initializes various frames and pages, sets up the primary stage and the
     * initial scene.
     * 
     * @param primaryStage: The primary stage for this application, onto which
     *                      scenes are set.
     */
    public View(Stage primaryStage) {
        // Initialization of frames and pages.
        this.appframe = new AppFrame();
        this.loginPage = new LoginPage();
        this.createframe = new CreateFrame();
        this.voiceInputFrame = new VoiceInputFrame();
        this.loginPage = new LoginPage();
        this.createAccountPage = new CreateAccountPage();
        this.mainStage = primaryStage;


        
        // Setting the initial scene to be the login page.
        this.currentScene = new Scene(this.loginPage, 1280, 720);
        
        // src/main/resources/stylesheets/LoginPage.css
        // Configuring the main stage with the initial scene and showing it.
        mainStage.setScene(currentScene);
        mainStage.setTitle("PantryPal");
        mainStage.setResizable(true);
        mainStage.show();
    }

    // GETTERS AND SETTERS FOR VIEW
    public CreateAccountPage getCreateAccountPage() {
        return this.createAccountPage;
    }

    public LoginPage getLoginPage() {
        return this.loginPage;
    }

    public AppFrame getAppFrame() {
        return this.appframe;
    }

    public CreateFrame getCreateFrame() {
        return this.createframe;
    }

    public VoiceInputFrame getVoiceInputFrame() {
        return this.voiceInputFrame;
    }

    /**
     * Method: returnToAppFrame
     * Resets the app frame and sets it as the current scene.
     * use this to return to the app frame from other scenes.
     */
    public void returnToAppFrame() {
        this.appframe = new AppFrame(); // Create a new instance of AppFrame
        this.currentScene.setRoot(this.appframe); // Set the new instance as the root
        mainStage.show();
    }

    /**
     * Method: switchScene
     * Switches the current scene to the provided node.
     * If the node is already part of a scene, that scene is used; otherwise, a new
     * scene is created.
     * 
     * @param node: The parent node for the new scene.
     */

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

/**
 * Class: CreateAccountPage
 */
class CreateAccountPage extends HBox {
    private Label firstNameLabel;
    private TextField firstName;
    private Label lastNameLabel;
    private TextField lastName;
    private Label usernameLabel;
    private TextField username;
    private Label passwordLabel;
    private TextField password;
    private Label passwordLabel2;
    private TextField password2;
    private Label phoneLabel;
    private TextField phone;
    private Button createAccount;
    Button loginPageButton;

    /**
     * 
     */
    CreateAccountPage() {
        this.getStyleClass().add("create-account-page");
        this.getStylesheets().add(getClass().getResource("/stylesheets/CreateAccountPage.css").toExternalForm());

        Circle shape1 = new Circle(120);
        shape1.getStyleClass().add("shape-blue");
        shape1.setTranslateX(630);
        shape1.setTranslateY(-300);
        shape1.setEffect(new GaussianBlur(10));

        Circle shape2 = new Circle(100);
        shape2.getStyleClass().add("shape-orange");
        shape2.setTranslateX(810);
        shape2.setTranslateY(291);
        shape2.setEffect(new GaussianBlur(10));

        this.setPadding(new Insets(20, 0, 20, 0));
        this.getStyleClass().add("create-account-page-background");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        Label appTitle = new Label("PantryPal");
        appTitle.getStyleClass().add("app-title");
        appTitle.setMinWidth(Control.USE_PREF_SIZE);
        appTitle.setMaxWidth(Double.MAX_VALUE);
        appTitle.setTranslateX(-250);

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setMinWidth(580);
        gridPane.setPrefHeight(200);
        gridPane.setTranslateX(-95);
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label createAccountLabel = new Label("Create New Account");
        createAccountLabel.getStyleClass().add("label-create-account");

        firstNameLabel = new Label("First Name: ");
        firstNameLabel.getStyleClass().add("label-common");
        firstName = new TextField();
        firstName.getStyleClass().add("text-field");

        lastNameLabel = new Label("Last Name: ");
        lastNameLabel.getStyleClass().add("label-common");
        lastName = new TextField();
        lastName.getStyleClass().add("text-field");

        usernameLabel = new Label("Username: ");
        usernameLabel.getStyleClass().add("label-common");
        username = new TextField();
        username.getStyleClass().add("text-field");

        phoneLabel = new Label("Mobile: ");
        phoneLabel.getStyleClass().add("label-common");
        phone = new TextField();
        phone.getStyleClass().add("text-field");

        passwordLabel = new Label("Enter Password: ");
        passwordLabel.getStyleClass().add("label-common");
        password = new TextField();
        password.getStyleClass().add("text-field");

        passwordLabel2 = new Label("Re-enter Password: ");
        passwordLabel2.getStyleClass().add("label-common");
        password2 = new TextField();
        password2.getStyleClass().add("text-field");
        createAccount = new Button("Create Account");
        createAccount.getStyleClass().add("button-create;");
        loginPageButton = new Button("Aleady have an account?");
        loginPageButton.getStyleClass().add("link-button-style");

        gridPane.add(createAccountLabel, 0, 0);

        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(firstName, 1, 1);
        gridPane.add(lastNameLabel, 0, 2);
        gridPane.add(lastName, 1, 2);
        gridPane.add(usernameLabel, 0, 3);
        gridPane.add(username, 1, 3);
        gridPane.add(phoneLabel, 0, 4);
        gridPane.add(phone, 1, 4);
        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(password, 1, 5);
        gridPane.add(passwordLabel2, 0, 6);
        gridPane.add(password2, 1, 6);

        gridPane.add(createAccount, 1, 7);
        GridPane.setMargin(createAccount, new Insets(10, 0, 0, 0));
        gridPane.add(loginPageButton, 1, 8);

        HBox.setHgrow(gridPane, Priority.ALWAYS);

        this.getChildren().addAll(shape1, shape2, appTitle, gridPane);
    }

    public void setLoginPageButtonAction(EventHandler<ActionEvent> eventHandler) {
        loginPageButton.setOnAction(eventHandler);
    }

    public void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        createAccount.setOnAction(eventHandler);
    }

    public String getFirstName() {
        return this.firstName.getText();
    }

    public String getLastName() {
        return this.lastName.getText();
    }

    public String getUsername() {
        return this.username.getText();
    }

    public String getPhone() {
        return this.phone.getText();
    }

    public String getPassword() {
        return this.password.getText();
    }

    public String getConfirmPassword() {
        return this.password2.getText();
    }

    /**
     * 
     * @param message
     */
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error creating an account!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * 
     */
    public void clearInputs() {
        this.firstName.setText("");
        this.lastName.setText("");
        this.username.setText("");
        this.password.setText("");
        this.password2.setText("");
        this.phone.setText("");
    }

}

/**
 * Class: LoginPage
 */
class LoginPage extends HBox {
    private Label loginLabel;
    private Label usernameLabel;
    private TextField usernameTextField;
    private Label passwordLabel;
    private TextField passwordTextField;
    Button loginButton;
    Button forgotPasswordButton;
    Button createAccountButton;
    private CheckBox autoLoginCheckBox;

    /**
     * 
     */
    LoginPage() {
        this.getStyleClass().add("login-page");
        this.getStylesheets().add(getClass().getResource("/stylesheets/LoginPage.css").toExternalForm());

        Circle shape1 = new Circle(120);
        shape1.getStyleClass().add("shape-blue");
        shape1.setTranslateX(630);
        shape1.setTranslateY(-300);
        shape1.setEffect(new GaussianBlur(10));

        Circle shape2 = new Circle(100);
        shape2.getStyleClass().add("shape-orange");
        shape2.setTranslateX(810);
        shape2.setTranslateY(291);
        shape2.setEffect(new GaussianBlur(10));

        this.setPadding(new Insets(20, 0, 20, 0));
        this.getStyleClass().add("login-page-background");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        Label appTitle = new Label("PantryPal");
        appTitle.getStyleClass().add("app-title");

        appTitle.setMinWidth(Control.USE_PREF_SIZE);
        appTitle.setMaxWidth(Double.MAX_VALUE);
        appTitle.setTranslateX(-300);

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setPrefWidth(700);
        gridPane.setPrefHeight(200);
        gridPane.setTranslateX(-55);
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        loginLabel = new Label("Login");
        loginLabel.getStyleClass().add("label-login");

        usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("label-common");

        usernameTextField = new TextField();
        usernameTextField.getStyleClass().add("text-field");

        passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label-common");

        passwordTextField = new TextField();
        passwordTextField.getStyleClass().add("text-field");

        loginButton = new Button("Login");
        loginButton.getStyleClass().add("button-login");

        forgotPasswordButton = new Button("Forgot Password?");
        forgotPasswordButton.getStyleClass().add("link-button-style");

        createAccountButton = new Button("Don't have an account?");
        createAccountButton.getStyleClass().add("link-button-style");

        autoLoginCheckBox = new CheckBox("Remember me");

        autoLoginCheckBox.getStyleClass().add("checkbox-style");


        gridPane.add(loginLabel, 1, 0);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameTextField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordTextField, 1, 2);
        gridPane.add(loginButton, 1, 3);
        GridPane.setMargin(loginButton, new Insets(10, 0, 0, 0));
        gridPane.add(autoLoginCheckBox, 1, 5);

        gridPane.add(forgotPasswordButton, 0, 6);
        gridPane.add(createAccountButton, 1, 6);

        HBox.setHgrow(gridPane, Priority.ALWAYS);
        this.getChildren().addAll(shape1, shape2, appTitle, gridPane);
    }

    /**
     * 
     * @param eventHandler
     */
    public void setLoginButtonAction(EventHandler<ActionEvent> eventHandler) {
        loginButton.setOnAction(eventHandler);
    }

    /**
     * 
     * @param eventHandler
     */
    public void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        createAccountButton.setOnAction(eventHandler);
    }

    /**
     * 
     * @param eventHandler
     */
    public void setForgotPasswordButtonOnAction(EventHandler<ActionEvent> eventHandler) {
        forgotPasswordButton.setOnAction(eventHandler);
    }

    // GETTERS METHODS
    public String getUsername() {
        return this.usernameTextField.getText();
    }

    public String getPassword() {
        return this.passwordTextField.getText();
    }

    /**
     * 
     */
    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication Error");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password. Please try again.");
        alert.showAndWait();
    }

    /**
     * 
     * @return
     */
    public boolean getAutoLoginStatus() {
        return autoLoginCheckBox.isSelected();
    }

}

/**
 * Class: Header
 */
class Header extends HBox {
    Button homeButton;
    Button profileButton;
    Button logoutButton;
    Button savedRecipesButton;
    String username;
    ComboBox<String> filterBox;

    Header() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Chillight-EaVR9.ttf"), 36);
        this.getStylesheets().add(getClass().getResource("/stylesheets/Header.css").toExternalForm());
        this.getStyleClass().add("header-background");
        this.homeButton = new Button("Pantry Pal");
        homeButton.getStyleClass().add("pantry-pal-button");

        this.setPrefSize(1280, 85); // Size of the header
        this.setPadding(new Insets(20, 35, 10, 35));

        this.profileButton = new Button("My Profile");
        profileButton.getStyleClass().add("my-profile-button");
        profileButton.setTranslateY(25);

        this.savedRecipesButton = new Button("Saved");
        savedRecipesButton.setStyle(
                "-fx-padding: 10 20 10 20; -fx-font-family: 'Verdana';  -fx-background-color: transparent; -fx-border-color: transparent; fx-text-fill: 616161; -fx-translate-y: 8;");

        Label filterLabel = new Label("Filter:");
        filterLabel.setStyle(
                "-fx-padding: 10 20 10 20; -fx-font-family: 'Verdana';  -fx-background-color: transparent; -fx-border-color: transparent; fx-text-fill: 616161; -fx-translate-y: 8;");
        String[] filter = {"all", "breakfast", "lunch", "dinner"};
        this.filterBox = new ComboBox<String>(FXCollections.observableArrayList(filter));
        filterBox.setStyle(
                "-fx-padding: 10 20 10 20; -fx-translate-y: 8;");
        filterBox.getSelectionModel().selectFirst();
        // A Region is used as a "spacer"
        // occupies all available space between the buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // add all childeren
        this.getChildren().addAll(homeButton, spacer, filterLabel, filterBox, savedRecipesButton, profileButton);
    }

    public void setProfileButtonOnAction(EventHandler<ActionEvent> eventHandler) {
        profileButton.setOnAction(eventHandler);
    }

    public void setUsername(String username) {
        this.profileButton.setText(username);
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public ComboBox<String> getFilterBox(){
        return this.filterBox;
    }

    public void setFilterBoxOnAction (EventHandler<ActionEvent> eventHandler) {
        filterBox.setOnAction(eventHandler);
    }

}

/**
 * Class: Footer
 * This is the footer used in the HomePage
 */
class Footer extends HBox {
    private Button createButton;

    /**
     * Default constructor for the he footer takes no arguments, initalizes a button
     * object
     * and loads its stylesheet
     */
    Footer() {
        this.getStylesheets().add(getClass().getResource("/stylesheets/Footer.css").toExternalForm());
        this.getStyleClass().add("footer-background");
        this.setPrefSize(1280, 90);
        this.setSpacing(15);
        createButton = new Button("Create a New Recipe");
        createButton.getStyleClass().add("create-button");

        createButton.setOnMouseEntered(e -> {
            createButton.setEffect(new DropShadow(30, Color.rgb(255, 255, 255, .6)));
            createButton.setStyle("-fx-background-color: #23a2f6; -fx-text-fill: #fff;");
        });

        createButton.setOnMouseExited(e -> {
            createButton.setEffect(null);
            createButton.setStyle("-fx-background-color: transparent; -fx-text-fill: fff;");
        });

        this.getChildren().addAll(createButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    // Getter for the create button
    public Button getCreateButton() {
        return createButton;
    }

    /**
     * 
     * @param eventHandler
     */
    public void setCreateButtonAction(EventHandler<ActionEvent> eventHandler) {
        createButton.setOnAction(eventHandler);
    }
}

class DetailFooter extends HBox {
    private Button saveButton;
    private Button backButton;
    private Button deleteButton;

    DetailFooter() {
        this.getStyleClass().add("detail-footer");
        this.getStylesheets().add(getClass().getResource("/stylesheets/DetailFooter.css").toExternalForm());

        this.setPrefSize(1280, 90);

        this.setSpacing(15);
        this.setPadding(new Insets(20, 35, 10, 35));

        backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        saveButton = new Button("Save");
        saveButton.getStyleClass().add("button");
        deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("button");

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

 class RecipeList extends VBox {
    private final int maxColumn = 3; // we will have 4 recipes per column
    private Label myRecipes;
    private GridPane gridPane;

    public RecipeList() {
        // import stylesheet
        Font.loadFont(getClass().getResourceAsStream("/fonts/Chillight-EaVR9.ttf"), 36);
        this.getStyleClass().add("recipe-list-page");
        this.getStylesheets().add(getClass().getResource("/stylesheets/RecipeListPage.css").toExternalForm());

        // create heading lable
        myRecipes = new Label("My Recipes");
        myRecipes.getStyleClass().add("my-recipes");
        myRecipes.setTranslateX(470);

        gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setPrefWidth(1240);
        gridPane.setTranslateX(75);
        GridPane.setMargin(myRecipes, new Insets(25, 25, 25, 25));
        gridPane.add(myRecipes, 0, 0);
        gridPane.setHgap(47);
        gridPane.setVgap(35);
    

        // SHAPES FOR STYLE
        Circle shape1 = new Circle(120);
        shape1.getStyleClass().add("shape-pink");
        shape1.setTranslateX(630);
        shape1.setTranslateY(50);
        shape1.setEffect(new GaussianBlur(10));

        Circle shape2 = new Circle(130);
        shape2.getStyleClass().add("shape-blue");
        shape2.setTranslateX(630);
        shape2.setTranslateY(-100);
        shape2.setEffect(new GaussianBlur(10));

        Circle shape3 = new Circle(60);
        shape3.getStyleClass().add("shape-yellow");
        shape3.setTranslateX(630);
        shape3.setTranslateY(-10);
        shape3.setEffect(new GaussianBlur(10));

        this.getChildren().addAll(myRecipes, gridPane);
    }

    public void addRecipeCard(RecipeCard card) {
    // Add the card first
        int numCards = getRecipeCards().size() ;
        int row_idx = numCards / maxColumn; 
        int col_idx = numCards % maxColumn;
        
        this.gridPane.add(card, col_idx, row_idx);
    }

    public void deleteRecipeCard(String title) {
        int index = getRecipeCards().size();
        for (int i = 0; i < getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i);
            if (title.equals(currentCard.getRecipeTitle())) {
                this.getChildren().remove(currentCard);
            }
        }

        // Update indices
        for (int i = 0; i < getRecipeCards().size(); i++) {
            RecipeCard currentCard = getRecipeCards().get(i);
            GridPane.setRowIndex(currentCard, i / maxColumn);
            GridPane.setColumnIndex(currentCard, i % maxColumn);
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
        return this.gridPane.getChildren().stream()
                .filter(node -> node instanceof RecipeCard)
                .map(node -> (RecipeCard) node)
                .collect(Collectors.toList());
    }
}

/**
 * Class: RecipeCard
 */
class RecipeCard extends VBox {
    
    private static final double CARD_WIDTH = 330;
    private static final double CARD_HEIGHT = 360; 
    private String recipeTitle;
    private String mealType;
    private Button detailsButton;
    private ImageView imageView;
    private RecipeDetailPage recipeDetailPage;

    public RecipeCard(String title, String mealType) {
        this.getStyleClass().add("recipe-card");
        this.getStylesheets().add(getClass().getResource("/stylesheets/RecipeCard.css").toExternalForm());
       
        this.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        this.setMinSize(CARD_WIDTH, CARD_HEIGHT);
        this.setMaxSize(CARD_WIDTH, CARD_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));
        
        this.imageView = new ImageView();
        this.imageView.setFitHeight(240);
        this.imageView.setFitWidth(280);
        
        this.recipeTitle = title;
        this.mealType = mealType;

        Label titleLabel = new Label(recipeTitle);
        titleLabel.getStyleClass().add("title");

        Label mealTypeLabel = new Label(mealType);
        mealTypeLabel.getStyleClass().add("meal-type");

        detailsButton = new Button("Learn more");
        detailsButton.getStyleClass().add("button");

        this.getChildren().addAll(imageView, titleLabel, mealTypeLabel, detailsButton);
    }

    public Button getDetailButton(){

        return this.detailsButton;
    }

    public String getRecipeTitle() {
        return this.recipeTitle;
    }

    public String getMealType() {
        return this.mealType;
    }

    public RecipeDetailPage getRecipeDetailPage() {
        return this.recipeDetailPage;
    }

    public void addRecipeDetail(RecipeDetailPage detailPage) {
        this.recipeDetailPage = detailPage;
    }
}


class DetailList extends VBox {

    DetailList() {
        this.setSpacing(5);
        this.setPrefSize(1280, 545);
        this.setStyle("-fx-background-color: black;");
    }
}


class ServerDownPage extends VBox {
    private Label label1; 
    private Label label2; 
    private Label label3;
    private ImageView imageView; 
    private Image image; 

    ServerDownPage(){
        Font.loadFont(getClass().getResourceAsStream("/fonts/Chillight-EaVR9.ttf"), 32);
        this.getStyleClass().add("server-down-page");
        this.getStylesheets().add(getClass().getResource("/stylesheets/ServerDown.css").toExternalForm());
        
        label1 = new Label("Whoops!"); 
        label1.getStyleClass().add("label-common");
        image = new Image("/resources/cat.jpg");
        imageView = new ImageView(image);
        label2 = new Label("Looks like this page went on Vacation!");
        label2.getStyleClass().add("label-common");
        label3 = new Label("Try again later");


        this.getChildren().addAll(label1, imageView, label2, label3);
        
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
    private ImageView imageView;

    RecipeDetailPage() {
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


       
        // initializing 
    RecipeDetailPage(JSONObject json){
        Font.loadFont(getClass().getResourceAsStream("/fonts/Chillight-EaVR9.ttf"), 32);
        this.getStyleClass().add("recipe-detail-page");
        this.getStylesheets().add(getClass().getResource("/stylesheets/RecipeDetailPage.css").toExternalForm());
        header = new Header();
        detailFooter = new DetailFooter();
        detailList = new DetailList();
        imageView = new ImageView();
        
        // make scroller
        ScrollPane scroller = new ScrollPane(detailList);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);

        //image view 
        this.imageView.setFitHeight(300);
        this.imageView.setFitWidth(500);
        this.setAlignment(imageView, Pos.CENTER_LEFT);
        detailList.getChildren().add(imageView);
    
        
        Label title = new Label(json.getString("Title"));
        detailList.getChildren().add(title);

        title.getStyleClass().add("recipe-detail-title");
        title.setTranslateX(100);
        detailList.getChildren().add(title);

        String[] ingredList = json.getString("Ingredients").replaceAll("\n+", "\n").split("\n");
        ingredientsSize = 0;
        for (String i : ingredList) {
            Label ingredients = new Label(i);
            ingredients.getStyleClass().add("ingredientsList");
            ingredients.setAlignment(Pos.BASELINE_CENTER);
            ingredients.setTranslateX(100);
            ingredients.setWrapText(true);
            detailList.getChildren().add(ingredients);
            ingredientsSize += 1;
        }

        detailList.getChildren().add(new Label(" ")); // ??

        Label stepsLabel = new Label("Steps: ");
        stepsLabel.setTranslateX(100);
        stepsLabel.getStyleClass().add("steps-label");
        detailList.getChildren().add(stepsLabel);
        
        // Add a text field for each step
        for(int i = 1; i <= json.getInt("numSteps"); i++) {
            TextField step = new TextField(json.getString(String.valueOf(i)));
            step.getStyleClass().add("step");
            step.setTranslateX(100);
            detailList.getChildren().add(step);
        }

        this.setTop(header);
        this.setBottom(detailFooter);
        this.setCenter(scroller);
    }

    public DetailFooter getDetailFooter() {
        return this.detailFooter;
    }

    public List<String> getSteps() {
        List<String> steps = new ArrayList<>();
        for (int i = 3 + ingredientsSize; i < detailList.getChildren().size(); i++) {
            steps.add(((Label) detailList.getChildren().get(i)).getText());
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
        BorderPane borderPane = new BorderPane();
        recipeList = new RecipeList(); // Create a recipeList Object to hold the recipes
        borderPane.setCenter(recipeList);
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
    private Button next;
    private String mealType;
    private Button record;
    private Label Title;

    CreateFrame() {
        this.setPadding(new javafx.geometry.Insets(100));
        // Meal type labels
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
        Title = new Label("Please verbally choose a type of meal.");
        Title.setFont(new Font(30));
        this.setTop(Title);
        setAlignment(Title, Pos.CENTER);
        this.setCenter(hbox);

        record = new Button("Record");

        // Next button to get to next scnene.
        next = new Button("Next");
        next.setVisible(false);
        next.setDisable(true);

        // Set up vbox for the bottom part of boaderpane
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.getChildren().addAll(record, next);

        // Insert vox into bottom of borderpane.
        this.setBottom(vbox);
    }

    public Button getRecordButton() {
        return record;
    }

    public void recordPressed(EventHandler<MouseEvent> eventHandler) {
        record.setOnMousePressed(eventHandler);
    }

    public void recordUnpressed(EventHandler<MouseEvent> eventHandler) {
        record.setOnMouseReleased(eventHandler);
    }

    public void nextButton(EventHandler<ActionEvent> eventHandler) {
        next.setOnAction(eventHandler);
    }

    public void setMealType(String s) {
        this.mealType = s;
    }

    // Will let the next button be visible if a meal type has not been selected.
    public void updateNextButton() {
        if (mealType == null) {
            next.setVisible(false);
            next.setDisable(true);
        } else {
            next.setVisible(true);
            next.setDisable(false);
        }
    }

    // Returns the meal type the user picks.
    public String getMealType() {
        mealType = mealType.toLowerCase();
        if (mealType.contains("breakfast")) {
            mealType = "breakfast";
        } else if (mealType.contains("dinner")) {
            mealType = "dinner";
        }
        else if(mealType.contains("lunch")){
            mealType = "lunch";
        }
        else{
            mealType = "TryAgain";
        }
        return mealType;
    }

    public void setTitle(String s){
        Title.setText(s);
        Title.setFont(new Font(30));
    }
}

class VoiceInputFrame extends BorderPane {

    private Label title;
    private Label prompt;
    private Button record;
    private Button next;
    private String ingredients;

    public VoiceInputFrame() {

        this.setPadding(new javafx.geometry.Insets(100));

        // Setting up the prompt to ask the user to ask for ingredients.
        prompt = new Label("Please list the ingredients you wish to cook with.");
        prompt.setFont(new Font(30));
        this.setCenter(prompt);
        setAlignment(prompt, Pos.CENTER);

        // Record button
        record = new Button("Record");
        // Next button to get to next scnene.
        next = new Button("Next");
        next.setVisible(false);
        next.setDisable(true);

        // Set up vbox for the bottom part of boaderpane
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.getChildren().addAll(record, next);

        this.setBottom(vbox);
        setAlignment(vbox, Pos.CENTER);
    }

    public Button getRecordButton() {
        return record;
    }

    public Button getNextButton() {
        return next;
    }

    public void nextButton(EventHandler<ActionEvent> eventHandler) {
        next.setOnAction(eventHandler);
    }

    public void recordPressed(EventHandler<MouseEvent> eventHandler) {
        record.setOnMousePressed(eventHandler);
    }

    public void recordUnpressed(EventHandler<MouseEvent> eventHandler) {
        record.setOnMouseReleased(eventHandler);
    }

    public void updatePrompt(String s) {
        this.prompt.setText(s);
        ingredients = s;
    }

    // This will set the title to indicate you have selcted breakfast, lunch or
    // dinner.
    public void setTitle(String s) {
        title = new Label("You have select " + s);
        title.setFont(new Font(30));
        this.setTop(title);
        setAlignment(title, Pos.CENTER);
    }

    // Will let the next button be visible if a meal type has not been selected.
    public void updateNextButton() {
        if (ingredients == null) {
            next.setVisible(false);
            next.setDisable(true);
        } else {
            next.setVisible(true);
            next.setDisable(false);
        }
    }

    public void setIngredients(String s) {
        ingredients = s;
    }
}