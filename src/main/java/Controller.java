import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

public class Controller {
    private View view;
    private Model model;
    private String mealType;
    private String ingredients;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        this.view.getAppFrame().getDetailFooter().setBackButtonAction(this::handleBackButtonClick);

        this.view.getAppFrame().getFooter().setCreateButtonAction(this::handleCreateButtonClick);

        //Filter and Sorting
        this.view.getAppFrame().getHeader().setFilterBoxOnAction(this::handleFilterBoxClick);
        this.view.getAppFrame().getHeader().setSortingBoxOnAction(this::handleSortingBoxClick);
        
        // Login Page
        this.view.getLoginPage().setCreateAccountButtonAction(this::handleCreateAccountButtonClick);
        this.view.getLoginPage().setLoginButtonAction(this::handleLoginButtonClick);

        // Create Account Page
        this.view.getCreateAccountPage().setLoginPageButtonAction(this::handleLoginPageButtonClick);
        this.view.getCreateAccountPage().setCreateAccountButtonAction(this::handleCreateAccountClick);

        //Create Frame actions.
        this.view.getCreateFrame().recordPressed(this::MealTypeRecord);
        this.view.getCreateFrame().recordUnpressed(this::MealTypeStopRecord);
        this.view.getCreateFrame().nextButton(this::MealTypeNextButton);

        //Voice Input Frame actions.
        this.view.getVoiceInputFrame().recordPressed(this::IngredientRecord);
        this.view.getVoiceInputFrame().recordUnpressed(this::IngredientStopRecord);
        this.view.getVoiceInputFrame().nextButton(this::IngredientNextButton);
       
