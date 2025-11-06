package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.services.ITranscriptionService;
import java.io.File;

public class MockTranscriptionService implements ITranscriptionService {

    @Override
    public String transcribe(File audioFile) {
        System.out.println("INFO: [MockTranscription] Simulating the transcription of the file: " + audioFile.getName());
        return "Hello, this is a mock transcript. I has some mistak.";
    }

}
