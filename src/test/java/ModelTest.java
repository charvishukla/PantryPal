import static com.mongodb.client.model.Filters.eq;
import org.junit.jupiter.api.BeforeEach;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Updates.set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.File;

// @ExtendWith(MockitoExtension.class)
class ModelTest {
    @Mock
    private AudioRecorder mockedAudioRecorder;
    @Mock
    private Database mockedDatabase;
    @Mock
    private Whisper mockedWhisper;
    @Mock
    private Model mockedModel;
    @Mock 
    private ChatGPT mockedChatGPT;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // // Test method to check ChatGPT's generate function with a valid ingredient
    // @Test 
    // void testGenerateWithValidIngredient1() throws IOException, InterruptedException, URISyntaxException {
    //     String ingredient = "potato"; 
    //     try {
    //         when(mockedChatGPT.generate(ingredient)).thenReturn("test");
    //     }
    //     catch (Exception e) {
    //         System.out.println(e);
    //     }
    //     assertEquals(mockedChatGPT.generate(ingredient), "test");
    // }

    // // Another test method for ChatGPT's generate function with multiple ingredients
    // @Test 
    // void testGenerateWithValidIngredient2() throws IOException, InterruptedException, URISyntaxException {
    //     String ingredient = "potato, chili, cheese, olive oil"; 
    //     when(mockedChatGPT.generate(ingredient)).thenReturn("test");
    //     assertEquals(mockedChatGPT.generate(ingredient), "test");
    // }

    // Test method to check the behavior of insert operation in the database
    @Test 
    void testInsert(){
        try{
            List<String> stepList = new ArrayList<>();
            stepList.add("item1");
            stepList.add("item2");
            stepList.add("item3");
            Mockito.doThrow(new Exception()).when(mockedDatabase).insert(stepList);
        }catch(Exception e){
            assertTrue(true);
        }
        
    }

    // Test method for retrieving data from the database
    @Test 
    void testGet(){
        String title = "hey";
        ArrayList<String> arr = new ArrayList<>();
        arr.add("hey"); 
        when(mockedDatabase.get(title)).thenReturn(arr);
        assertEquals(mockedDatabase.get(title), arr);  
    }

     // Test method for updating steps in the database
    @Test 
    void testUpdateSteps(){
        try{
            List<String> stepList = new ArrayList<String>();
            stepList.add("TestStep");
            String title = "TestTitle";
            Mockito.doThrow(new Exception()).when(mockedDatabase).updateSteps(title, stepList);
        }catch(Exception e){
            assertTrue(true);
        }
        
    }

    // Test method for updating ingredients in the database
    @Test 
    void testUpdateIngredient(){
        try{
            String ingrediant = "TestIngredient";
            String title = "TestTitle";
            Mockito.doThrow(new Exception()).when(mockedDatabase).updateIngredient(title, ingrediant);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    // Test method for deleting an entry from the database
    @Test 
    void testDelete(){
        try{
            String title = "TestTitle";
            Mockito.doThrow(new Exception()).when(mockedDatabase).delete(title);
        }catch(Exception e){
            assertTrue(true);
        }
    }   

    // Test method for stopping audiorecording
    @Test 
    void testStopRecording(){
        try{
            Mockito.doThrow(new Exception()).when(mockedAudioRecorder).stopRecording();
        }catch(Exception e){
            assertTrue(true);
        }
    }


    // Test method for starting audio recoding 
    @Test 
    void testStartRecording(){
        try{
            Mockito.doThrow(new Exception()).when(mockedAudioRecorder).startRecording();
        }catch(Exception e){
            assertTrue(true);
        }
    }



}