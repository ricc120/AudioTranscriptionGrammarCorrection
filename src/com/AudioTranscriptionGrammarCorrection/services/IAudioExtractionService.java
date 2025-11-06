package com.AudioTranscriptionGrammarCorrection.services;
import java.io.File;

public interface IAudioExtractionService {
    File extractAudio(File videoFile);
}
