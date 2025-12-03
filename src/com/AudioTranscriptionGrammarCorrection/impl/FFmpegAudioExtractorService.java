package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.services.IAudioExtractionService;
import java.io.File;
import java.io.IOException;

public class FFmpegAudioExtractorService implements IAudioExtractionService {

    @Override
    public File extractAudio(File videoFile) {
        try {
            // Define where to save the temp's file audio
            File tempAudioFile = File.createTempFile("audio_extract_", ".wav");
            tempAudioFile.deleteOnExit(); // Assure that the file is cancelled

            // Build the FFmpeg's command
            String[] command = {
                    "ffmpeg",                     // Command must be in the path
                    "-i",                         // Input file
                    videoFile.getAbsolutePath(),  // Video path
                    "-vn",                        // No video
                    "-acodec", "pcm_s16le",       // Format: WAV standard
                    "-ar", "16000",               // Sample rate 16kHz
                    "-ac", "1",                   // Mono channel
                    "-y",                         // Overload the output if exist
                    tempAudioFile.getAbsolutePath() // File audio path
            };

            // Use ProcessBuilder to throw the command
            System.out.println("INFO: Execution of FFmpeg...");
            ProcessBuilder pb = new ProcessBuilder(command);

            // Redirect output error of FFmpeg on Java console
            // (if FFmpeg fails, we can see the error)
            pb.inheritIO();

            // Start the process and wait the end
            Process process = pb.start();
            int exitCode = process.waitFor();

            // Check result
            if (exitCode == 0) {
                System.out.println("INFO: Audio extraction completed.");
                return tempAudioFile;
            } else {
                System.err.println("Error: FFmpeg failed with exit code " + exitCode);
                return null;
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: FFmpeg process's execution failed.");
            e.printStackTrace();
            return null;
        }
    }
}