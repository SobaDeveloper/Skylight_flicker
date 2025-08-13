# SkylightFlickr

A demo Android photo search app, demonstrating modern practices using Kotlin, Jetpack Compose, Koin, and Clean / MVVM architecture.

![screen-20250731-1706223 (1)](https://github.com/user-attachments/assets/3414df8b-8a06-4725-a0b1-2fd36ea4223b)
![infinite scroll](https://github.com/user-attachments/assets/41fb7090-85b8-4757-9078-aaf766698019)

## Built With

- **[Kotlin](https://kotlinlang.org/)** – First class and official programming language for Android development
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** – Modern toolkit for building native UI
- **[Coroutines](https://developer.android.com/kotlin/coroutines)** – Asynchronous programming and concurrency
- **[Flow](https://developer.android.com/kotlin/flow)** – Reactive streams for handling asynchronous data
- **[Koin](https://insert-koin.io/)** – Lightweight dependency injection for Kotlin
- **[Jetpack Libraries](https://developer.android.com/jetpack)**
  - [Navigation Compose](https://developer.android.com/guide/navigation) - Type-safe navigation for Compose
  - [Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3) - Material Design 3 components
- **[Moshi](https://github.com/square/moshi)** – JSON serialization library
- **[Retrofit](https://square.github.io/retrofit/)**  - Type-safe HTTP client for Android and Java.
- **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)** – Kotlin's native serialization library.
- **[Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)** – Efficient loading and displaying of paged data   
- **[Glide](https://github.com/bumptech/glide)** – Image loading library for Android.
- **[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)** – Modern replacement for SharedPreferences.
- **[JUnit](https://junit.org/junit4/)** - Unit testing framework
- **[MockK](https://mockk.io/)** - Mocking library for Kotlin


## Feature Requirements

- Photo search
  - Query Flickr's API for a given tag
  - Display results in a gallery view
  - Infinite scroll on search results (optional)
- Photo detail page
  - Hero image
  - Photo title, description, date taken, date posted, link back to search results
- Unit Tests


## Architecture Overview

```
📦 SkylightFlickr
├── 🧩 app          # UI components, Navigation, ViewModels, and user interaction handling
│   ├── feature     # Jetpack Compose screens, UI logic, and ViewModels
│   ├── navigation  # Compose destinations and navigation graph
│   └── ui          # Material 3 styling, theming, and components
│
├── 🧩 domain       # Business logic, use cases, and domain models
│   ├── model       # Domain models
│   ├── usecase     # UseCases coordinating business rules
│   ├── paging      # Paging data sources
│   ├── repo        # Repositories
│   └── mapper      # DTO to domain model mappers
│
├── 🧩 data         # Data sources (network)
│   ├── service     # Retrofit interfaces and API DTOs
│   └── network     # Networking config (Retrofit, interceptors)
│
├── 🧩 core         # Shared utilities and common functionality
│   └── util        # Date formatting, extension functions, etc.
```


## Project Setup

To build and run this project, please add the following to the `local.properties` file at the root of your project:

```
FLICKR_BASE_URL=https://www.flickr.com
FLICKR_API_KEY=[YOUR API KEY]
```

