package com.AudioTranscriptionGrammarCorrection.services;
import com.AudioTranscriptionGrammarCorrection.model.Correction;

public interface IGrammarService {
    Correction checkGrammar(String text);
}
