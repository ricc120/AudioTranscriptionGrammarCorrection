# Audio Transcription & Grammar Correction

This project is a java application for analyzing spoken video files in english.
It has been created to make practice with java language and Design Pattern.
## Architecture and Design Pattern

The program is built on three main patterns to guarantee flexibility and maintainability.
### 1. Strategy Pattern (`com.AudioTranscriptionGrammarCorrection.services`)

- **Scope:** Change the business logic in interchangeable strategies.
- **How:** Define interfaces like `ITranscriptionService`,`IGrammarService`and`IAudioExtractionService`. In this way,
  the core of application doesn't know if we are using a "Mock" or a "Google" service.

### 2. Facade Pattern (`com.AudioTranscriptionGrammarCorrection.processing`)
- **Scope:** Simplify the use of the system.
- **How:** Class `VideoProcessor` acts like facade. Hides the complexity of calling the services, behind one method:
  `processVideo()`.

### 3. Factory Pattern (`com.AudioTranscriptionGrammarCorrection.factory`)

- **Scope:** Centralize the creation of services and uncouple the client (`Main`) from concrete implementations.
- **How:** `ServiceFactory` it's the only point of application that knows implementations class'names.

## Execution flow

1. The `Main` (client) asks `ServiceFactory` to build the services.
2. The `Factory` creates concrete strategies (es. `MockGrammarService`) and return them masked by their interfaces (es.
   `IGrammarService`).
3. `Main` injects those services in the constructor of `VideoProcessor`.
4. The `Facade` receives the strategies without knowing their implementations.
5. `Facade` manages the work and delegates every task to the corresponding strategy.

## How to start

At present the project uses Mock implementations so it doesn't require external APIs.

To start:

1. Compile all `.java` classes.
2. Execute `Main.java` class.
3. The analysis output will be printed on the console.