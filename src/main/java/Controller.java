import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import org.eclipse.jetty.http.HttpParser.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.nio.file.Files;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {
    private View view;
    //private Model model;
    private AudioRecorder audioRecorder;
    private String mealType;
    private String ingredients;
    private HttpClient client;
    private Logger log = LoggerFactory.getLogger(Controller.class);

    private static final String API_ENDPOINT = "http://127.0.0.1:7000";

    public Controller(View view) {
        this.view = view;
        //this.model = model;
        this.audioRecorder = new AudioRecorder();
        this.client = HttpClient.newHttpClient();

        this.view.getAppFrame().getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        this.view.getAppFrame().getDetailFooter().setShareButtonAction(this::handleShareButtonClick);

        this.view.getAppFrame().getFooter().setCreateButtonAction(this::handleCreateButtonClick);
        this.view.getAppFrame().getHeader().setLogoutButtonOnAction(this::handleLogoutButtonClick);

        //Filter and Sorting
        this.view.getAppFrame().getHeader().setFilterBoxOnAction(this::handleFilterBoxClick);
        this.view.getAppFrame().getHeader().setSortingBoxOnAction(this::handleSortingBoxClick);
        
        // Login Page
        this.view.getLoginPage().setCreateAccountButtonAction(this::handleCreateAccountButtonClick);
        this.view.getLoginPage().setLoginButtonAction(this::handleLoginButtonClick);

        // Create Account Page
        this.view.getCreateAccountPage().setLoginPageButtonAction(this::handleLoginPageButtonClick);
        this.view.getCreateAccountPage().setCreateAccountButtonAction(this::handleCreateAccountClick);

        //Create Frame actions.
        this.view.getCreateFrame().recordPressed(this::MealTypeRecord);
        this.view.getCreateFrame().recordUnpressed(this::MealTypeStopRecord);
        this.view.getCreateFrame().nextButton(this::MealTypeNextButton);

        //Voice Input Frame actions.
        this.view.getVoiceInputFrame().recordPressed(this::IngredientRecord);
        this.view.getVoiceInputFrame().recordUnpressed(this::IngredientStopRecord);
        this.view.getVoiceInputFrame().nextButton(this::IngredientNextButton);
       
        /*
         * Determines if the app has to automatically log in
         * a user.
         */
        JSONObject userAndExist = new JSONObject();
        try{ userAndExist = skipLogin(); }
        catch (Exception e) {
                log.error(e.toString());
                e.printStackTrace();
        }
        boolean exist = Boolean.parseBoolean(userAndExist.getString("exist"));
        if(exist){
            view.getAppFrame().getHeader().setUsername(userAndExist.getString("username"));
            try {
                setupRecipeCardsDetailsAction();
            }
            catch (Exception e) {
                log.error(e.toString());
                e.printStackTrace();
            }
            view.switchScene(this.view.getAppFrame());
        }
    }

    private JSONObject skipLogin() throws
    IOException, InterruptedException, URISyntaxException {

        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/auth/skip"))
        .header("Content-Type", "application/json")
        .GET()
        .build();

        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        
        JSONObject responseJSON = new JSONObject(response.body());
        return responseJSON;
    }

    private List<String> getAllTitles(String username) throws
    IOException, InterruptedException, URISyntaxException {
        // Create the request object
        username = username.replaceAll(" ", "%20");
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/recipe?title=&user=" + username))
        .header("Content-Type", "application/json")
        .GET()
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        
        JSONObject responseJSON = new JSONObject(response.body());
        JSONArray arr = responseJSON.getJSONArray("data");

        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < arr.length(); i++) {
            titles.add(arr.getString(i));
        }

        return titles;
    }

    private JSONObject getRecipe(String title, String username) throws
    IOException, InterruptedException, URISyntaxException {
        // Create the request object
        title = title.replaceAll(" ", "%20");
        username = username.replaceAll(" ", "%20");
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/recipe?title=" + title + "&user=" + username))
        .header("Content-Type", "application/json")
        .GET()
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        
        JSONObject responseJSON = new JSONObject(response.body());
        return responseJSON;
    }

    private JSONObject getRecipeByID(String id) throws
    IOException, InterruptedException, URISyntaxException {
        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/recipe?id=" + id))
        .header("Content-Type", "application/json")
        .GET()
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        
        JSONObject responseJSON = new JSONObject(response.body());
        return responseJSON;
    }

    private String getNewImageURL(String prompt) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("title", prompt);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/image"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

        String url = response.body();

        return url;
    }

    private String updateImageURL(String title, String newURL) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("title", title).put("url", newURL);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/image"))
        .header("Content-Type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private String updateImageTimestamp(String title, String timestamp) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("title", title).put("timestamp", timestamp);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/image/time"))
        .header("Content-Type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private String updateSteps(String title, List<String> stepsList) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("title", title);
        JSONArray steps = new JSONArray(stepsList);
        requestBody.put("steps", steps);

        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/recipe"))
        .header("Content-Type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private void deleteRecipe(String title) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("title", title);

        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/recipe"))
        .header("Content-Type", "application/json")
        .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        client.send(request,HttpResponse.BodyHandlers.ofString());
    }

    private void insertRecipe(JSONObject json) throws 
    IOException, InterruptedException, URISyntaxException {

        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/recipe"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
        .build();

        client.send(request,HttpResponse.BodyHandlers.ofString());
    }

    private JSONObject getNewRecipe(String mealType, String ingredients) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("mealType", mealType).put("ingredients", ingredients);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/recipe/generate"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

        JSONObject responseBody = new JSONObject(response.body());

        return responseBody;
    }

    private String audioToText() throws 
    IOException, InterruptedException, URISyntaxException {
        String boundary = "MyBoundary";
        String formData = "--" + boundary + "\r\n" +
            "Content-Disposition: form-data; name=\"recording.wav\"; filename=\"recording.wav\"\r\n" +
            "Content-Type: audio/wav\r\n\r\n" +
            new String(Files.readAllBytes(Paths.get("recording.wav"))) +
            "\r\n--" + boundary + "--\r\n";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "/whisper"))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private boolean login(String username, String password) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("username", username).put("password", password);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/auth/login"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        return Boolean.parseBoolean(result);
    }

    private boolean sessionExist(String username) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("username", username);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/auth/session"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        return Boolean.parseBoolean(result);
    }

    private void markAuto(String username) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("username", username);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/auth/auto"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private boolean checkUserExist(String username) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = new JSONObject().put("username", username);
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/auth/check"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        return Boolean.parseBoolean(result);
    }

    private boolean createUser(JSONObject info) throws 
    IOException, InterruptedException, URISyntaxException {
        JSONObject requestBody = info;
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT + "/auth/create"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        return Boolean.parseBoolean(result);
    }

    public void handleLogoutButtonClick(ActionEvent event){
        this.view.getAppFrame().getRecipeList().deleteAll();
        view.switchScene(this.view.getLoginPage());
        File identifyer = new File("Device Identifier");
        identifyer.delete();
    }

    public void handleFilterBoxClick(ActionEvent event) {
        String filter = this.view.getAppFrame().getHeader().getFilterBox().getValue();
        RecipeList recipeList = this.view.getAppFrame().getRecipeList();
        List<RecipeCard> recipeCards = recipeList.getRecipeCards();
        recipeList.deleteAllOnScene();
        if(filter.equals("all")) {
            for(int i = 0; i < recipeCards.size(); i++) {
                recipeList.addRecipeCardOnScene(recipeCards.get(i));
            }
        } else {
            for(int i = 0; i < recipeCards.size(); i++) {
                RecipeCard temp = recipeCards.get(i);
                if(temp.getMealType().equals(filter)) {
                    recipeList.addRecipeCardOnScene(temp);
                }
            }
        }
    }

    public void handleSortingBoxClick(ActionEvent event) {
        String sorting = this.view.getAppFrame().getHeader().getSortingBox().getValue();
        RecipeList recipeList = this.view.getAppFrame().getRecipeList();
        List<RecipeCard> recipeCards = recipeList.getRecipeCards();
        if(sorting.equals("Alphabetically")){
            for (int i = 0; i < recipeCards.size(); i++){
                int maxIndex = i;
                for (int j = i + 1; j < recipeCards.size(); j++) {
                    if(recipeCards.get(j).getTitle().compareToIgnoreCase(recipeCards.get(maxIndex).getTitle()) > 0) {
                        maxIndex = j;
                    }
                }
                RecipeCard temp = recipeCards.get(maxIndex);
                recipeCards.set(maxIndex, recipeCards.get(i));
                recipeCards.set(i, temp);
            }
        } else if(sorting.equals("Newest to Oldest")){
            for (int i = 0; i < recipeCards.size(); i++){
                int oldestIndex = i;
                for (int j = i + 1; j < recipeCards.size(); j++) {
                    if(recipeCards.get(j).getTime().isBefore(recipeCards.get(oldestIndex).getTime())) {
                        oldestIndex = j;
                    }
                }
                RecipeCard temp = recipeCards.get(oldestIndex);
                recipeCards.set(oldestIndex, recipeCards.get(i));
                recipeCards.set(i, temp);
            }
        } else {
            for (int i = 0; i < recipeCards.size(); i++){
                int newestIndex = i;
                for (int j = i + 1; j < recipeCards.size(); j++) {
                    if(recipeCards.get(j).getTime().isAfter(recipeCards.get(newestIndex).getTime())) {
                        newestIndex = j;
                    }
                }
                RecipeCard temp = recipeCards.get(newestIndex);
                recipeCards.set(newestIndex, recipeCards.get(i));
                recipeCards.set(i, temp);
            }
        }
        recipeList.deleteAllOnScene();
        for (int i = 0; i < recipeCards.size(); i++) {
            recipeList.addRecipeCardOnScene(recipeCards.get(i));
        }
    }

    private void handleLoginButtonClick(ActionEvent event) {
        String username = this.view.getLoginPage().getUsername();
        String password = this.view.getLoginPage().getPassword();
        boolean exist = false;
        try{ exist = login(username, password); }
        catch (Exception ex) {ex.printStackTrace(); serverDown();}
        // try{ exist = sessionExist(username); }
        // catch (Exception ex) {ex.printStackTrace(); serverDown();}

        if (exist){

            //If user selected remeber me, then we leave a mark in the database to remember. 
            if(view.getLoginPage().getAutoLoginStatus() == true){
                try { markAuto(username); }
                catch (Exception ex) {ex.printStackTrace(); serverDown();}
            }
            view.switchScene(this.view.getAppFrame());
            view.getAppFrame().getHeader().setUsername(username);
            try {
                setupRecipeCardsDetailsAction();
            }
            catch (Exception e) {
                log.error(e.toString());
                e.printStackTrace();
            }
        }else{
            view.getLoginPage().showAlert();
        }
    }
 
    /**
     * Create a new account 
     * @param event
     */
    private void handleCreateAccountClick(ActionEvent event) {
        String username = this.view.getCreateAccountPage().getUsername();
        String password = this.view.getCreateAccountPage().getPassword();
        String confirmPassword = this.view.getCreateAccountPage().getConfirmPassword();
        String phone = this.view.getCreateAccountPage().getPhone();
        String firstName = this.view.getCreateAccountPage().getFirstName();
        String lastName = this.view.getCreateAccountPage().getLastName();

        if (!password.equals(confirmPassword)){
            this.view.getCreateAccountPage().showAlert("Passwords do not match");
            return;
        }
        boolean exist = true;
        try { exist = checkUserExist(username); }
        catch (Exception ex) {ex.printStackTrace(); serverDown();}
        if (exist){
            this.view.getCreateAccountPage().showAlert("Username already exists");
            return;
        }
        JSONObject info = new JSONObject().put("username", username).put("confirmPassword", confirmPassword).put("firstName", firstName).put("lastName", lastName).put("phone", phone);
        boolean created = false;
        try { created = createUser(info); }
        catch (Exception ex) {ex.printStackTrace(); serverDown();}
        if (created){
            this.view.switchScene(this.view.getLoginPage());
            return;
        }else{
            this.view.getCreateAccountPage().showAlert("Something went wrong");
            return;
        }
    }
    /**
     * Navigates from Login Page to Create Account Page if the user does not have an account
     * @param event
     */
    private void handleCreateAccountButtonClick(ActionEvent event){
        view.switchScene(this.view.getCreateAccountPage());
    }

    /**
     * Navigates from Create Account Page to Login Page if the user already has an account
     * @param event
     */
    private void handleLoginPageButtonClick(ActionEvent event){
        view.switchScene(this.view.getLoginPage());
        this.view.getCreateAccountPage().clearInputs();
    }

    /**
     * 
     */
    private void setupRecipeCardsDetailsAction() throws 
    IOException, InterruptedException, URISyntaxException {
        final String username = this.view.getAppFrame().getHeader().getUsername();
        //Returns a list of titles of each recipe in database;
        List<String> titles = getAllTitles(username);

        //A JSONObject to store title, ingredients, and step by step recipe.
        JSONObject recipeJSON;

        for(String title : titles){
            //Generate the recipe detail page.
            recipeJSON = getRecipe(title, username);
            Instant oldTime;
            try {
                oldTime = Instant.parse(recipeJSON.getString("ImageTime"));
            }
            catch (Exception e) {
                log.error(e.toString());
                oldTime = Instant.MIN;
            }
            if(!Helper._checkImageValid(Instant.now(), oldTime)){
                String newURL = getNewImageURL(title);
                System.out.println("Stale image. new url: " + newURL);
                recipeJSON.put("Image", newURL);
                recipeJSON.put("ImageTime", Instant.now().toString());
                updateImageURL(title, newURL);
                updateImageTimestamp(title, recipeJSON.getString("ImageTime"));
            }
            RecipeDetailPage deet = new RecipeDetailPage(recipeJSON);

            RecipeCard newRecipe = new RecipeCard(title, recipeJSON.getString("MealType"), recipeJSON.getString("Time"));
            newRecipe.setImage(recipeJSON.getString("Image"));
            newRecipe.addRecipeDetail(deet);
            this.view.getAppFrame().getRecipeList().addRecipeCard(newRecipe);
            newRecipe.getDetailButton().setOnAction(e1 -> {this.view.switchScene(deet);});
            
            deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
            deet.getDetailFooter().getSaveButton().setOnAction(
                e ->    {
                        try { updateSteps(title, deet.getSteps()); }
                        catch (Exception ex) { ex.printStackTrace(); serverDown(); } // ADD SERVER TIMEOUT
                        this.view.switchScene(this.view.getAppFrame());
                        });
            deet.getDetailFooter().getDeleteButton().setOnAction(
                e ->    {this.view.getAppFrame().getRecipeList().deleteRecipeCard(title);
                        try { deleteRecipe(title); }
                        catch (Exception ex) { ex.printStackTrace(); serverDown(); } // ADD SERVER TIMEOUT
                        this.view.switchScene(this.view.getAppFrame());
                        });
            deet.getDetailFooter().getAddStepButton().setOnAction(
                e ->    {
                        TextField temp = new TextField();
                        temp.getStyleClass().add("step");
                        temp.setTranslateX(100);
                        deet.getDetailList().getChildren().addLast(temp);
                        });
            deet.getDetailFooter().getDeleteStepButton().setOnAction(
                e ->    {
                        Node last = deet.getDetailList().getChildren().getLast();
                        if(last instanceof TextField) {
                            deet.getDetailList().getChildren().remove(last);
                        }
                        });
            deet.getDetailFooter().getShareButton().setOnAction(
                e ->    {
                        deet.showAlert("http://127.0.0.1:7000/share?id=" + deet.getResponse().getString("id"));
                        });
        }
    }
    
    private void handleBackButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getAppFrame());
    }

    //If recording is started, let the microphone image glow,
    //and let the next button be available.
    private void MealTypeRecord(MouseEvent event){
        this.view.getCreateFrame().getRecordButton().setEffect(new Glow(50));
        this.view.getCreateFrame().setMealType(null);
        this.view.getCreateFrame().updateNextButton();
        this.audioRecorder.startRecording(); 
    }

    //If the recording is stopped, Stop recording and 
    //call on whisper to convert audio to text.
    private void MealTypeStopRecord(MouseEvent event){
        this.view.getCreateFrame().getRecordButton().setEffect(null);
        this.audioRecorder.stopRecording();
        try { mealType = audioToText(); }
        catch (Exception e) { e.printStackTrace(); }
        this.view.getCreateFrame().setMealType(mealType);
        
        if(this.view.getCreateFrame().getMealType().equals("TryAgain")){
            this.view.getCreateFrame().setMealType(null);
            this.view.getCreateFrame().updateNextButton();
            this.view.getCreateFrame().setTitle("Please try to state your meal type again.");
        }
        else{
            this.view.getCreateFrame().setMealType(mealType);
            mealType = this.view.getCreateFrame().getMealType();
            this.view.getCreateFrame().updateNextButton();
        } 
    }

    //If next is clicked, switches from create frame to voice input frame.
    //And update the next frame to display what meal type you selected.
    private void MealTypeNextButton(ActionEvent event){
        this.view.switchScene(this.view.getVoiceInputFrame());
        this.view.getVoiceInputFrame().setTitle(this.view.getCreateFrame().getMealType());
        this.view.getCreateFrame().setMealType(null);
        this.view.getCreateFrame().updateNextButton();
    }

    public void handleCreateButtonClick(ActionEvent event) {
        this.view.switchScene(this.view.getCreateFrame());
    }

    public void handleShareButtonClick(ActionEvent event) {
        // this.view.getRecipeDetailPage.showAlert();
    }

    //If the record button is clicked. 
    private void IngredientRecord(MouseEvent event) {
        this.view.getVoiceInputFrame().getRecordButton().setEffect(new Glow(50));
        this.audioRecorder.startRecording();
        this.view.getVoiceInputFrame().setIngredients(null); 
        this.view.getVoiceInputFrame().updateNextButton();
    }

    //If the recording is stopped, Stop recoding and 
    //call on whisper to convert audio to text.
    private void IngredientStopRecord(MouseEvent event){
         this.view.getVoiceInputFrame().getRecordButton().setEffect(null);
         this.audioRecorder.stopRecording();
         try { ingredients = audioToText(); }
         catch (Exception e) { e.printStackTrace(); }
         this.view.getVoiceInputFrame().updatePrompt("Prompt received: " + ingredients);
         this.view.getVoiceInputFrame().updateNextButton();
    }    

    //If next is clicked, switches from voice input frame to recipe genati
    //And update the next frame to display what meal type you selected.
    private void IngredientNextButton(ActionEvent event) {
        this.view.getVoiceInputFrame().updatePrompt("Please list the ingredients you wish to cook with.");
        this.view.getVoiceInputFrame().setIngredients(null);
        this.view.getVoiceInputFrame().updateNextButton();

        //System.out.println(prompt);
        //response = {Title, Ingredients, Step 1, Step2, Step3, .....}
        JSONObject response = new JSONObject();
        try {
            response = getNewRecipe(mealType, ingredients.substring(0, ingredients.length() - 1)); 
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());// HANDLE SERVER TIMEOUT
            serverDown();
        }
        response.put("User", this.view.getAppFrame().getHeader().getUsername());
        response.put("Time", Instant.now().toString());
        try {
            response.put("Image", getNewImageURL(response.getString("Title")));
        }
        catch (Exception e) { // HANDLE SERVER TIMEOUT
            e.printStackTrace();
            log.error(e.toString());
            serverDown();
        }
        response.put("ImageTime", Instant.now().toString());
        RecipeDetailPage deet = new RecipeDetailPage(response);
        deet.enableRefresh();
        deet.getDetailFooter().setBackButtonAction(this::handleBackButtonClick);
        deet.getDetailFooter().getSaveButton().setOnAction(
            e ->    {
                    RecipeCard recipe = new RecipeCard(deet.getResponse().getString("Title"), mealType, deet.getResponse().getString("Time"));
                    recipe.setImage(deet.getResponse().getString("Image"));
                    deet.disableRefresh();
                    recipe.addRecipeDetail(deet);
                    recipe.getDetailButton().setOnAction(e1 -> {this.view.switchScene(deet);});
                    if(!this.view.getAppFrame().getRecipeList().checkRecipeExists(deet.getResponse().getString("Title"))) {
                        this.view.getAppFrame().getRecipeList().addRecipeCard(recipe);
                        try { insertRecipe(deet.getResponse()); }
                        catch (Exception ex) { ex.printStackTrace(); serverDown(); } // HANDLE SERVER TIMEOUT
                    } else {
                        try { updateSteps(deet.getResponse().getString("Title"), deet.getSteps()); }
                        catch (Exception ex) { ex.printStackTrace(); serverDown(); } // HANDLE SERVER TIMEOUT
                    }
                    this.view.switchScene(this.view.getAppFrame());
                    });
        deet.getDetailFooter().getDeleteButton().setOnAction(
            e ->    {
                    this.view.getAppFrame().getRecipeList().deleteRecipeCard(deet.getResponse().getString("Title"));
                    try { deleteRecipe(deet.getResponse().getString("Title")); }
                    catch (Exception ex) { ex.printStackTrace(); serverDown(); } // ADD SERVER TIMEOUT
                    this.view.switchScene(this.view.getAppFrame());
                    });
        deet.getDetailFooter().getAddStepButton().setOnAction(
            e ->    {
                    TextField temp = new TextField();
                    temp.getStyleClass().add("step");
                    temp.setTranslateX(100);
                    deet.getDetailList().getChildren().addLast(temp);
                    });
        deet.getDetailFooter().getDeleteStepButton().setOnAction(
            e ->    {
                    Node last = deet.getDetailList().getChildren().getLast();
                    if(last instanceof TextField) {
                        deet.getDetailList().getChildren().remove(last);
                    }
                    });
        deet.getRefreshButton().setOnAction(
            e ->    {
                    JSONObject newResponse = new JSONObject();
                    try {
                        newResponse = getNewRecipe(mealType, ingredients.substring(0, ingredients.length() - 1));
                    } catch (Exception ex) { // HANDLE SERVER TIMEOUT
                        ex.printStackTrace();
                        log.error(ex.toString());
                    }
                    newResponse.put("User", this.view.getAppFrame().getHeader().getUsername());
                    newResponse.put("Time", Instant.now().toString());
                    try { newResponse.put("Image", getNewImageURL(newResponse.getString("Title"))); }
                    catch (Exception ex) { ex.printStackTrace(); } // ADD SERVER TIMEOUT
                    newResponse.put("ImageTime", Instant.now().toString());
                    deet.updateResponse(newResponse);
                    deet.update();
                    });
        deet.getDetailFooter().getShareButton().setOnAction(
            e ->    {
                    deet.showAlert("http://127.0.0.1/share?id=" + deet.getResponse().getString("id"));
                    });
        this.view.switchScene(deet);
    }

    void serverDown() {
        //this.view.switchScene();
    }
}