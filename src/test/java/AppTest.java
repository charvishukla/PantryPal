

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AppTest {
    @Test
    void DummyTest1(){
        int x = 1;
        int y = 1;
        assertEquals(x,y);
    }
    
    void DummyTest2(){
        boolean name = false;
        assertFalse(name);
    }

    void DummyTest3(){
        boolean name = true;
        assertTrue(name);
    }
    
}
