package com.AudioTranscriptionGrammarCorrection.impl;
import com.AudioTranscriptionGrammarCorrection.services.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;
import java.io.*;

public class VoskTranscriptionService implements ITranscriptionService {

    private Model model;
    private Gson gson;

    public VoskTranscriptionService() {
        // Reduce vosk's log noise
        LibVosk.setLogLevel(LogLevel.WARNINGS);
        this.gson = new Gson();

        try {
            this.model = new Model("vosk-model-en");
            System.out.println("INFO: Vosk's model loaded correctly");
        } catch (IOException e) {
            throw new RuntimeException("CRITICAL ERROR: Vosk's model loading failed", e);
        }
    }

    @Override
    public String transcribe(File audioFile) {
        if (audioFile == null || !audioFile.exists()) {
            return  "";
        }

        System.out.println("INFO: Running Vosk's transcription...");
        StringBuilder fullTranscript = new StringBuilder();

        // Create a recognizer with 16000.0f as sample rate
        // corresponding to the one set for FFmpeg (-ar 16000)
        try (Recognizer recognizer = new Recognizer(model, 16000.0f);
            InputStream ais = new BufferedInputStream(new FileInputStream(audioFile))) {

            int bytesRead;
            byte[] b = new byte[4096]; // Read buffer

            // Read audio file in pieces and pass to the recognizer
            while ((bytesRead = ais.read(b)) >= 0) {
                if (recognizer.acceptWaveForm(b, bytesRead)) {
                    // If Vosk recognizes a completed sentence
                    fullTranscript.append(extractTextFromJson(recognizer.getResult())).append(" ");
                }
            }


        } catch(IOException e) {
            System.out.println("Error during transcription" + e.getMessage());
        }
        return  fullTranscript.toString().trim();
    }

    /**
     * helper method to clean dirty JSON from Vosk.
     * Vosk returns: { "text" : "hello world" }
     * We want only: hello world
     */
    private String extractTextFromJson(String jsonResponse) {
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        if (jsonObject.has("text")) {
            return jsonObject.get("text").getAsString();
        }
        return "";
    }
}
