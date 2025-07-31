# SkylightFlickr

A demo Android photo search app using the Flickr API, showcasing modern practices with Kotlin, Jetpack Compose, Koin, and Clean / MVVM architecture.

---

## Built With

- **[Kotlin](https://kotlinlang.org/)** – First class and official programming language for Android development.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** – Modern toolkit for building native UI.
- **[Coroutines](https://developer.android.com/kotlin/coroutines)** – For asynchronous programming and managing long-running tasks.
- **[Flow](https://developer.android.com/kotlin/flow)** – Kotlin Reactive streams for emitting events.
- **[Koin](https://insert-koin.io/)** – Lightweight dependency injection for Kotlin.
- **[Jetpack Libraries](https://developer.android.com/jetpack)**
  - [Navigation](https://developer.android.com/guide/navigation)
  - [Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- **[Moshi](https://github.com/square/moshi)** – JSON library for Kotlin and Java.
- **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)** – Kotlin's native serialization library.
- **[Glide](https://github.com/bumptech/glide)** – Image loading library for Android.
- **[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)** – Modern replacement for SharedPreferences.

---

## Architecture Overview

```
📦 SkylightFlickr
├── 🧩 app          # UI layer: features, navigation, theme, and ViewModels
│   ├── feature     # Jetpack Compose screens, UI logic, and ViewModels
│   ├── navigation  # Compose destinations and navigation graph
│   └── theme       # Material 3 styling and theming
│
├── 🧩 domain       # Business logic and app rules
│   ├── model       # Domain models
│   ├── usecase     # UseCases coordinating business rules
│   ├── repository  # Repositories
│   └── mapper      # DTO to domain model mappers
│
├── 🧩 data         # Data sources (network)
│   ├── service     # Retrofit interfaces and API DTOs
│   └── network     # Networking config (Retrofit, interceptors)
│
├── 🧩 core         # Shared utilities and constants
│   └── util        # Date formatting, extension functions, etc.
```

## Project Setup

To build and run this project, please add the following to the `local.properties` file at the root of your project:

```
FLICKR_BASE_URL=https://www.flickr.com
FLICKR_API_KEY=0bd5c6b1be09e68292e53d0fe2e8e2ad
```

<img width="852" height="397" alt="Screenshot 2025-07-31 at 4 32 47 PM" src="https://github.com/user-attachments/assets/84dacc10-1097-4483-ae14-3f6379fc68ce" />

