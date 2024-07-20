# Android PlanetSpotters

An planet spotting app created to test out Android development best practices with clean architecture.

## Architecture

Model-View-ViewModel (MVVM)
Domain Layer with UsesCases
Repository Pattern
Dependency Injection

## Frameworks

Jetpack Compose
Compose Navigation
Hilt
Coroutines

## Requirements

### Gemini API key

PlanetSpotters uses the [Gemini API](https://ai.google.dev/) to load information on spotted planets.  

To use the API, you will need to obtain a free developer API key. See the
[Gemini API Documentation](https://ai.google.dev/gemini-api/docs/api-key) for instructions.

Once you have the key, add this line to the `gradle.properties` file in the project's root folder:

```
geminiApiKey=<your_actual_api_key>
```

The app is still usable without an API key, though you won't be able to look up information on your planet.
