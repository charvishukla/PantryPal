
import java.io.IOException;
import java.net.URISyntaxException;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class MockWhisper extends Whisper {

    public static boolean isMockHandleErrorResponseCalled = false;
    public static boolean isMockHandleSuccessResponseCalled = false;
    public static boolean isMockWriteFileToOutputStreamCalled = false;

    public static String audioToText(File file) throws IOException, URISyntaxException {
        return "Mocked text for file: " + file.getName();
    }

    // Mock implementation of handleErrorResponse
    public static void mockHandleErrorResponse(HttpURLConnection connection) {
        isMockHandleErrorResponseCalled = true;
        String mockErrorMessage = "Simulated error response for testing";
        System.out.println("Mock handle error response called: " + mockErrorMessage);
    }

    // Mock implementation of handleSuccessResponse
    public static String mockHandleSuccessResponse(HttpURLConnection connection) {
        isMockHandleSuccessResponseCalled = true;
        System.out.println("Mock handle success response called");
        return "Simulated successful response text";
    }

    // Mock implementation of writeFileToOutputStream
    public static void mockWriteFileToOutputStream(
        OutputStream outputStream, File file, String boundary) throws IOException {
        isMockWriteFileToOutputStreamCalled = true;
        System.out.println("Mock write file to output stream called");
        outputStream.write(("Mock data for file: " + file.getName()).getBytes());
    }

    // Method to reset mock flags for testing
    public static void resetMockFlags() {
        isMockHandleErrorResponseCalled = false;
        isMockHandleSuccessResponseCalled = false;
        isMockWriteFileToOutputStreamCalled = false;
    }

}