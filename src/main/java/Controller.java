import java.util.List;

import javafx.event.ActionEvent;
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

    private void setupRecipeCardsDetailsAction() {
        List<RecipeCard> recipeCards = this.view.getAppFrame().getRecipeList().getRecipeCards();
        for (RecipeCard card : recipeCards) {
            card.setDetailsButtonAction(event -> showRecipeDetails());
        }
    }

    // Method to show recipe details
    private void showRecipeDetails() {
        RecipeDetailPage deet = new RecipeDetailPage();
        deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        this.view.switchScene(deet);
    }

    private void handleBackButtonClick(ActionEvent event) {
        System.out.println("Back button clicked"); 
        
        this.view.switchScene(this.view.getAppFrame());
    }

    private void handleCreateButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getCreateFrame());
    }

    //If recording is started, let the microphone image glow,
    //and let the next button be available.
    private void handleCreateFrameRecord(MouseEvent event){
        this.view.getCreateFrame().getRecordButton().setEffect(new Glow(50));
        this.view.getCreateFrame().setMealType(null);
        this.view.getCreateFrame().updateNextButton();
        this.model.startRecording(); 
    }

    //If the recording is stopped, Stop recording and 
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
        List<String> reponse = this.model.parseGPTResponse(prompt);
        RecipeDetailPage deet = new RecipeDetailPage(reponse);
        deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        this.view.switchScene(deet);

    }
}