
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import javafx.application.Application;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;

public class App extends Application {

    private Model model;
    private RestController restController;
    private View view;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //AppFrame root = new AppFrame(primaryStage);                       // instantiating the entire appframe 

        /**
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        **/
        try {
            this.model = new Model();
            this.restController = new RestController(model);
            this.view = new View(primaryStage);
            this.controller = new Controller(view, model);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void stop() {
        // Called when JavaFX application is closing
        if (model.getDatabase() != null) {
            model.getDatabase().close();
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            launch(args);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

