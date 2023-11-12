import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.List;

public class Model {
    private ChatGPT gpt;

    public Model() {
        gpt = new ChatGPT();
        String uri = "mongodb+srv://team13:CohNSwNGemiYmOOI@cluster0.1nejphw.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println("Successfully connected to MongoDB! :)");
        }
    }
}

class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "gpt-3.5-turbo";

    private HttpClient client;

    public ChatGPT() {
        this.client = HttpClient.newHttpClient();
    }

    public String generate(String prompt) throws
    IOException, InterruptedException, URISyntaxException {

        int maxTokens = 500;

        JSONObject requestBody = new JSONObject();
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "user").put("content", prompt));
        requestBody.put("model", MODEL);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = this.client.send(request,
        HttpResponse.BodyHandlers.ofString());
        
        JSONObject responseJson = new JSONObject(response.body());

        String generatedText = "";

        try {
            JSONArray choices = responseJson.getJSONArray("choices");
            generatedText = choices.getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");
        }
        catch (org.json.JSONException e) {
            System.out.println(e);
            System.out.println(responseJson);
        }

        return generatedText;
    }
}

/**
 * Class: Recipe
 * Store stuff from the Chat GPT response into this object
 * 
 */
class Recipe {
    private String recipeTitle; // name of the recipe
    private String description;
    private String mealType; // type of the meal as selected by the user
    private Map<String, String> ingredients; // a map of ingredients and their quantities
    private List<String> directions; // a list of ingredients

    Recipe() {
    }

    // getters and setters for Recipe
    public String getTitle() {
        return recipeTitle;
    }

    public void setTitle(String title) {
        this.recipeTitle = title;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}