# WellnessQuests

**WellnessQuests** is an Android application that combines physical and mental health with game mechanics. Users complete quests, collect coins, and progress through an interactive level-based map. The application is developed in Android Studio using Java and follows the MVVM architecture pattern with DataBinding, LiveData, and Firebase integration.

This submission corresponds to tag **v4.0**, which includes quest verification via ML Kit, a dynamic level system, sound-effects, music, and improved project structure for evaluation.

---

## Download the Project

1. Visit the GitHub repository:  
   https://github.com/Pizzabagarn/WellnessQuests

2. Navigate to the **Releases** or **Tags** section.

3. Locate the tag `v4.0`.

4. Click **Download ZIP** to download the source code.

5. Extract the ZIP file to a folder on your computer.

---

## Requirements

- Android Studio (recommended version: 2022.3 "Electric Eel" or later)
- Java 17
- Gradle (automatically handled by Android Studio)
- A physical Android device or emulator
- Internet connection (for downloading dependencies)

**Firebase configuration:**

- For testing purposes, place the provided `google-services.json` file (included alongside this README in the `readme&json/` folder on Canvas) into the Android project at:
  WellnessQuest/app/google-services.json

---

## How to Run the Application

1. Open **Android Studio**.

2. Select **Open** and navigate to the extracted `WellnessQuest` folder.

3. Wait for Android Studio to sync the project and download required dependencies.

4. Copy the `google-services.json` file into `WellnessQuest/app/`.

5. Open **Device Manager** in Android Studio (top-right corner), and create a virtual device:

- Choose a device type (e.g., Pixel 5)
- Select a system image (e.g., API 33)
- Click **Finish**, then press **Play** to start the emulator

6. Press the green **Run 'app'** button to build and launch the application.

> Note: The first build may take a few minutes due to Gradle sync.

---

## Features

- **Firebase Authentication**: Register and log in with email and password
- **Cloud Firestore**: Coins, level data, and quest status are saved in the cloud
- **DataBinding and LiveData**: UI elements update in real-time as data changes
- **Quest system**: Each quest contains:
- Title, description, and category (e.g., "Mind", "Fitness")
- Coin reward
- A list of valid image tags for verification

- **ML Kit Integration**:  
  During quest verification, users upload or capture a photo. ML Kit analyzes the image locally and compares it to the expected tags. If matching, the quest is automatically marked as complete.

- **Interactive Level Map**:  
  Each level contains 8 quests. Users unlock levels using coins and can tap each level node to view information.  
  

- **Offline support**:  
  Basic functionality such as quest display and UI navigation may work offline **after login**, but the app **requires an internet connection to log in** and to sync data with Firestore. Offline support is limited and not fully implemented in this version

---

## Authors

- Alexander Westman
- Mena Nasir
- Gen Félix Teramoto
- Lowisa Svensson Christell

---

## License

This project is for educational purposes and part of a university course (System Development and Project, Spring 2025).