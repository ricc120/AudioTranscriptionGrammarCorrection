package com.AudioTranscriptionGrammarCorrection.processing;
import com.AudioTranscriptionGrammarCorrection.services.*;
import com.AudioTranscriptionGrammarCorrection.model.*;
import java.io.File;
/**
 * (FACADE)
 * Its scope is to hide the complexity of the entire process
 * behind a clean method: processVideo().
 */

public class VideoProcessor {

    private final IAudioExtractionService audioExtractionService;
    private final IGrammarService grammarService;
    private final ITranscriptionService transcriptionService;

    public VideoProcessor(IAudioExtractionService audioExtractionService,
                          IGrammarService grammarService,
                          ITranscriptionService transcriptionService) {
        this.audioExtractionService = audioExtractionService;
        this.grammarService = grammarService;
        this.transcriptionService = transcriptionService;
    }

    public AnalysisResult processVideo(File videoFile) {

        System.out.println("--- Begin process (Facade) for: " + videoFile.getName() + " ---");

        File audioFile = audioExtractionService.extractAudio(videoFile);
        if  (audioFile == null) {
            System.out.println("--- ERROR: Audio file is null ---");
            return null;
        }

        String transcript = transcriptionService.transcribe(audioFile);
        if (transcript == null ||  transcript.isEmpty()) {
            System.out.println("--- ERROR: Transcript is empty ---");
        }

        Correction correction = grammarService.checkGrammar(transcript);
        return new AnalysisResult(transcript, correction);
    }


}
