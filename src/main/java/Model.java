import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import OpenAI.Whisper;
import Recording.AudioRecorder;

public class Model{
    public Model(){
        String uri = "mongodb+srv://team13:CohNSwNGemiYmOOI@cluster0.1nejphw.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println("Successfully connected to MongoDB! :)");
        }
    }

    AudioRecorder audioRecorder = new AudioRecorder();

    //This activates whisper api and uses the file "recording.wav".
    public String performListening( ){

        Whisper whisper = new Whisper();
        String message = "";

         try {
            message = whisper.whisperer();
        } catch (IOException e) {
            e.printStackTrace(); // or handle the exception in an appropriate way
        } catch (URISyntaxException e) {
            e.printStackTrace(); // or handle the exception in an appropriate way
        }

        return message;
    }

    //To Start recoding audio.
    public void startRecording(){
        audioRecorder.startRecording();
    }

    //To stop the recoding process. 
    public void stopRecording(){
        audioRecorder.stopRecording();
    }

}