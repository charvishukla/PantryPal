import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import org.bson.Document;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Updates.set;
import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URISyntaxException;
import java.util.Map;

public class Model {
    private String uri;
    private MongoDatabase database;
    private MongoCollection<Document> recipeCollection;
    private AudioRecorder audioRecorder;

    public Model() {
        audioRecorder = new AudioRecorder();
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