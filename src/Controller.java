package src;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class Controller{
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        this.view.getAppFrame().geRecipeList().setAllTitleAction(this::handleTitleClick);
    }

    private void handleTitleClick(MouseEvent event) {
        this.view.getAppFrame().showDetail();
    }
}