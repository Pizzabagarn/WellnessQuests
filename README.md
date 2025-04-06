# WellnessQuests 🌿🎮

**WellnessQuests** är en Android-applikation som kombinerar fysisk och mental hälsa med spelmekanik. Användaren kan genomföra quests, samla coins och ta sig vidare på en interaktiv karta. Applikationen är utvecklad i Android Studio med Java och följer 
just nu ingen arkitektur då detta endast är en demoversion tänkt att visa ungefär hur vi tänkt att appen ska se ut och fungera
inför retrospektpresentationen för sprint 1.

Versionen som lämnas för granskning är taggad som `v1.0-review`. För att ladda ner och använda denna version:

## 📦 Ladda ner projektet

1. Gå till GitHub-repot: https://github.com/Pizzabagarn/WellnessQuests
2. Klicka på fliken **"Releases"** eller **"Tags"**.
3. Leta upp taggen `v1.0-Demo`.
4. Klicka på **"Download ZIP"** under `v1.0-Demo` för att ladda ner källkoden.
5. Extrahera ZIP-filen till en mapp på din dator.

## 🚀 Starta applikationen

### 🔧 Krav

- **Android Studio** (rekommenderad version: 2022.3 (Electric Eel) eller senare)
- **Java 17**
- **Gradle** (används automatiskt via Android Studio)
- **En fysisk Android-enhet** eller emulator
- **Internetanslutning** för att ladda ned beroenden

### 💻 Steg-för-steg (Android Studio)

1. Starta Android Studio.
2. Välj **"Open"** och navigera till den extraherade mappen (`WellnessQuest`).
3. Vänta medan Android Studio synkroniserar projektet och laddar beroenden.
4. Välj en fysisk enhet eller starta en emulator via **Device Manager**.
5. Tryck på den gröna ▶️-knappen (Run ‘app’) för att bygga och starta appen.

**Obs:** Första gången kan det ta några minuter att bygga projektet på grund av Gradle-synk.

## 📱 Funktionalitet

- Visa coins, profil och meny i topbar
- Navigera till Quest-sida och Map-sida via ikoner
- Snygga quests i pergament-design med beskrivningar och belöningar
- Spara användardata lokalt (kommer i framtida version)

## 🧠 Struktur

Projektet följer ingen arkitektur just nu:


## 📝 Ytterligare info

- Projektet använder ingen backend eller databas i nuläget, allt är lokalt.
- Applikationen fungerar både med emulator och fysisk Android-telefon.
- Bilder och resurser finns i `res/drawable` och laddas direkt till GUI:t.







