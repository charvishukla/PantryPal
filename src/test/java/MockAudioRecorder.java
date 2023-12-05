
public class MockAudioRecorder extends AudioRecorder {
      private boolean isRecording = false;

    @Override
    public void startRecording() {
        // Mock implementation
        isRecording = true;
        System.out.println("Mock startRecording called");
    }

    @Override
    public void stopRecording() {
        // Mock implementation
        isRecording = false;
        System.out.println("Mock stopRecording called");
    }

    public boolean isRecording() {
        return isRecording;
    }
}