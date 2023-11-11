import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        this.view.getAppFrame().getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        this.view.getAppFrame().getFooter().setCreateButtonAction(this::handleCreateButtonClick);
        this.view.getVoiceInputFrame().setClicked(this::handleVoiceInputButtonClicked);
        this.view.getVoiceInputFrame().setReleased(this::handleVoiceInputButtonReleased);
       
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
        // this.view.mainStage.setScene(this.view.getAppFrame());
    }

    

    private void handleCreateButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getCreateFrame());
    }

    private void handleVoiceInputButtonClicked(MouseEvent event) {
        System.out.println("here");
        this.view.getVoiceInputFrame().getPressButton().setText("Listening");
    }

    private void handleVoiceInputButtonReleased(MouseEvent event) {
        this.view.getVoiceInputFrame().getPressButton().setText("Press to speak.");
    }

    
}