import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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
        mockedModel = new Model();
        mockedChatGPT = new ChatGPT();
    }

    @Test 
    void testGenerateWithValidIngredient1() throws IOException, InterruptedException, URISyntaxException {
            String ingredient = "potato"; 
            String recipe = mockedChatGPT.generate(ingredient);
            assertTrue(recipe instanceof String, "The generated recipe should be of String type");
    }

    @Test 
    void testGenerateWithValidIngredient2() throws IOException, InterruptedException, URISyntaxException {
            String ingredient = "potato, chili, cheese, olive oil"; 
            String recipe = mockedChatGPT.generate(ingredient);
            assertTrue(recipe instanceof String, "The generated recipe should be of String type");
    }

    @Test
    void audioToText_ReturnsText_OnValidFile() throws IOException, URISyntaxException {
        mockedWhisper.audioToText(any(File.class));
        Mockito.verify(mockedWhisper).audioToText(any(File.class));
        when(mockedWhisper.audioToText(any(File.class))).thenReturn("Sample text");
        assertEquals("Sample text", mockedModel.audioToText());
    }
}