import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.io.InputStream;

import org.bson.Document;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Updates.set;

import javax.faces.context.FlashWrapper;
import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import java.util.Scanner;
import com.sun.net.httpserver.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class Model implements HttpHandler {
    private AudioRecorder audioRecorder;
    private Database db;

    public Model() {
        try {
            Server.start(this);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        this.audioRecorder = new AudioRecorder();
        this.db = new Database();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Not handled";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
            } else {
                throw new Exception("Invalid Request: " + method);
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();

            //Sending back response to the client
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outStream = httpExchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.close();
        }
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String[] args = value.split("&");
            switch (args[0]) {
                case "gpt" -> {
                    response = "pass";// parseGPTResponse(args[1]);
                }
            }
        }
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String language = postData.substring(
            0,
            postData.indexOf(",")
        ), year = postData.substring(postData.indexOf(",") + 1);

        String response = "Posted entry {" + language + ", " + year + "}";
        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handlePut(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String year = "a"; // Retrieve data from hashmap
        }
        return response;
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
        }
        return response;
    }

    public String audioToText() {
        String message = "";

        try {
            message = Whisper.audioToText(new File("recording.wav"));
        } catch (IOException e) {
            e.printStackTrace(); // or handle the exception in an appropriate way
        } catch (URISyntaxException e) {
            e.printStackTrace(); // or handle the exception in an appropriate way
        }

        return message;
    }

    public void startRecording(){
        audioRecorder.startRecording();
    }

    //To stop the recoding process. 
    public void stopRecording(){
        audioRecorder.stopRecording();
    }

    public List<String> getNewRecipe(String mealType, String ingredients) {
        String prompt = ChatGPT.formPrompt(mealType, ingredients);
        List<String> response = ChatGPT.generateRecipe(prompt);
        return response;
    }
    
    public Database getDatabase(){
        return db;
    }

}

class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "gpt-3.5-turbo";

    public static String generate(String prompt) throws
    IOException, InterruptedException, URISyntaxException {

        HttpClient client = HttpClient.newHttpClient();
        int maxTokens = 500;

        JSONObject requestBody = new JSONObject();
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "user").put("content", prompt));
        requestBody.put("model", MODEL);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 0.5);

        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
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

    public static String formPrompt(String mealType, String ingredients) {
        String prompt = "What is a step-by-step " + mealType + " recipe I can make using " + ingredients + "? Please provide a Title, ingredients, and steps.";
        return prompt;
    }

    public static List<String> generateRecipe(String prompt) {
        List<String> response = new ArrayList<>();
        try{
            String originalResponse = ChatGPT.generate(prompt);
            String[] parts = originalResponse.split("\n\n", 3);
            String[] tidyParts = new String[] {parts[0].replace("Title: ", ""), parts[1], parts[2].replace("Steps:\n", "")};
            response.add(tidyParts[0]);
            response.add(tidyParts[1]);
            String tidySteps = tidyParts[2].replaceAll("\n+", "\n");
            String[] steps = tidySteps.split("\n");
            for(String s: steps) {
                if(!s.isEmpty()) {
                    response.add(s);
                }
            }          
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return response;
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

class AudioRecorder {

    private static AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, true);
    TargetDataLine targetDataLine;

    public void startRecording() {
        Thread t = new Thread(
            new Runnable(){
                @Override
                public void run(){
                    try {
                        // the format of the TargetDataLine
                        DataLine.Info dataLineInfo = new DataLine.Info(
                            TargetDataLine.class, audioFormat);

                        // the TargetDataLine used to capture audio data from the microphone
                        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                        targetDataLine.open(audioFormat);
                        targetDataLine.start();

                        // the AudioInputStream that will be used to write the audio data to a file
                        AudioInputStream audioInputStream = new AudioInputStream(
                                targetDataLine);

                        // the file that will contain the audio data
                        File audioFile = new File("recording.wav");
                        AudioSystem.write(
                                audioInputStream,
                                AudioFileFormat.Type.WAVE,
                                audioFile);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        );

        t.start();
    }

    public void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }
    
}

class Whisper {
    
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";

    private static void writeParameterToOutputStream(
        OutputStream outputStream,
        String parameterName,
        String parameterValue,
        String boundary
    ) 
    throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
            (
                "Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n"
            ).getBytes()
        );
        outputStream.write((parameterValue + "\r\n").getBytes());
    }


    // Helper method to write a file to the output stream in multipart form data format
    private static void writeFileToOutputStream(
        OutputStream outputStream,
        File file,
        String boundary
    ) 
    throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
            "Content-Disposition: form-data; name=\"file\"; filename=\"" +
            file.getName() +
            "\"\r\n"
        ).getBytes()
    );

        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());
    
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }
    

    // Helper method to handle a successful response
    private static String handleSuccessResponse(HttpURLConnection connection)
    throws IOException, JSONException {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject responseJson = new JSONObject(response.toString());

        String generatedText = responseJson.getString("text");

        return generatedText;
    }


    // Helper method to handle an error response
    private static void handleErrorResponse(HttpURLConnection connection)
    throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
            new InputStreamReader(connection.getErrorStream())
        );
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        System.out.println("Error Result: " + errorResult);
    }
    
    public static String audioToText(File file) throws IOException, URISyntaxException {

        // Set up HTTP connection
        URL url = new URI(API_ENDPOINT).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set up request headers
        String boundary = "Boundary-" + System.currentTimeMillis();
        connection.setRequestProperty(
        "Content-Type",
        "multipart/form-data; boundary=" + boundary
        );
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        // Set up output stream to write request body
        OutputStream outputStream = connection.getOutputStream();

        // Write model parameter to request body
        writeParameterToOutputStream(outputStream, "model", MODEL, boundary);

        // Write file parameter to request body
        writeFileToOutputStream(outputStream, file, boundary);

        // Write closing boundary to request body
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

        // Flush and close output stream
        outputStream.flush();
        outputStream.close();

        // Get response code
        int responseCode = connection.getResponseCode();
        String generatedText = "";

        // Check response code and handle response accordingly
        if (responseCode == HttpURLConnection.HTTP_OK) {
            generatedText = handleSuccessResponse(connection);
        } else {
            handleErrorResponse(connection);
        }

        // Disconnect connection
        connection.disconnect();

        return generatedText;
    }
}

