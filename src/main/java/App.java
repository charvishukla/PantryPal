
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import javafx.application.Application;
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
        Model model = new Model();
        View view = new View(primaryStage);
        Controller controller = new Controller(view, model);

    }

    public static void main(String[] args) {
        launch(args);
    }
}


// public class App {
//     public static void main( String[] args ) {

//         // Replace the placeholder with your MongoDB deployment's connection string
//         String uri = "mongodb://cshukla:dojacat123@ac-xi6azbp-shard-00-00.uw5iowu.mongodb.net:27017,ac-xi6azbp-shard-00-01.uw5iowu.mongodb.net:27017,ac-xi6azbp-shard-00-02.uw5iowu.mongodb.net:27017/?ssl=true&replicaSet=atlas-bkvh70-shard-0&authSource=admin&retryWrites=true&w=majority";

//         try (MongoClient mongoClient = MongoClients.create(uri)) {
//             MongoDatabase database = mongoClient.getDatabase("sample_mflix");
//             MongoCollection<Document> collection = database.getCollection("movies");

//             Document doc = collection.find(eq("title", "Back to the Future")).first();
//             if (doc != null) {
//                 System.out.println(doc.toJson());
//             } else {
//                 System.out.println("No matching documents found.");
//             }
//         }
//     }
// }