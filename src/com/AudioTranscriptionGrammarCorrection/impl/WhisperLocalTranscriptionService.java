package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.services.ITranscriptionService;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WhisperLocalTranscriptionService implements ITranscriptionService {

    private static final String WHISPER_EXE_PATH = "whisper/whisper-bin-x64/Release/whisper-cli.exe";
    private static final String WHISPER_MODEL_PATH = "whisper/ggml-large-v3-turbo.bin";

    @Override
    public String transcribe(File audioFile) {
        if (audioFile == null || !audioFile.exists()) {
            return "";
        }
        System.out.println("Started Whisper local transcription...");
        try {

            // Build the command:
            // whisper-cli.exe -m ggml-large-v3-turbo.bin -f input.wav --output-txt --no-timestamps
            List<String> command = new ArrayList<>();
            command.add(new File(WHISPER_EXE_PATH).getAbsolutePath());
            command.add("-m");
            command.add(new File(WHISPER_MODEL_PATH).getAbsolutePath());
            command.add("-f");
            command.add(audioFile.getAbsolutePath());
            command.add("--output-txt"); // Generate a file .txt instead of print to console
            command.add("--no-timestamps");
            command.add("--no-prints"); // Remove the colors from output for more cleaning

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.inheritIO();

            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println(exitCode);
                return "";
            }

            // Whisper creates a file adding .txt to the file's name
            File resultFile = new File(audioFile.getAbsolutePath() + ".txt");
            if (resultFile.exists()) {
                String transcription = Files.readString(resultFile.toPath());
                // Delete the temporary file
                resultFile.delete();
                // Cleaning the string with trim space and newlines
                return transcription.trim();
            } else {
                System.out.println("Error: Whisper output file does not exist." + resultFile.getAbsolutePath());
                return "";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error during Whisper transcription";
        }
    }
}
