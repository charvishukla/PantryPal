import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class Controller{
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        this.view.getAppFrame().geRecipeList().setAllTitleAction(this::handleTitleClick);
        this.view.getAppFrame().getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        this.view.getAppFrame().getFooter().setCreateButtonAction(this::handleCreateButtonClick);
        this.view.getVoiceInputFrame().setClicked(this::handleVoiceInputButtonClicked);
        this.view.getVoiceInputFrame().setReleased(this::handleVoiceInputButtonReleased);
    }

    private void handleTitleClick(MouseEvent event) {
        this.view.getAppFrame().showDetail();
    }

    private void handleBackButtonClick(ActionEvent event) {
        this.view.getAppFrame().showList();
    }

    private void handleCreateButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getCreateFrame());
    }

    private void handleVoiceInputButtonClicked(MouseEvent event) {
        this.view.getVoiceInputFrame().getPressButton().setText("Listening");
    }

    private void handleVoiceInputButtonReleased(MouseEvent event) {
        this.view.getVoiceInputFrame().getPressButton().setText("Press to speak.");
    }
}