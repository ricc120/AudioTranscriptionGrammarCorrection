package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.services.IGrammarService;
import com.AudioTranscriptionGrammarCorrection.model.Correction;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LanguageToolGrammarService implements IGrammarService {

    // LanguageTool is an expensive object to build.
    // We create it only one time and keep in the constructor
    // as private field.
    private final JLanguageTool langTool;

    public LanguageToolGrammarService() {
        this.langTool = new JLanguageTool(new BritishEnglish());
    }

    @Override
    public Correction checkGrammar(String text) {
        try {
            // check() method executes the analysis and return a list of error
            List<RuleMatch> matches = langTool.check(text);

            // Convert errors in a readable suggestions list
            List<String> suggestions = new ArrayList<>();

            // Create a StringBuilder from original text to modify the text quickly
            StringBuilder corrected = new StringBuilder(text);

            // Iterate on the error in reverse. In the other way
            // if we substituted the first word, text's length would change
            // and all index would be wrong.
            for (int i = matches.size() - 1; i >= 0; i--) {
                RuleMatch match = matches.get(i);

                // Save the suggestion for the list
                suggestions.add(0,String.format(
                        "Error '%s' -> Suggestion: %s",
                        text.substring(match.getFromPos(),match.getToPos()),
                        match.getSuggestedReplacements()
                ));

                // Apply the correction to the text if there is a suggestion at least
                if (!match.getSuggestedReplacements().isEmpty()) {
                    String bestSuggestion = match.getSuggestedReplacements().get(0);
                    corrected.replace(match.getFromPos(), match.getToPos(), bestSuggestion);
                }
            }

            // LanguageTool doesn't creat a unique correct text
            // because an error can have more than one suggestion.
            // Return original text "correctedText" field and the
            // suggestions list.
            return new Correction(text,corrected.toString(),suggestions);

        } catch (IOException e) {
            System.err.println("Error during grammatical analysis: " + e.getMessage());
            return new Correction(text,text,List.of("Error during analysis: " + e.getMessage()));

        }
    }
}
