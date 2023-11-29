import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
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
        
        // Login Page
        this.view.getLoginPage().setCreateAccountButtonAction(this::handleCreateAccountButtonClick);
        this.view.getLoginPage().setLoginButtonAction(this::handleLoginButtonClick);

        // Create Account Page
        this.view.getCreateAccountPage().setLoginPageButtonAction(this::handleLoginPageButtonClick);
        this.view.getCreateAccountPage().setCreateAccountButtonAction(this::handleCreateAccountClick);

        //Create Frame actions.
        this.view.getCreateFrame().recordPressed(this::handleCreateFrameRecord);
        this.view.getCreateFrame().recordUnpressed(this::handleCreateFrameStopRecord);
        this.view.getCreateFrame().nextButton(this::handleCreateFrameNext);

        //Voice Input Frame actions.
        this.view.getVoiceInputFrame().recordPressed(this::handleRecordVoiceInput);
        this.view.getVoiceInputFrame().recordUnpressed(this::handleStopRecordVoiceInput);
        this.view.getVoiceInputFrame().nextButton(this::handleVoiceInputFrameNext);
       
        setupRecipeCardsDetailsAction();

    }

    private void handleLoginButtonClick(ActionEvent event) {
        Authentication authManager = new Authentication();
        String username = this.view.getLoginPage().getUsername();
        String password = this.view.getLoginPage().getPassword();
        UserSession loginDetails = authManager.login(username, password);
        if (loginDetails != null){
            view.switchScene(this.view.getAppFrame());
            this.view.getAppFrame().getHeader().setUsername(username);
        }else{
            this.view.getLoginPage().showAlert();
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
        List<String> titles = model.getDatabase().getAllTitles();
        //System.out.println(titles.get(0));
        //An arrayList to store title, ingredients, and step by step recipe.
        List<String> response = new ArrayList<>();

        for(String title : titles){
            //Generate the recipe detail page.
            response = model.getDatabase().get(title);
            RecipeDetailPage deet = new RecipeDetailPage(response);

            RecipeCard newRecipe = new RecipeCard(title, Helper._splitMealType(response));
            newRecipe.addRecipeDetail(deet);
            this.view.getAppFrame().getRecipeList().addRecipeCard(newRecipe);
            newRecipe.getDetailButton().setOnAction(e1 -> {this.view.switchScene(deet);});
            
            deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
            deet.getDetailFooter().getSaveButton().setOnAction(
                e ->    {
                        model.getDatabase().updateSteps(title, deet.getSteps());
                        this.view.switchScene(this.view.getAppFrame());
                        });
            deet.getDetailFooter().getDeleteButton().setOnAction(
                e ->    {this.view.getAppFrame().getRecipeList().deleteRecipeCardByTitle(title);
                        model.getDatabase().delete(title);
                        this.view.switchScene(this.view.getAppFrame());
                        });
        }
    }
    
    private void handleBackButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getAppFrame());
    }

    //If rocrd is started, let the microphone image glow,
    //and let the next button be available.
    private void handleCreateFrameRecord(MouseEvent event){
        this.view.getCreateFrame().getRecordButton().setEffect(new Glow(50));
        this.view.getCreateFrame().setMealType(null);
        this.view.getCreateFrame().updateNextButton();
        this.model.startRecording(); 
    }

    //If the recording is stoped, Stop recoding and 
    //call on whisper to convert audio to text.
    private void handleCreateFrameStopRecord(MouseEvent event){
        this.view.getCreateFrame().getRecordButton().setEffect(null);
        this.model.stopRecording();
        mealType = model.audioToText();
        this.view.getCreateFrame().setMealType(mealType);
        mealType = this.view.getCreateFrame().getMealType();
        this.view.getCreateFrame().updateNextButton();
    }

     //If next is clicked, switches from create frame to voice input frame.
    //And update the next frame to display what meal type you selected.
    private void handleCreateFrameNext(ActionEvent event){
        this.view.switchScene(this.view.getVoiceInputFrame());
        this.view.getVoiceInputFrame().setTitle(this.view.getCreateFrame().getMealType());
        this.view.getCreateFrame().setMealType(null);
        this.view.getCreateFrame().updateNextButton();
    }

    public void handleCreateButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getCreateFrame());
    }

    //If the record button is clicked. 
    private void handleRecordVoiceInput(MouseEvent event) {
        this.view.getVoiceInputFrame().getRecordButton().setEffect(new Glow(50));
        this.model.startRecording(); 
    }

    //If the recording is stopped, Stop recoding and 
    //call on whisper to convert audio to text.
    private void handleStopRecordVoiceInput(MouseEvent event){
         this.view.getVoiceInputFrame().getRecordButton().setEffect(null);
         this.model.stopRecording();
         ingredients = model.audioToText();
         this.view.getVoiceInputFrame().updatePrompt("Prompt recieved: " + ingredients);
         this.view.getVoiceInputFrame().updateNextButton();
    }    

    //If next is clicked, switches from voice input frame to recipe genati
    //And update the next frame to display what meal type you selected.
    private void handleVoiceInputFrameNext(ActionEvent event){
        this.view.getVoiceInputFrame().updatePrompt("Please list the ingredients you wish to cook with.");
        this.view.getVoiceInputFrame().setIngredients(null);
        this.view.getVoiceInputFrame().updateNextButton();

        String prompt = this.model.formPrompt(mealType, ingredients.substring(0, ingredients.length() - 1));
        //System.out.println(prompt);
        //response = {Title, Ingredients, Step 1, Step2, Step3, .....}
        List<String> response = this.model.parseGPTResponse(prompt);
        response.addLast(mealType);
        RecipeDetailPage deet = new RecipeDetailPage(response);
        deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        deet.getDetailFooter().getSaveButton().setOnAction(
            e ->    {
                    RecipeCard recipe = new RecipeCard(response.get(0), mealType);
                    recipe.addRecipeDetail(deet);
                    recipe.getDetailButton().setOnAction(e1 -> {this.view.switchScene(deet);});
                    if(!this.view.getAppFrame().getRecipeList().checkRecipeExists(response.get(0))) {
                        this.view.getAppFrame().getRecipeList().addRecipeCard(recipe);
                        model.getDatabase().insert(response);
                    } else {
                        model.getDatabase().updateSteps(response.get(0), deet.getSteps());
                    }
                    this.view.switchScene(this.view.getAppFrame());
                    });
        deet.getDetailFooter().getDeleteButton().setOnAction(
            e ->    {
                    this.view.getAppFrame().getRecipeList().deleteRecipeCardByTitle(response.get(0));
                    model.getDatabase().delete(response.get(0));
                    this.view.switchScene(this.view.getAppFrame());
                    });
        this.view.switchScene(deet);

    }
}