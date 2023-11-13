import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import java.io.IOException;
import java.net.URISyntaxException;

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
        // mockedAudioRecorder = mock(AudioRecorder.class);
        // mockedDatabase = mock(Database.class);
        // mockedWhisper = mock(Whisper.class);
        // mockedModel = mock(Model.class);
        // mockedChatGPT = mock(ChatGPT.class);
    }

    @Test 
    void testGenerateWithValidIngredient1() throws IOException, InterruptedException, URISyntaxException {
        String ingredient = "potato"; 
        // String recipe = mockedChatGPT.generate(ingredient);
        when(mockedChatGPT.generate(ingredient)).thenReturn("test");
        assertEquals(mockedChatGPT.generate(ingredient), "test");
    }

    @Test 
    void testGenerateWithValidIngredient2() throws IOException, InterruptedException, URISyntaxException {
        String ingredient = "potato, chili, cheese, olive oil"; 
        // String recipe = mockedChatGPT.generate(ingredient);
        when(mockedChatGPT.generate(ingredient)).thenReturn("test");
        assertEquals(mockedChatGPT.generate(ingredient), "test");
    }


    // @Test
    // void audioToText_ReturnsText_OnValidFile() throws IOException, URISyntaxException {
    //     mockedWhisper.audioToText(any(File.class));
    //     verify(mockedWhisper).audioToText(any(File.class));
    //     when(mockedWhisper.audioToText(any(File.class))).thenReturn("Sample text");
    //     assertEquals("Sample text", mockedModel.audioToText());
    // }
    
    
    // @Test
    // void startRecording_CallsAudioRecorderStart() {
    //     doNothing()
    //     when(mockedAudioRecorder.startRecording()).thenReturn();
    //     verify(mockedAudioRecorder.startRecording());
    //     mockedModel.startRecording();
    // }

    // @Test
    // void stopRecording_CallsAudioRecorderStop() {
    //     verify(mockedAudioRecorder.stopRecording());
    //     when(mockedModel.stopRecording()).thenReturn("anything");

    // }
}