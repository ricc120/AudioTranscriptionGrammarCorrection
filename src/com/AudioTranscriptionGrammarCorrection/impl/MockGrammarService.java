package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.model.Correction;
import com.AudioTranscriptionGrammarCorrection.services.IGrammarService;
import java.util.List;

public class MockGrammarService implements IGrammarService {

    @Override
    public Correction checkGrammar(String text) {
        System.out.println("INFO: [MockGrammar] Simulation correction for: '" + text + "'");
        String corrected = "Hello, this is an example transcript. I think I have some mistakes.";
        List<String> suggestions = List.of(
                "'a example' -> 'an example'",
                "'has' -> 'have'",
                "'mistak' -> 'mistakes'"
        );
        return new Correction(text, corrected, suggestions);
    }
}
