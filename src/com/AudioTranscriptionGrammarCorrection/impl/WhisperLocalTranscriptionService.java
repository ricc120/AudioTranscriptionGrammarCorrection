package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.services.ITranscriptionService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WhisperLocalTranscriptionService implements ITranscriptionService {

    private static final String WHISPER_EXE_PATH = "whisper/whisper-bin-x64/Release/whisper-cli.exe";
    private static final String WHISPER_MODEL_PATH = "whisper/ggml-small.en.bin";

    @Override
    public String transcribe(File audioFile) {
        if (audioFile == null || !audioFile.exists()) {
            return "";
        }
        System.out.println("Started Whisper local transcription...");
        try {
            List<String> command = new ArrayList<>();
            command.add(new File(WHISPER_EXE_PATH).getAbsolutePath());
            command.add("-m");
            command.add(new File(WHISPER_MODEL_PATH).getAbsolutePath());
            command.add("-f");
            command.add(audioFile.getAbsolutePath());
            command.add("--output-txt");
            command.add("--no-timestamps");
            command.add("--no-prints");

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.inheritIO();

            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println(exitCode);
                return "";
            }

            File resultFile = new File(audioFile.getAbsolutePath() + ".txt");
            if (resultFile.exists()) {
                String transcription = Files.readString(resultFile.toPath());
                resultFile.delete();
                return transcription.trim();
            } else {
                System.out.println("Error");
                return "";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error during transcription";
        }
    }
}
