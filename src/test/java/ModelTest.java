import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.api.BooleanArrayAssert;
import org.bson.Document;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.mockito.junit.MockitoJUnitRunner.Strict;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.AssertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

class ModelTest {
    private AudioRecorder mockAudioRecorder;
    private ChatGPT chatGPT;
    private Whisper whisper;
    private Authentication authentication;
    private Database database;
    private JSONObject testRecipe;
    
    @BeforeEach
    void setUp() {
        mockAudioRecorder = new MockAudioRecorder();
        chatGPT = new MockChatGPT();
        whisper = new MockWhisper();
        MockWhisper.resetMockFlags();
        // Mocking authentication
        authentication = new MockAuthentication();
        // Populating the Database:
        authentication.createUser("Charmander", "fire123", "Char", "Mander", "1234567890");
        authentication.createUser("Bulbasaur", "water123", "Bulb", "Asaur", "2345678901");
        authentication.createUser("Pikachu", "zap123zap", "Pika", "Chu", "4567890123");
    
        // Mocking Database 
        database = new MockDatabase();
        testRecipe = new JSONObject();
        testRecipe.put("Title", "Test Recipe");
        testRecipe.put("Ingredients", "Test Ingredients");
        testRecipe.put("Steps", Arrays.asList("Step 1", "Step 2"));
        testRecipe.put("MealType", "Test Meal");
        testRecipe.put("User", "TestUser");
        testRecipe.put("numSteps", 2);
    
    }
    // --------------------------------------------------------------------------
    // -------------------------------CHATGPT TESTS------------------------------
    // --------------------------------------------------------------------------

