import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ModelTest {

    @Mock
    private Model model;
    private ChatGPTAPI mockedChatGPT;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new Model();
        mockedChatGPT = new ChatGPTAPI();
    }

    @Test 
    void testGenerateWithValidIngredient1() throws IOException, InterruptedException, URISyntaxException {
            String ingredient = "potato"; 
            String recipe = mockedChatGPT.generate(ingredient);
            assertTrue(recipe instanceof String, "The generated recipe should be of String type");
    }

    @Test 
    void testGenerateWithValidIngredient2() throws IOException, InterruptedException, URISyntaxException {
            String ingredient = "potato, chilli, cheese, olive oil"; 
            String recipe = mockedChatGPT.generate(ingredient);
            assertTrue(recipe instanceof String, "The generated recipe should be of String type");
    }

   
 


}