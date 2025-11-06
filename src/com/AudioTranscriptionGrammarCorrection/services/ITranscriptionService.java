package com.AudioTranscriptionGrammarCorrection.services;
import java.io.File;

public interface ITranscriptionService {
    String transcribe(File audioFile);
}

