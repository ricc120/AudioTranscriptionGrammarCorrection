# Audio Transcription & Grammar Correction

This project is a java application for analyzing spoken video files in english.
It Has been created to make practice with java language and Design Pattern.
## Architecture and Design Pattern

The program is built on three principle patterns to guarantee flexibility and maintainability.
### 1. Strategy Pattern (`com.AudioTranscriptionGrammarCorrection.services`)
- **Scope:** Change the business logic in interchangeable strategy.
- **How:** Define interfaces like `ITranscriptionService`,`IGrammarService`and`IAudioExtractionService`. In this way, the core of application doesn't know if we are using a "Mock" service for test or "Google" service for real.

### 2. Facade Pattern (`com.AudioTranscriptionGrammarCorrection.processing`)
- **Scope:** Simplify the use of the system.
- **How:** Class `VideoProcessor` acts like facade. Nasconde la complessità di dover chiamare 3-4 servizi in ordine (estrazione, trascrizione, correzione) dietro un unico metodo: `processVideo()`.

### 3. Factory Pattern (`com.AudioTranscriptionGrammarCorrection.factory`)
#TODO