        /*
         * Determines if the app has to automatically log in
         * a user.
         */
        Authentication authManager = new Authentication();
        String username = authManager.SkipLoginIfRemembered();
        if(username != null){
            view.getAppFrame().getHeader().setUsername(username);
            setupRecipeCardsDetailsAction();
            view.switchScene(this.view.getAppFrame());
        }
        

    }

    private void handleFilterBoxClick(ActionEvent event) {
        String filter = this.view.getAppFrame().getHeader().getFilterBox().getValue();
        RecipeList recipeList = this.view.getAppFrame().getRecipeList();
        List<RecipeCard> recipeCards = recipeList.getRecipeCards();
        recipeList.deleteAllOnScene();
        if(filter.equals("all")) {
            for(int i = 0; i < recipeCards.size(); i++) {
                recipeList.addRecipeCardOnScene(recipeCards.get(i));
            }
        } else {
            for(int i = 0; i < recipeCards.size(); i++) {
                RecipeCard temp = recipeCards.get(i);
                if(temp.getMealType().equals(filter)) {
                    recipeList.addRecipeCardOnScene(temp);
                }
            }
        }
    }

    private void handleSortingBoxClick(ActionEvent event) {
        String sorting = this.view.getAppFrame().getHeader().getSortingBox().getValue();
        RecipeList recipeList = this.view.getAppFrame().getRecipeList();
        List<RecipeCard> recipeCards = recipeList.getRecipeCards();
        if(sorting.equals("Alphabetically")){
            for (int i = 0; i < recipeCards.size(); i++){
                int maxIndex = i;
                for (int j = i + 1; j < recipeCards.size(); j++) {
                    if(recipeCards.get(j).getTitle().compareToIgnoreCase(recipeCards.get(maxIndex).getTitle()) > 0) {
                        maxIndex = j;
                    }
                }
                RecipeCard temp = recipeCards.get(maxIndex);
                recipeCards.set(maxIndex, recipeCards.get(i));
                recipeCards.set(i, temp);
            }
        } else if(sorting.equals("Newest to Oldest")){
            for (int i = 0; i < recipeCards.size(); i++){
                int oldestIndex = i;
                for (int j = i + 1; j < recipeCards.size(); j++) {
                    if(recipeCards.get(j).getTime().isBefore(recipeCards.get(oldestIndex).getTime())) {
                        oldestIndex = j;
                    }
                }
                RecipeCard temp = recipeCards.get(oldestIndex);
                recipeCards.set(oldestIndex, recipeCards.get(i));
                recipeCards.set(i, temp);
            }
        } else {
            for (int i = 0; i < recipeCards.size(); i++){
                int newestIndex = i;
                for (int j = i + 1; j < recipeCards.size(); j++) {
                    if(recipeCards.get(j).getTime().isAfter(recipeCards.get(newestIndex).getTime())) {
                        newestIndex = j;
                    }
                }
                RecipeCard temp = recipeCards.get(newestIndex);
                recipeCards.set(newestIndex, recipeCards.get(i));
                recipeCards.set(i, temp);
            }
        }
        recipeList.deleteAllOnScene();
        for (int i = 0; i < recipeCards.size(); i++) {
            recipeList.addRecipeCardOnScene(recipeCards.get(i));
        }
    }

    private void handleLoginButtonClick(ActionEvent event) {
        Authentication authManager = new Authentication();
        String username = this.view.getLoginPage().getUsername();
        String password = this.view.getLoginPage().getPassword();
        UserSession loginDetails = authManager.login(username, password);

        if (loginDetails != null){

            //If user selected remeber me, then we leave a mark in the database to remember. 
            if(view.getLoginPage().getAutoLoginStatus() == true){
                authManager.markAutoLoginStatus(username);
            }
            view.switchScene(this.view.getAppFrame());
            view.getAppFrame().getHeader().setUsername(username);
            setupRecipeCardsDetailsAction();
        }else{
            view.getLoginPage().showAlert();
        }
    }
 
    /**
     * Create a new account 
     * @param event
     */
    private void handleCreateAccountClick(ActionEvent event) {
        Authentication authManager = new Authentication();
        String username = this.view.getCreateAccountPage().getUsername();
        String password = this.view.getCreateAccountPage().getPassword();
        String confirmPassword = this.view.getCreateAccountPage().getConfirmPassword();
        String phone = this.view.getCreateAccountPage().getPhone();
        String firstName = this.view.getCreateAccountPage().getFirstName();
        String lastName = this.view.getCreateAccountPage().getLastName();

        if (!password.equals(confirmPassword)){
            this.view.getCreateAccountPage().showAlert("Passwords do not match");
            return;
        }
        
        if (authManager.checkUserExists(username)){
            this.view.getCreateAccountPage().showAlert("Username already exists");
            return;
        }

        if (authManager.createUser(username, confirmPassword, firstName, lastName, phone)){
            this.view.switchScene(this.view.getLoginPage());
            return;
        }else{
            this.view.getCreateAccountPage().showAlert("Something went wrong");
            return;
        }
    }
    /**
     * Navigates from Login Page to Create Account Page if the user does not have an account
     * @param event
     */
    private void handleCreateAccountButtonClick(ActionEvent event){
        view.switchScene(this.view.getCreateAccountPage());
    }

    /**
     * Navigates from Create Account Page to Login Page if the user already has an account
     * @param event
     */
    private void handleLoginPageButtonClick(ActionEvent event){
        view.switchScene(this.view.getLoginPage());
        this.view.getCreateAccountPage().clearInputs();
    }

    /**
     * 
     */
    private void setupRecipeCardsDetailsAction() {

        //Returns a list of titles of each recipe in database;
        List<String> titles = model.getDatabase().getAllTitles(this.view.getAppFrame().getHeader().getUsername()); //TODO
        //System.out.println(titles.get(0));

        //A JSONObject to store title, ingredients, and step by step recipe.
        JSONObject response;

        for(String title : titles){
            //Generate the recipe detail page.
            response = model.getDatabase().get(title); //TODO
            RecipeDetailPage deet = new RecipeDetailPage(response);

            RecipeCard newRecipe = new RecipeCard(title, response.getString("MealType"), response.getString("Time"));
            newRecipe.setImage(response.getString("Image"));
            newRecipe.addRecipeDetail(deet);
            this.view.getAppFrame().getRecipeList().addRecipeCard(newRecipe);
            newRecipe.getDetailButton().setOnAction(e1 -> {this.view.switchScene(deet);});
            
            deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
            deet.getDetailFooter().getSaveButton().setOnAction(
                e ->    {
                        model.getDatabase().updateSteps(title, deet.getSteps()); //TODO
                        this.view.switchScene(this.view.getAppFrame());
                        });
            deet.getDetailFooter().getDeleteButton().setOnAction(
                e ->    {this.view.getAppFrame().getRecipeList().deleteRecipeCard(title);
                        model.getDatabase().delete(title); //TODO
                        this.view.switchScene(this.view.getAppFrame());
                        });
            deet.getDetailFooter().getAddStepButton().setOnAction(
                e ->    {
                        TextField temp = new TextField();
                        temp.getStyleClass().add("step");
                        temp.setTranslateX(100);
                        deet.getDetailList().getChildren().addLast(temp);
                        });
            deet.getDetailFooter().getDeleteStepButton().setOnAction(
                e ->    {
                        Node last = deet.getDetailList().getChildren().getLast();
                        if(last instanceof TextField) {
                            deet.getDetailList().getChildren().remove(last);
                        }
                        });
        }
    }
    
    private void handleBackButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getAppFrame());
    }

    //If recording is started, let the microphone image glow,
    //and let the next button be available.
    private void MealTypeRecord(MouseEvent event){
        this.view.getCreateFrame().getRecordButton().setEffect(new Glow(50));
        this.view.getCreateFrame().setMealType(null);
        this.view.getCreateFrame().updateNextButton();
        this.model.startRecording(); 
    }

    //If the recording is stopped, Stop recording and 
    //call on whisper to convert audio to text.
    private void MealTypeStopRecord(MouseEvent event){
        this.view.getCreateFrame().getRecordButton().setEffect(null);
        this.model.stopRecording();
        mealType = model.audioToText();
        this.view.getCreateFrame().setMealType(mealType);
        
        if(this.view.getCreateFrame().getMealType().equals("TryAgain")){
            this.view.getCreateFrame().setMealType(null);
            this.view.getCreateFrame().updateNextButton();
            this.view.getCreateFrame().setTitle("Please try to state your meal type again.");
        }
        else{
            this.view.getCreateFrame().setMealType(mealType);
            mealType = this.view.getCreateFrame().getMealType();
            this.view.getCreateFrame().updateNextButton();
        } 
    }

    //If next is clicked, switches from create frame to voice input frame.
    //And update the next frame to display what meal type you selected.
    private void MealTypeNextButton(ActionEvent event){
        this.view.switchScene(this.view.getVoiceInputFrame());
        this.view.getVoiceInputFrame().setTitle(this.view.getCreateFrame().getMealType());
        this.view.getCreateFrame().setMealType(null);
        this.view.getCreateFrame().updateNextButton();
    }

    public void handleCreateButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getCreateFrame());
    }

    //If the record button is clicked. 
    private void IngredientRecord(MouseEvent event) {
        this.view.getVoiceInputFrame().getRecordButton().setEffect(new Glow(50));
        this.model.startRecording();
        this.view.getVoiceInputFrame().setIngredients(null); 
        this.view.getVoiceInputFrame().updateNextButton();
    }

    //If the recording is stopped, Stop recoding and 
    //call on whisper to convert audio to text.
    private void IngredientStopRecord(MouseEvent event){
         this.view.getVoiceInputFrame().getRecordButton().setEffect(null);
         this.model.stopRecording();
         ingredients = model.audioToText();
         this.view.getVoiceInputFrame().updatePrompt("Prompt received: " + ingredients);
         this.view.getVoiceInputFrame().updateNextButton();
    }    

    //If next is clicked, switches from voice input frame to recipe genati
    //And update the next frame to display what meal type you selected.
    private void IngredientNextButton(ActionEvent event){
        this.view.getVoiceInputFrame().updatePrompt("Please list the ingredients you wish to cook with.");
        this.view.getVoiceInputFrame().setIngredients(null);
        this.view.getVoiceInputFrame().updateNextButton();

        //System.out.println(prompt);
        //response = {Title, Ingredients, Step 1, Step2, Step3, .....}
        JSONObject response = this.model.getNewRecipe(mealType, ingredients.substring(0, ingredients.length() - 1)); //TODO
        response.put("User", this.view.getAppFrame().getHeader().getUsername());
        response.put("Time", Instant.now().toString());
        response.put("Image", this.model.getNewImage(response.getString("Title")));
        RecipeDetailPage deet = new RecipeDetailPage(response);
        deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        deet.getDetailFooter().getSaveButton().setOnAction(
            e ->    {
                    RecipeCard recipe = new RecipeCard(response.getString("Title"), mealType, response.getString("Time"));
                    recipe.setImage(response.getString("Image"));
                    recipe.addRecipeDetail(deet);
                    recipe.getDetailButton().setOnAction(e1 -> {this.view.switchScene(deet);});
                    if(!this.view.getAppFrame().getRecipeList().checkRecipeExists(response.getString("Title"))) {
                        this.view.getAppFrame().getRecipeList().addRecipeCard(recipe);
                        model.getDatabase().insert(response); //TODO
                    } else {
                        model.getDatabase().updateSteps(response.getString("Title"), deet.getSteps()); //TODO
                    }
                    this.view.switchScene(this.view.getAppFrame());
                    });
        deet.getDetailFooter().getDeleteButton().setOnAction(
            e ->    {
                    this.view.getAppFrame().getRecipeList().deleteRecipeCard(response.getString("Title"));
                    model.getDatabase().delete(response.getString("Title")); //TODO
                    this.view.switchScene(this.view.getAppFrame());
                    });
        deet.getDetailFooter().getAddStepButton().setOnAction(
            e ->    {
                    TextField temp = new TextField();
                    temp.getStyleClass().add("step");
                    temp.setTranslateX(100);
                    deet.getDetailList().getChildren().addLast(temp);
                    });
        deet.getDetailFooter().getDeleteStepButton().setOnAction(
            e ->    {
                    Node last = deet.getDetailList().getChildren().getLast();
                    if(last instanceof TextField) {
                        deet.getDetailList().getChildren().remove(last);
                    }
                    });
        this.view.switchScene(deet);
    }
}