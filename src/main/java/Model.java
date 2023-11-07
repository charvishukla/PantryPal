import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Model{
    public Model(){
        String uri = "mongodb+srv://team13:CohNSwNGemiYmOOI@cluster0.1nejphw.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println("Successfully connected to MongoDB! :)");
        }
    }
}