class Database{
    private String uri;
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> recipeCollection;

    public Database(){
        this.uri = "mongodb+srv://team13:CohNSwNGemiYmOOI@cluster0.1nejphw.mongodb.net/?retryWrites=true&w=majority";

        // Initialize MongoClient without try-with-resources
        this.client = MongoClients.create(uri);
        this.database = client.getDatabase("PantryPal");
        this.recipeCollection = database.getCollection("Recipe");
        System.out.println("Successfully connected to MongoDB! :)");
    }

    public void close() {
        if (client != null) {
            this.client.close();
        }
    }

    public void insert(List<String> recipeDetail) {
        List<Document> stepList = new ArrayList<>();
        for(int i = 2; i < recipeDetail.size() - 1; i++) {
            stepList.add(new Document("Step", recipeDetail.get(i)));
        }

        Document recipe = new Document("_id", new ObjectId());
        recipe.append("Title", recipeDetail.get(0))
              .append("Ingredients", recipeDetail.get(1))
              .append("Steps", stepList)
              .append("MealType", recipeDetail.get(recipeDetail.size()-1));

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
            recipeDetail.add(recipe.getString("MealType"));
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

    public List<String> getAllTitles() {
        List<String> recipes = new ArrayList<>();
        try (MongoCursor<Document> cursor = recipeCollection.find().iterator()) {
            while (cursor.hasNext()) {
                recipes.add(cursor.next().getString("Title"));
            }
        }
        return recipes;
    }
}

class Authentication{
    private String uri;
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> userCollection;

    public Authentication(){
        this.uri = "mongodb+srv://team13:CohNSwNGemiYmOOI@cluster0.1nejphw.mongodb.net/?retryWrites=true&w=majority";

        // Initialize MongoClient without try-with-resources
        this.client = MongoClients.create(uri);
        this.database = client.getDatabase("PantryPal");
        this.userCollection = database.getCollection("Users");
        System.out.println("Successfully connected to MongoDB! :)");
    }

    public Authentication(MongoClient client, MongoDatabase database, MongoCollection<Document> userCollection){
        this.uri = "mongodb+srv://team13:CohNSwNGemiYmOOI@cluster0.1nejphw.mongodb.net/?retryWrites=true&w=majority";

        this.client= MongoClients.create(uri);; 
        this.database = client.getDatabase("PantryPal");
        this.userCollection = database.getCollection("Users");
    }

    public void close() {
        if (client != null) {
            this.client.close();
        }
    } 

    public boolean createUser(String username, String password, String firstName, String lastName, String phone) {
        try{
            // Generate a bcrypt hash of the password
            String hashedPassword = hashPassword(password);

            // Store the user's information in the database
            Document userDocument = new Document("username", username)
                    .append("password", hashedPassword)
                    .append("phone", phone)
                    .append("firstName", firstName)
                    .append("lastName", lastName);
            userCollection.insertOne(userDocument);
            return true;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return false;
        }
    }

    public Document mockCreateUser(String username, String password, String firstName, String lastName, String phone) {
        
            // Store the user's information in the database
            Document userDocument = new Document("username", username)
                    .append("password", password)
                    .append("phone", phone)
                    .append("firstName", firstName)
                    .append("lastName", lastName);
            userCollection.insertOne(userDocument);
            return userDocument;
    }

    public boolean checkUserExists(String username){
        Document userDocument = userCollection.find(new Document("username", username)).first();
        if (userDocument != null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean mockCheckUserExists(Document doc, ArrayList<Document> list ){
        return list.contains(doc);
    }

    public boolean verifyUser(String username, String password) {
        // Retrieve the user's information from the database
        Document userDocument = userCollection.find(new Document("username", username)).first();

        if (userDocument != null) {
            // Extract the stored hashed password
            String storedHashedPassword = userDocument.getString("password");
            //return storedHashedPassword.equals(password);

            // Verify the provided password against the stored hash
            return verifyPassword(password, storedHashedPassword);
        }
        
        return false; // User not found
    }

    public UserSession login(String username, String password) {
        // Verify the user's credentials
        if (verifyUser(username, password)) {
            return new UserSession(username);
        }
        return null; // Authentication failed
    }

    private String hashPassword(String password) {
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        return bcryptHashString;
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

}

class UserSession {
    private String username;

    public UserSession(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

// class UserSessionService {
//     private static UserSessionService instance;
//     private UserSession currentUserSession;

//     private UserSessionService() {
//         // Private constructor to enforce singleton pattern
//     }

//     public static UserSessionService getInstance() {
//         if (instance == null) {
//             System.out.println
//             instance = new UserSessionService();
//         }
//         return instance;
//     }

//     public UserSession getCurrentUserSession() {
//         return currentUserSession;
//     }

//     public void setCurrentUserSession(UserSession userSession) {
//         currentUserSession = userSession;
//     }
// }