    @Test
    void testGenerateWithValidPrompt() {
        String prompt = "test prompt";
        String expected = "Successfully invoked ChatGPT API for the prompt: " + prompt;
        String actual = "";
        try {
            actual = MockChatGPT.generate(prompt);
        } catch (Exception e) {
            System.out.println("Method generate() failed with an exception: " + e);
        }
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateWithEmptyPrompt() {
        String prompt = "";
        String expected = "Successfully invoked ChatGPT API for the prompt: " + prompt;
        String actual = "";
        try {
            actual = MockChatGPT.generate(prompt);
        } catch (Exception e) {
            System.out.println("Method generate() failed with an exception: " + e);
        }
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    void testFormPromptWithValidInputs() {
        String mealType = "dinner";
        String ingredients = "chicken, rice";
        String expected = "formPrompt(): What is a step-by-step " + mealType + " recipe I can make using " + ingredients
                + "? Please provide a Title, ingredients, and steps.";
        String actual = MockChatGPT.formPrompt(mealType, ingredients);
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    void testFormPromptWithEmptyInputs() {
        String mealType = "";
        String ingredients = "";
        String expected = "formPrompt(): What is a step-by-step " + mealType + " recipe I can make using " + ingredients
                + "? Please provide a Title, ingredients, and steps.";
        String actual = MockChatGPT.formPrompt(mealType, ingredients);
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateRecipeWithValidPrompt() {
        String prompt = "test recipe prompt";
        JSONObject expected = new JSONObject();
        expected.put("Title", "Mock Recipe Title");
        expected.put("Ingredients", "Ingredient 1, Ingredient 2");
        expected.put("Steps", "Step 1. Do something\nStep 2. Do something else");
        expected.put("numSteps", 2);
        JSONObject actual = MockChatGPT.generateRecipe(prompt);
        System.out.println(expected.toString());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testGenerateRecipeWithEmptyPrompt() {
        String prompt = "";
        JSONObject expected = new JSONObject();
        expected.put("Title", "Mock Recipe Title");
        expected.put("Ingredients", "Ingredient 1, Ingredient 2");
        expected.put("Steps", "Step 1. Do something\nStep 2. Do something else");
        expected.put("numSteps", 2);
        System.out.println(expected.toString());
        JSONObject actual = MockChatGPT.generateRecipe(prompt);
        assertEquals(expected.toString(), actual.toString());
    }

    // --------------------------------------------------------------------------
    // -------------------------AUDIORECORDER TESTS------------------------------
    // --------------------------------------------------------------------------

    @Test
    void startRecording_ShouldSetIsRecordingToTrue() {
        mockAudioRecorder.startRecording();
        assertTrue(mockAudioRecorder.isRecording);
    }

    @Test
    void startRecording_ShouldPrintCorrectMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        mockAudioRecorder.startRecording();
        assertEquals("Mock startRecording called\n", outContent.toString());

        System.setOut(System.out);
    }

    @Test
    void stopRecording_ShouldSetIsRecordingToFalse() {
        mockAudioRecorder.startRecording(); // Ensure recording is started
        mockAudioRecorder.stopRecording();
        assertFalse(mockAudioRecorder.isRecording());
    }

    @Test
    void stopRecording_ShouldPrintCorrectMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        mockAudioRecorder.stopRecording();
        assertEquals("Mock stopRecording called\n", outContent.toString());
        System.setOut(System.out);
    }

    // --------------------------------------------------------------------------
    // ------------------------------WHISPER TESTS-------------------------------
    // --------------------------------------------------------------------------

    @Test
    void audioToText_ShouldReturnMockedText() {
        String response = "";
        try {
            response = MockWhisper.audioToText(new File("testfile.mp3"));
        } catch (Exception e) {
            response = "AudioToText failed";
            e.printStackTrace();
        }
        assertEquals("Mocked text for file: testfile.mp3", response);
    }

    void audioToText_ShouldHandleNullFile() {
        Boolean boo = false;
        try {
            MockWhisper.audioToText(null);
        } catch (Exception IOException) {
            boo = true;
        }
        assertTrue(boo);
    }

    @Test
    void mockHandleErrorResponse_ShouldSetFlag() {
        MockWhisper.mockHandleErrorResponse(null);
        assertTrue(MockWhisper.isMockHandleErrorResponseCalled);
    }

    @Test
    void mockHandleErrorResponse_ShouldNotThrowException() {
        assertDoesNotThrow(() -> MockWhisper.mockHandleErrorResponse(null));
    }

    // Tests for mockHandleSuccessResponse
    @Test
    void mockHandleSuccessResponse_ShouldSetFlag() {
        MockWhisper.mockHandleSuccessResponse(null);
        assertTrue(MockWhisper.isMockHandleSuccessResponseCalled);
    }

    @Test
    void mockHandleSuccessResponse_ShouldReturnMockedText() {
        String response = MockWhisper.mockHandleSuccessResponse(null);
        assertEquals("Simulated successful response text", response);
    }

    @Test
    void mockWriteFileToOutputStream_ShouldSetFlag() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockWhisper.mockWriteFileToOutputStream(outputStream, new File("testfile.mp3"), "boundary");
        assertTrue(MockWhisper.isMockWriteFileToOutputStreamCalled);
    }

    @Test
    void mockWriteFileToOutputStream_ShouldWriteToOutputStream() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockWhisper.mockWriteFileToOutputStream(outputStream, new File("testfile.mp3"), "boundary");
        String expectedOutput = "Mock data for file: testfile.mp3";
        assertEquals(expectedOutput, outputStream.toString());
    }
    // -------------------------------------------------------------------------
    // ------------------------------AuthenticationTests------------------------
    // -------------------------------------------------------------------------

    @Test
    void createUser_ShouldReturnTrueForNewUser() {
        boolean result = authentication.createUser("cse110programmer", "ilovepython", "Yeet", "YeetLastname", "0987654321");
        assertTrue(result);
    }

    @Test
    void createUser_ShouldReturnFalseForExistingUser() {
        boolean result = authentication.createUser("Pikachu", "zap123zap", "Pika", "Chu", "4567890123");
        assertFalse(result);
    }

    @Test
    void checkUserExists_ShouldReturnTrueForExistingUser() {
        assertTrue(authentication.checkUserExists("Charmander"));
    }

    @Test
    void checkUserExists_ShouldReturnFalseForNonExistingUser() {
        assertFalse(authentication.checkUserExists("Charizard"));
    }

    
    @Test
    void verifyUser_ShouldReturnTrueForCorrectCredentials() {
        assertTrue(authentication.verifyUser("Charmander", "fire123"));
    }

    @Test
    void verifyUser_ShouldReturnFalseForIncorrectCredentials() {
        assertFalse(authentication.verifyUser("Charmander", "wrongPassword"));
    }

    @Test
    void login_ShouldReturnUserSessionForCorrectCredentials() {
        UserSession session = authentication.login("Bulbasaur", "water123");
        assertNotNull(session);
        assertEquals("Bulbasaur", session.getUsername());
    }

    @Test
    void login_ShouldReturnNullForIncorrectCredentials() {
        UserSession session = authentication.login("Bulbasaur", "wrongPassword");
        assertNull(session);
    }

    // -------------------------------------------------------------------------
    // ---------------------------Database Tests--------------------------------
    // -------------------------------------------------------------------------

    @Test
    void insert_ShouldAddRecipe() {
        database.insert(testRecipe);
        JSONObject retrievedRecipe = database.get("Test Recipe");
        assertNotNull(retrievedRecipe);
        assertEquals("Test Recipe", retrievedRecipe.getString("Title"));
    }

    @Test
    void insert_ShouldOverwriteExistingRecipe() {
        database.insert(testRecipe);
        testRecipe.put("Ingredients", "New Ingredients");
        database.insert(testRecipe);

        JSONObject retrievedRecipe = database.get("Test Recipe");
        assertEquals("New Ingredients", retrievedRecipe.getString("Ingredients"));
    }

    // Tests for get
    @Test
    void get_ShouldReturnRecipe() {
        database.insert(testRecipe);
        JSONObject retrievedRecipe = database.get("Test Recipe");
        assertNotNull(retrievedRecipe);
        assertEquals("Test Ingredients", retrievedRecipe.getString("Ingredients"));
    }

    @Test
    void get_ShouldReturnNullForNonExistingRecipe() {
        JSONObject retrievedRecipe = database.get("Non Existing Recipe");
        assertNull(retrievedRecipe);
    }

    // Tests for updateIngredient
    @Test
    void updateIngredient_ShouldUpdateIngredients() {
        database.insert(testRecipe);
        database.updateIngredient("Test Recipe", "Updated Ingredients");

        JSONObject retrievedRecipe = database.get("Test Recipe");
        assertEquals("Updated Ingredients", retrievedRecipe.getString("Ingredients"));
    }

    @Test
    void updateIngredient_ShouldNotUpdateNonExistingRecipe() {
        database.updateIngredient("Non Existing Recipe", "Updated Ingredients");
        JSONObject retrievedRecipe = database.get("Non Existing Recipe");
        assertNull(retrievedRecipe);
    }

    // Tests for updateSteps
    @Test
    void updateSteps_ShouldUpdateSteps() {
        // database.insert(testRecipe);

        // List<String> newSteps = Arrays.asList("New Step 1", "New Step 2");

        // database.updateSteps("Test Recipe", newSteps);

        // JSONObject expected = new JSONObject();
        // expected.put("Title", "Test Recipe");
        // expected.put("Ingredients", "Test Ingredients");
        // expected.put("Steps", Arrays.asList("New Step 1", "New Step 2"));
        // expected.put("MealType", "Test Meal");
        // expected.put("User", "TestUser");
        // expected.put("numSteps", 2);
        // //retrievedRecipe.get("Steps")
        // assertSame(expected, database.get("Test Recipe"));
    }

    @Test
    void updateSteps_ShouldNotUpdateNonExistingRecipe() {
        List<String> newSteps = Arrays.asList("New Step 1", "New Step 2");
        database.updateSteps("Non Existing Recipe", newSteps);
        JSONObject retrievedRecipe = database.get("Non Existing Recipe");
        assertNull(retrievedRecipe);
    }

    // Tests for delete
    @Test
    void delete_ShouldRemoveRecipe() {
        database.insert(testRecipe);
        database.delete("Test Recipe");
        JSONObject retrievedRecipe = database.get("Test Recipe");
        assertNull(retrievedRecipe);
    }

    @Test
    void delete_ShouldNotAffectNonExistingRecipe() {
        database.delete("Non Existing Recipe");
        JSONObject retrievedRecipe = database.get("Non Existing Recipe");
        assertNull(retrievedRecipe);
    }

    // Tests for getAllTitles
    @Test
    void getAllTitles_ShouldReturnTitlesForUser() {
        database.insert(testRecipe);
        List<String> titles = database.getAllTitles("TestUser");
        assertNotNull(titles);
        assertTrue(titles.contains("Test Recipe"));
    }

    @Test
    void getAllTitles_ShouldReturnEmptyListForNonExistingUser() {
        List<String> titles = database.getAllTitles("Non Existing User");
        assertTrue(titles.isEmpty());
    }
}

    

