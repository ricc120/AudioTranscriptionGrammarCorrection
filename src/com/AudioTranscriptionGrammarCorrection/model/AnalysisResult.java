package com.AudioTranscriptionGrammarCorrection.model;

public record AnalysisResult(String originalTranscript,
                             Correction grammarCorrection) {}
