package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //AppFrame root = new AppFrame(primaryStage);                       // instantiating the entire appframe 

        /**
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        **/
        View view = new View();
        primaryStage.setScene(new Scene(view.getAppFrame(), 1280, 720));    // this is the size of the home screen 
        primaryStage.setTitle("PantryPal");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
