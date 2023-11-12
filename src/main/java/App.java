
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
    @Override
    public void start(Stage primaryStage) throws Exception {
        //AppFrame root = new AppFrame(primaryStage);                       // instantiating the entire appframe 

        /**
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        **/
        Model model = new Model();
        View view = new View(primaryStage);
        Controller controller = new Controller(view, model);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

