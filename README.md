# WellnessQuests 🌿🎮

**WellnessQuests** är en Android-applikation som kombinerar fysisk och mental hälsa med spelmekanik. Användaren kan genomföra quests, samla coins och ta sig vidare på en interaktiv karta. Applikationen är utvecklad i Android Studio med Java och använder MVVM-arkitektur med databinding och Firebase.

Versionen som lämnas för granskning är taggad som `v2.0`. För att ladda ner och använda denna version:

## 📦 Ladda ner projektet

1. Gå till GitHub-repot: https://github.com/Pizzabagarn/WellnessQuests
2. Klicka på fliken **"Releases"** eller **"Tags"**.
3. Leta upp taggen `v2.0`.
4. Klicka på **"Download ZIP"** under `v2.0` för att ladda ner källkoden.
5. Extrahera ZIP-filen till en mapp på din dator.

## 🚀 Starta applikationen

### 🔧 Krav

- **Android Studio** (rekommenderad version: 2022.3 (Electric Eel) eller senare)
- **Java 17**
- **Gradle** (används automatiskt via Android Studio)
- **En fysisk Android-enhet** eller emulator
- **Internetanslutning** för att ladda ned beroenden
- **Google Services JSON-fil:**
    - För testning, lägg medföljande `google-services.json`-fil (som ligger i samma mapp som denna README, under `readme&json`) i Android Studio-projektet.
    - Den ska placeras i `WellnessQuest/app/`-mappen, det vill säga samma mapp som innehåller `build.gradle` för modulen `app`.

### 💻 Steg-för-steg (Android Studio)

1. Starta Android Studio.
2. Välj **"Open"** och navigera till den extraherade mappen (`WellnessQuest`).
3. Vänta medan Android Studio synkroniserar projektet och laddar beroenden.
4. Kopiera in filen `google-services.json` från `readme&json` till mappen `WellnessQuest/app/`.
5. Öppna **Device Manager** (uppe till höger i Android Studio) och klicka på **"Create Device"** för att skapa en emulator:
    - Välj enhetstyp (t.ex. Pixel 5)
    - Välj en system image (t.ex. API 33)
    - Klicka på "Finish" och sedan "Play" för att starta emulatorn
6. Tryck på den gröna ▶️-knappen (Run ‘app’) för att bygga och starta appen.

**Obs:** Första gången kan det ta några minuter att bygga projektet på grund av Gradle-synk.

## 📱 Funktionalitet

- Firebase Authentication: Användare kan registrera sig och logga in via e-post & lösenord.
- Firestore: Coins och användardata sparas i molnet och laddas in vid inloggning.
- Testknapp för coins: En knapp på hemskärmen lägger till 1 coin och uppdaterar både UI och Firestore.
- Databinding + LiveData: Coins uppdateras direkt i gränssnittet.
- Quest-logik: Quests har kategorier ("Mind", "Fitness"), med ikon, beskrivning och coin-belöning.

## 🧠 Struktur

Projektet använder nu **MVVM-arkitektur** med databinding och LiveData.

```
com.example.wellnessquest
│
├── model          // Innehåller User, Quest, UserManager etc.
├── view           // Aktiviteter och fragments
├── viewmodel      // LoginViewModel, UserViewModel, QuestViewModel etc.
├── adapter        // RecyclerView adapters (QuestAdapter)
└── res/drawable   // Bilder som används i GUI:t
```

## 📝 Ytterligare info

- Applikationen fungerar både med emulator och fysisk Android-telefon.
- Bilder och resurser finns i `res/drawable` och laddas direkt till GUI:t.
- Projektet är i aktiv utveckling och fler funktioner är planerade.

---

