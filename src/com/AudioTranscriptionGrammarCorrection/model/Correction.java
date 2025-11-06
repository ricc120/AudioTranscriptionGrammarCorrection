package com.AudioTranscriptionGrammarCorrection.model;
import java.util.List;
/**
 *  (POJO / Record)
 *
 * This is a "data carrier". It's an immutable object
 * that serves to contain the results of the grammatical analysis.
 */
public record Correction(String originalText,
                         String correctedText,
                         List<String> suggestions) {}
