import static com.mongodb.client.model.Filters.eq;
import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.api.BooleanArrayAssert;
import org.bson.Document;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Updates.set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.File;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class ModelTest {
     private AudioRecorder mockAudioRecorder;
     private ChatGPT chatGPT;
     private Whisper whisper;
     private MongoClient mockClient;
     private MongoDatabase mockDatabase;
     private MongoCollection<Document> mockCollection;
     private MongoCollection<Document> mockUserCollection;
     private Authentication auth;
    @BeforeEach
    void setUp() {
        mockAudioRecorder = new MockAudioRecorder();
        chatGPT = new MockChatGPT();
        whisper = new MockWhisper();
        MockWhisper.resetMockFlags();

    }
    //--------------------------------------------------------------------------
    //-------------------------------CHATGPT TESTS------------------------------
    //--------------------------------------------------------------------------
    
    @Test
    void testGenerateWithValidPrompt() {
        String prompt = "test prompt";
        String expected = "Successfully invoked ChatGPT API for the prompt: " + prompt;
        String actual = "";
        try {
              actual = MockChatGPT.generate(prompt);
        } catch (Exception e) {
            System.out.println("Method generate() failed with an exception: "+ e );
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
            System.out.println("Method generate() failed with an exception: "+ e );
        }
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    
    @Test
    void testFormPromptWithValidInputs() {
        String mealType = "dinner";
        String ingredients = "chicken, rice";
        String expected = "formPrompt(): What is a step-by-step " + mealType + " recipe I can make using " + ingredients + "? Please provide a Title, ingredients, and steps.";
        String actual = MockChatGPT.formPrompt(mealType, ingredients);
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    void testFormPromptWithEmptyInputs() {
        String mealType = "";
        String ingredients = "";
        String expected = "formPrompt(): What is a step-by-step " + mealType + " recipe I can make using " + ingredients + "? Please provide a Title, ingredients, and steps.";
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

    //--------------------------------------------------------------------------
    //-------------------------AUDIORECORDER TESTS------------------------------
    //--------------------------------------------------------------------------
   

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

    //--------------------------------------------------------------------------
    //------------------------------WHISPER TESTS-------------------------------
    //--------------------------------------------------------------------------

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

    // Tests for mockHandleErrorResponse
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

    // Tests for mockWriteFileToOutputStream
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
     //-------------------------------------------------------------------------
    //------------------------------DataBaseTests-------------------------------
    //--------------------------------------------------------------------------
}


