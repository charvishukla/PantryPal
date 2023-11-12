import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Updates.set;

import java.util.List;
import java.util.ArrayList;

public class Model{
    private String uri;
    private MongoDatabase database;
    private MongoCollection<Document> recipeCollection;

    public Model(){
        this.uri = "mongodb+srv://team13:CohNSwNGemiYmOOI@cluster0.1nejphw.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            this.database = mongoClient.getDatabase("PantryPal");
            this.recipeCollection = database.getCollection("Recipe");
            System.out.println("Successfully connected to MongoDB! :)");

            //test insert
            /** 
            List<String> test = new ArrayList<>();
            test.add("Title 1");
            test.add("Ingredient A B C D");
            test.add("Step 1: Add water");
            test.add("Step 2: Boil");
            this.insert(test);
            **/

            //test get
            /** 
            List<String> test1 = new ArrayList<>();
            test1 = this.get("Title 1");
            System.out.println("Testing get");
            System.out.println(test1.get(0));
            **/

            //test delete
            /**
            this.delete("Title 1");
            **/

            //test update
            /** 
            this.updateIngredient("Title 1", "new Ingredent update");
            List<String> test2 = new ArrayList<>();
            test2.add("Step 1: Add water");
            test2.add("Step 2: Boil");
            test2.add("Step 3: Bake");
            test2.add("Step 4: Eat");
            this.updateSteps("Title 1", test2);
            **/
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }       
    }

    public void insert(List<String> recipeDetail) {
        List<Document> stepList = new ArrayList<>();
        for(int i = 2; i < recipeDetail.size(); i++) {
            stepList.add(new Document("Step", recipeDetail.get(i)));
        }

        Document recipe = new Document("_id", new ObjectId());
        recipe.append("Title", recipeDetail.get(0))
              .append("Ingredients", recipeDetail.get(1))
              .append("Steps", stepList);

        recipeCollection.insertOne(recipe);
    }

    public ArrayList<String> get(String title) {
        Document recipe = recipeCollection.find(new Document("Title", title)).first();
        ArrayList<String> recipeDetail = new ArrayList<>();
        if(recipe != null) {
            recipeDetail.add(recipe.getString("Title"));
            recipeDetail.add(recipe.getString("Ingredients"));
            List<Document> stepList = (List<Document>)recipe.get("Steps");
            for(Document step: stepList) {
                recipeDetail.add(step.getString("Step"));
            }
        }
        return recipeDetail;
    }

    public void updateIngredient(String title, String newIngredient) {
        Bson filter = eq("Title", title);
        Bson updateOperation = set("Ingredients", newIngredient);
        UpdateResult updateResult = recipeCollection.updateMany(filter, updateOperation);
        System.out.println(updateResult);
    }

    public void updateSteps(String title, List<String> newSteps) {
        List<Document> stepList = new ArrayList<>();
        for(String newStep: newSteps) {
            stepList.add(new Document("Step", newStep));
        }
        Bson filter = eq("Title", title);
        Bson updateOperation = set("Steps", stepList);
        UpdateResult updateResult = recipeCollection.updateMany(filter, updateOperation);
        System.out.println(updateResult);
    }

    public void delete(String title) {
        Bson filter = eq("Title", title);
        DeleteResult result = recipeCollection.deleteMany(filter);
        System.out.println(result);
    }

}