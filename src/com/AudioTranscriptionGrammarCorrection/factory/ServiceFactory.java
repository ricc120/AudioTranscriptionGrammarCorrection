package com.AudioTranscriptionGrammarCorrection.factory;
import com.AudioTranscriptionGrammarCorrection.services.*;
import com.AudioTranscriptionGrammarCorrection.impl.*;
/**
 * (FACTORY).
 * It's a utility class with static method to create the concrete
 * implementation of our services. It's the only place in all the program
 * that know "Mock" class's names.
 */
public class ServiceFactory {
    // Private constructor to prevent anyone from instantiating a ServiceFactory.
    // It's a class of static methods only
    private ServiceFactory(){
        throw new IllegalStateException("Utility class");
    }

    public static IAudioExtractionService createAudioExtractorService() {
        return new MockAudioExtractorService();
    }

    public static ITranscriptionService createTranscriptionService() {
        return new MockTranscriptionService();
    }

    public static IGrammarService createGrammarService() {
        return new MockGrammarService();
    }

}
