# Tasks

A native Android task manager app built with Java, AndroidX, Room, Dagger, and Material Design.

## Overview

`Tasks` is a productivity application for managing personal tasks, organized by state and searchable by text.

### Key features

- Add new tasks with details
- Search tasks using the top app bar search field
- Filter tasks by state: To Do, Processing, Done
- Sort tasks by state using the toolbar action
- Swipe to delete tasks with undo support
- View task details in a bottom sheet dialog
- Persist tasks locally using Room database
- Uses Dagger for dependency injection
- Built with RecyclerView and Material components

## Architecture

- `MainActivity` manages the task list and user interactions
- `CreateActivity` handles task creation
- `MainViewModel` provides task data and database operations
- `TasksAdapter` renders task items inside a RecyclerView
- `InfoFragment` displays task details
- Room stores tasks persistently on the device
- Dagger injects dependencies into the activity and view model

## Project structure

```
Tasks/
├── app/
│   ├── src/main/java/com/erank/tasks/
│   │   ├── activities/          # App activities and UI controllers
│   │   ├── fragments/           # Task detail dialog fragment
│   │   ├── interfaces/          # Click, update, delete callbacks
│   │   ├── models/              # Data models and enums
│   │   └── utils/               # Adapter, database, navigation utilities
│   ├── src/main/res/            # Layouts, strings, drawables, menus
│   └── build.gradle             # Application module Gradle configuration
├── build.gradle                 # Root Gradle configuration
├── gradle/                      # Gradle wrapper files
├── gradlew
└── gradlew.bat
```

## Requirements

- Android Studio or command-line Gradle
- Java 8 compatibility
- Android SDK API 29
- Minimum supported SDK: 21

## Build and run

From the repository root:

```bash
./gradlew assembleDebug
```

To install and run on a connected device or emulator:

```bash
./gradlew installDebug
```

Or open the project in Android Studio and run the `app` module.

## Notes

- The app uses `androidx.room:room-runtime` for local persistence.
- The UI is based on `RecyclerView` and Material Design components.
- Tasks can be restored after deletion using the undo snackbar.

## Contact

For questions about this project, reach out to the repository owner on GitHub: [eranCat](https://github.com/eranCat)
