package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.services.IAudioExtractionService;
import java.io.File;
import java.io.IOException;

public class MockAudioExtractorService implements  IAudioExtractionService {

    @Override
    public File extractAudio(File videoFile) {
        System.out.println("INFO: [MockAudioExtractor] Simulation audio extraction from: " + videoFile.getName());
        try {
            File tempAudio = File.createTempFile("mock_audio_", ".wav");
            System.out.println("INFO: [MockAudioExtractor] audio file fictitious created in: " + tempAudio.getAbsolutePath());
            tempAudio.deleteOnExit();
            return tempAudio;
        } catch (IOException e) {
            System.err.println("Error in creating mock audio file");
            return null;
        }
    }

}
