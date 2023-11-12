package Recording;

import java.io.*;
import javax.sound.sampled.*;



public class AudioRecorder{

    private AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, true);
    private TargetDataLine targetDataLine;

    public void startRecording() {
        Thread t = new Thread(
            new Runnable(){
                @Override
                public void run(){
                    try {
                        // the format of the TargetDataLine
                        DataLine.Info dataLineInfo = new DataLine.Info(
              TargetDataLine.class,audioFormat);

                        // the TargetDataLine used to capture audio data from the microphone
                        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                        targetDataLine.open(audioFormat);
                        targetDataLine.start();

                        // the AudioInputStream that will be used to write the audio data to a file
                        AudioInputStream audioInputStream = new AudioInputStream(
                                targetDataLine);

                        // the file that will contain the audio data
                        File audioFile = new File("recording.wav");
                        AudioSystem.write(
                                audioInputStream,
                                AudioFileFormat.Type.WAVE,
                                audioFile);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        );

        t.start();
        
    }

    public void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }
    
}
