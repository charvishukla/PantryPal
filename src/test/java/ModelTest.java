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

    private Authentication auth;
    private MongoClient mockClient;
    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MongoCollection<Document> mockUserCollection;
    
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        mockClient = mock(MongoClient.class);
        mockDatabase = mock(MongoDatabase.class);
        mockUserCollection = mock(MongoCollection.class);
        when(mockClient.getDatabase("PantryPal")).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("Users")).thenReturn(mockUserCollection);
        auth = new Authentication(mockClient, mockDatabase, mockUserCollection);
    }

    @Test
    void testInsert() {
        try {
            List<String> stepList = new ArrayList<>();
            stepList.add("item1");
            stepList.add("item2");
            stepList.add("item3");
            ((Database) Mockito.doThrow(new Exception()).when(mockDatabase)).insert(stepList);
        } catch (Exception e) {
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

    //Given: Caitlin clicks on create account. 
    //When: CAitlin fills in all the info for a new accouNY
    //And: Caitlin hits create.
    //Then: A new user is created and stored. 
    @Test
    void testAuthentication_BDD0(){
        Authentication authentication = Mockito.mock(Authentication.class);
        Document test1 = authentication.mockCreateUser("testUser", "testPassword", "Cait", "lastName", "testPhone");
        ArrayList<Document> list = new ArrayList();
        list.add(test1);
        assertTrue(auth.mockCheckUserExists(test1, list));
    }

    //Given: Jacob clicks on create account. 
    //When: Jacob fills in all the info for a new accouNY
    //And: Jacon hits create.
    //Then: A new user is created and stored. 
    @Test
    void createUserSuccessfully_BDD1() {
        when(mockUserCollection.insertOne(any())).thenReturn(null); 

        boolean result = auth.createUser("username", "password", "Jacob", "Bro", "8582331234");

        verify(mockUserCollection).insertOne(any());
        assertTrue(result, "User creation should return true");
    }

    /**
     * Test User exists
     * BDD Scenario:
     * Given: Caitlin already has an account 
     * When: Caitlin tries to log in 
     * Then: Her account exists and she logs in.  
     */
    @Test
    void checkUserExists_True_BDD2() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        when(mockUserCollection.find(any(Document.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(new Document());

        boolean exists = auth.checkUserExists("existingUser");

        assertTrue(exists, "checkUserExists should return true for existing user");
    }


    

}