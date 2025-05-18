WellnessQuests 🌿🎮
WellnessQuests är en Android-applikation som kombinerar fysisk och mental hälsa med spelmekanik. Användaren kan genomföra quests, samla coins och ta sig vidare på en interaktiv karta med nivåer. Applikationen är utvecklad i Android Studio med Java och använder MVVM-arkitektur med databinding, LiveData och Firebase.

Versionen som lämnas för granskning är taggad som v3.0. Denna version innehåller bland annat quest-verifiering med ML Kit och ett nytt nivåsystem.

📦 Ladda ner projektet
Gå till GitHub-repot: https://github.com/Pizzabagarn/WellnessQuests

Klicka på fliken "Releases" eller "Tags"

Leta upp taggen v3.0

Klicka på "Download ZIP" under v3.0 för att ladda ner källkoden

Extrahera ZIP-filen till en mapp på din dator

🚀 Starta applikationen
🔧 Krav
Android Studio (rekommenderad version: 2022.3 (Electric Eel) eller senare)

Java 17

Gradle (används automatiskt via Android Studio)

En fysisk Android-enhet eller emulator

Internetanslutning för att ladda ner beroenden

Google Services JSON-fil:

För testning, lägg medföljande google-services.json-fil (som ligger i samma mapp som denna README, under readme&json) i Android Studio-projektet.

Den ska placeras i WellnessQuest/app/-mappen, det vill säga samma mapp som innehåller build.gradle för modulen app.

💻 Steg-för-steg (Android Studio)
Starta Android Studio.

Välj "Open" och navigera till den extraherade mappen (WellnessQuest).

Vänta medan Android Studio synkroniserar projektet och laddar beroenden.

Kopiera in filen google-services.json från samma mapp som readme ligger i canvas, till mappen WellnessQuest/app/.

Öppna Device Manager (uppe till höger i Android Studio) och klicka på "Create Device" för att skapa en emulator:

Välj enhetstyp (t.ex. Pixel 5)

Välj en system image (t.ex. API 33)

Klicka på "Finish" och sedan "Play" för att starta emulatorn

Tryck på den gröna ▶️-knappen (Run ‘app’) för att bygga och starta appen.

Obs: Första gången kan det ta några minuter att bygga projektet på grund av Gradle-synk.

📱 Funktionalitet
Firebase Authentication – Registrera och logga in med e-post och lösenord

Firestore – Coins, nivådata och quests sparas i molnet och laddas vid inloggning

Databinding + LiveData – Coins och quests uppdateras direkt i gränssnittet

Quest-logik – Varje quest har:

Titel, beskrivning, kategori (t.ex. “Mind”, “Fitness”)

Belöning i coins

Ikon

ValidTag-lista för bildverifiering

ML Kit-integration – När ett quest ska verifieras får användaren ta eller ladda upp en bild. Bilden analyseras lokalt med ML Kit för att jämföras med questets taggar. Om taggarna matchar godkänns questet automatiskt.

Dagbokssystem – Efter verifiering sparas en dagbokspost med bild och textbeskrivning av questet.

Interaktiv nivå-karta – Varje nivå innehåller 8 quests. Användaren låser upp nästa nivå med coins och kan klicka på varje nivå för att visa detaljer i en toolbox.(Finns men inte släppt än i denna versionen)

Offline-stöd – De flesta funktioner fungerar utan konstant internetförbindelse (undantaget inloggning och databassynk).