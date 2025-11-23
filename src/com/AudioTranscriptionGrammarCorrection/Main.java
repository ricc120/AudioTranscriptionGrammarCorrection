package com.AudioTranscriptionGrammarCorrection;
import com.AudioTranscriptionGrammarCorrection.model.AnalysisResult;
import com.AudioTranscriptionGrammarCorrection.processing.VideoProcessor;
import com.AudioTranscriptionGrammarCorrection.factory.ServiceFactory;
import com.AudioTranscriptionGrammarCorrection.services.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started.");

        // --- Graphic Setup ---
        // Set native style of Windows for the window
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignore aesthetic errors
        }

        System.out.println("Applicazione avviata. Selezione file in corso...");

        // --- File Selection (JFileChooser) ---
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select the video to analyze");

        // Filter to show only videos
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "File Video (MP4, MOV, AVI, MKV)", "mp4", "mov", "avi", "mkv");
        fileChooser.setFileFilter(filter);

        // Open the window and wait user choice
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("No files selected. Exit.");
            return;
        }

        // Get the file choose by user
        File videoFile = fileChooser.getSelectedFile();
        System.out.println("File chosen: " + videoFile.getAbsolutePath());

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
        File mioVideo = new File(videoFile.getAbsolutePath());

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