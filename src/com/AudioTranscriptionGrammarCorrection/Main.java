package com.AudioTranscriptionGrammarCorrection;
import com.AudioTranscriptionGrammarCorrection.model.AnalysisResult;
import com.AudioTranscriptionGrammarCorrection.processing.VideoProcessor;
import com.AudioTranscriptionGrammarCorrection.factory.ServiceFactory;
import com.AudioTranscriptionGrammarCorrection.services.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started.");

        // --- Creation (Factory Pattern) ---
        // We ask Factory of giving services.
        // We didn't know which concrete implementation we are receiving.
        IAudioExtractionService audioService = ServiceFactory.createAudioExtractorService();
        ITranscriptionService transcriptionService = ServiceFactory.createTranscriptionService();
        IGrammarService grammarService = ServiceFactory.createGrammarService();

        // --- Assembly (Dependency Injection) ---
        // We create our own Facade and inject it with services that we
        // need to work.
        VideoProcessor processor = new VideoProcessor(
                audioService,
                grammarService,
                transcriptionService
        );

        // --- Execution (Facade Pattern) ---
        // We simulate a video file
        File mioVideo = new File("my_english_practice_day_20.mp4");

        // We call the only method of Facade
        AnalysisResult result = processor.processVideo(mioVideo);

        // --- Result ---
        if (result != null) {
            System.out.println("\n========= Analysis Result ==========");
            System.out.println("\nOriginal transcription:");
            System.out.println("  " + result.originalTranscript());

            System.out.println("\nCorrected test:");
            System.out.println("  " + result.grammarCorrection().correctedText());

            System.out.println("\nSuggestions:");
            result.grammarCorrection().suggestions()
                    .forEach(sugg -> System.out.println("  - " + sugg));
            System.out.println("=========================================");
        } else {
            System.err.println("Failed analysis.");
        }
    }
}