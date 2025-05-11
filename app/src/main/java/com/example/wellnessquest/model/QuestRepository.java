package com.example.wellnessquest.model;

import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.Quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestRepository {

    public static Level getLevel(int levelNumber) {
        List<Quest> quests = generateQuestsForLevel(levelNumber);
        int cost = calculateLevelCost(levelNumber);
        return new Level(levelNumber, cost, quests);
    }

    public static List<Level> getAllLevels() {
        List<Level> levels = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            levels.add(getLevel(i));
        }
        return levels;
    }

    private static int calculateLevelCost(int levelNumber) {
        // Exempel: kostnaden ökar per nivå
        return 60 + (levelNumber - 1) * 20;
    }

    private static List<Quest> generateQuestsForLevel(int level) {
        List<Quest> quests = new ArrayList<>();

        switch (level) {
// Level 1 – Foundation
            case 1:
                quests.add(new Quest("q1", "Water of Clarity", "Begin your journey with clarity. Drink a full glass of water and capture it in your hand as proof of intention.", "Fitness", false, 10, Arrays.asList("glass", "bottle", "water", "drink")));
                quests.add(new Quest("q2", "Arms Toward the Sky", "Stretch both arms upward and hold for 10 seconds. Let your photo show the posture of openness.", "Fitness", false, 10, Arrays.asList("stretch", "arms", "pose", "exercise")));
                quests.add(new Quest("q3", "The First Step", "Step outside and feel the world. Capture your shoes on the ground beneath you.", "Fitness", false, 10, Arrays.asList("shoes", "outdoor", "sidewalk", "walk")));
                quests.add(new Quest("q4", "Reflection in Stillness", "Stand before a mirror and smile with peace. Capture the reflection of your strength.", "Mind", false, 10, Arrays.asList("mirror", "face", "smile", "reflection")));
                quests.add(new Quest("q5", "Roots in the Earth", "Sit calmly near nature. Photograph a tree, grass, or sky as witness.", "Mind", false, 10, Arrays.asList("tree", "grass", "sky", "outdoor")));
                quests.add(new Quest("q6", "The Word Within", "Write a word that captures how you feel. Capture the word on paper.", "Mind", false, 10, Arrays.asList("paper", "pen", "word", "writing")));
                quests.add(new Quest("q7", "Skyward Vision", "Look up and admire the sky. Capture the clouds or the clear blue as a sign of breath.", "Mind", false, 10, Arrays.asList("sky", "cloud", "blue", "light")));
                quests.add(new Quest("q8", "Motion of Awakening", "Do 10 jumping jacks to awaken the body. Capture your stance in motion or stillness.", "Fitness", false, 10, Arrays.asList("jump", "person", "movement", "fitness")));
                break;

// Level 2 – Awakening
            case 2:
                quests.add(new Quest("q9", "Steps of Progress", "Take 500 steps today and show your journey with a picture of your walking path.", "Fitness", false, 15, Arrays.asList("path", "street", "shoes", "walk")));
                quests.add(new Quest("q10", "Grateful Marks", "Write two things you're thankful for and photograph your written note.", "Mind", false, 10, Arrays.asList("note", "journal", "pen", "gratitude")));
                quests.add(new Quest("q11", "The Seated Breath", "Sit calmly and breathe deeply for 3 minutes. Let your posture be captured.", "Mind", false, 10, Arrays.asList("sitting", "chair", "person", "calm")));
                quests.add(new Quest("q12", "Lower Ground", "Complete 10 squats. Capture your mid-squat form or your preparation.", "Fitness", false, 15, Arrays.asList("squat", "fitness", "legs", "pose")));
                quests.add(new Quest("q13", "Colorful Fuel", "Prepare a healthy snack. Photograph the colors on your plate.", "Fitness", false, 15, Arrays.asList("food", "snack", "plate", "fruit")));
                quests.add(new Quest("q14", "Eyes of Curiosity", "Observe something natural: a bird, a leaf, or a cloud. Photograph what you see.", "Mind", false, 10, Arrays.asList("bird", "leaf", "tree", "sky")));
                quests.add(new Quest("q15", "Smile Shared", "Smile with someone and capture your faces together, genuine and kind.", "Mind", false, 10, Arrays.asList("smile", "people", "face", "together")));
                quests.add(new Quest("q16", "Written Intention", "Write one goal for the day and show your handwritten words.", "Mind", false, 10, Arrays.asList("paper", "goal", "pen", "note")));
                break;

// Level 3 – Grounding
            case 3:
                quests.add(new Quest("q17", "Path of Green", "Walk in a park or near trees. Photograph nature surrounding your path.", "Fitness", false, 15, Arrays.asList("tree", "grass", "path", "park")));
                quests.add(new Quest("q18", "Balanced Frame", "Hold a yoga pose for 30 seconds and capture your form and stability.", "Fitness", false, 15, Arrays.asList("yoga", "pose", "balance", "person")));
                quests.add(new Quest("q19", "Energy in a Glass", "Make a smoothie from fruit or greens and photograph the drink.", "Fitness", false, 15, Arrays.asList("smoothie", "fruit", "glass", "drink")));
                quests.add(new Quest("q20", "Memory Scroll", "Write a memory from this week. Photograph your journal page.", "Mind", false, 10, Arrays.asList("journal", "notebook", "memory", "pen")));
                quests.add(new Quest("q21", "Silence Within", "Meditate for 5 minutes and capture your seated stillness.", "Mind", false, 15, Arrays.asList("meditation", "calm", "pose", "eyes closed")));
                quests.add(new Quest("q22", "Strength in Lunge", "Do 15 lunges. Capture one mid-lunge in side view.", "Fitness", false, 20, Arrays.asList("lunge", "leg", "exercise", "balance")));
                quests.add(new Quest("q23", "Fading Light", "Watch the sunset and photograph the horizon as day ends.", "Mind", false, 10, Arrays.asList("sunset", "sky", "orange", "light")));
                quests.add(new Quest("q24", "Plated Harmony", "Prepare a healthy meal. Photograph the complete plate before eating.", "Fitness", false, 20, Arrays.asList("meal", "vegetables", "plate", "food")));
                break;

// Level 4 – Strength and Stillness
            case 4:
                quests.add(new Quest("q25", "The Long Path", "Walk 2,000 steps with focus. Capture your final destination as a sign of persistence.", "Fitness", false, 20, Arrays.asList("walk", "shoes", "distance", "path")));
                quests.add(new Quest("q26", "Rooted Push", "Do 20 push-ups. Capture your position from a side angle to show grounded strength.", "Fitness", false, 20, Arrays.asList("pushup", "arms", "pose", "exercise")));
                quests.add(new Quest("q27", "Challenge Reflected", "Write about a challenge you faced this week. Photograph your honest words.", "Mind", false, 15, Arrays.asList("journal", "writing", "note", "pen")));
                quests.add(new Quest("q28", "Forest Glimpse", "Enter a green space and photograph the natural elements around you.", "Mind", false, 15, Arrays.asList("tree", "leaf", "outdoor", "nature")));
                quests.add(new Quest("q29", "Brew of Calm", "Make yourself herbal tea or warm lemon water. Capture the steam rising from your cup.", "Mind", false, 15, Arrays.asList("tea", "cup", "steam", "drink")));
                quests.add(new Quest("q30", "Steady as Stone", "Hold a plank pose for 30 seconds. Photograph the moment or aftermath.", "Fitness", false, 25, Arrays.asList("plank", "core", "pose", "strength")));
                quests.add(new Quest("q31", "Open Book", "Read 5 pages of a book. Take a photo of your reading spot.", "Mind", false, 10, Arrays.asList("book", "reading", "pages", "quiet")));
                quests.add(new Quest("q32", "Smile and Scribble", "Smile and hold up a note with a positive thought. Capture both.", "Mind", false, 15, Arrays.asList("smile", "paper", "handwriting", "person")));
                break;
// Level 5 – Expansion
            case 5:
                quests.add(new Quest("q33", "Ten-Minute Run", "Run or jog for 10 minutes. Capture your shoes or path after finishing.", "Fitness", false, 25, Arrays.asList("run", "shoes", "outdoor", "sweat")));
                quests.add(new Quest("q34", "Meal of Balance", "Prepare a balanced meal with protein, vegetables, and carbs. Show the full plate.", "Fitness", false, 25, Arrays.asList("meal", "plate", "food", "vegetables")));
                quests.add(new Quest("q35", "The Power Chair", "Hold a wall sit for 45 seconds. Photograph your form from the side or reflection.", "Fitness", false, 25, Arrays.asList("wall", "legs", "pose", "chair")));
                quests.add(new Quest("q36", "Clear Reflection", "Write a paragraph on how you've grown in the last 5 days. Photograph your text.", "Mind", false, 20, Arrays.asList("reflection", "journal", "write", "pen")));
                quests.add(new Quest("q37", "Deep Focus", "Meditate in silence or with calm sounds for 10 minutes. Capture your seated posture or view.", "Mind", false, 20, Arrays.asList("meditation", "eyes closed", "calm", "person")));
                quests.add(new Quest("q38", "Balance of One", "Stand on one leg with hands overhead for 30 seconds. Photograph the pose.", "Fitness", false, 25, Arrays.asList("balance", "yoga", "pose", "standing")));
                quests.add(new Quest("q39", "Fruit Mosaic", "Create a colorful fruit bowl. Photograph the presentation before eating.", "Fitness", false, 20, Arrays.asList("fruit", "bowl", "color", "snack")));
                quests.add(new Quest("q40", "Still Frame", "Find a quiet place that brings peace. Photograph it as a symbol of rest.", "Mind", false, 20, Arrays.asList("peaceful", "quiet", "place", "landscape")));
                break;

// Level 6 – Consolidation
            case 6:
                quests.add(new Quest("q41", "Hillside Breath", "Walk uphill or on an incline for 10 minutes. Capture the incline or horizon ahead.", "Fitness", false, 30, Arrays.asList("hill", "slope", "path", "shoes")));
                quests.add(new Quest("q42", "Reflective Brew", "Prepare a warm drink and sit quietly reflecting for 5 minutes. Capture the drink and setting.", "Mind", false, 20, Arrays.asList("tea", "cup", "steam", "calm")));
                quests.add(new Quest("q43", "Bridge of Strength", "Hold a bridge pose for 30 seconds. Capture your form from a safe angle.", "Fitness", false, 25, Arrays.asList("bridge", "pose", "exercise", "core")));
                quests.add(new Quest("q44", "Letters to Tomorrow", "Write a note to your future self. Photograph the note or the pen in motion.", "Mind", false, 20, Arrays.asList("paper", "pen", "writing", "letter")));
                quests.add(new Quest("q45", "Mountain Stillness", "Meditate for 12 minutes in total silence. Capture your seated space.", "Mind", false, 25, Arrays.asList("person", "pose", "eyes closed", "calm")));
                quests.add(new Quest("q46", "Fruitful Journey", "Create a meal or snack with 3+ fruits. Photograph the colorful spread.", "Fitness", false, 20, Arrays.asList("fruit", "bowl", "plate", "color")));
                quests.add(new Quest("q47", "Flow and Form", "Practice yoga with three transitions. Capture one transition mid-motion.", "Fitness", false, 30, Arrays.asList("yoga", "pose", "motion", "exercise")));
                quests.add(new Quest("q48", "Quiet Horizon", "Watch a sunrise or early morning light. Capture the view and its colors.", "Mind", false, 20, Arrays.asList("sunrise", "sky", "light", "morning")));
                break;

// Level 7 – Resilience
            case 7:
                quests.add(new Quest("q49", "Steadfast Footing", "Walk 3,000 steps and photograph your route or worn shoes.", "Fitness", false, 30, Arrays.asList("walk", "shoes", "step", "route")));
                quests.add(new Quest("q50", "Wisdom Poured", "Write a lesson you've learned this month. Capture the reflection in writing.", "Mind", false, 25, Arrays.asList("write", "lesson", "pen", "notebook")));
                quests.add(new Quest("q51", "Legs of Stone", "Hold a wall sit for 1 minute. Capture the effort in your posture.", "Fitness", false, 30, Arrays.asList("wall", "legs", "pose", "strength")));
                quests.add(new Quest("q52", "Still Center", "Meditate for 15 minutes. Show the calm space you created.", "Mind", false, 25, Arrays.asList("meditation", "space", "still", "quiet")));
                quests.add(new Quest("q53", "Create Light", "Light a candle and sit for 5 minutes. Photograph the candle's flame.", "Mind", false, 20, Arrays.asList("candle", "flame", "light", "focus")));
                quests.add(new Quest("q54", "Push Forward", "Complete 25 push-ups. Photograph the final rep or aftermath.", "Fitness", false, 30, Arrays.asList("pushup", "strength", "exercise", "pose")));
                quests.add(new Quest("q55", "Walking Reflection", "Take a long reflective walk and photograph something you notice anew.", "Mind", false, 20, Arrays.asList("path", "walk", "nature", "detail")));
                quests.add(new Quest("q56", "Fuel with Purpose", "Prepare a nutrient-rich breakfast. Capture its color and balance.", "Fitness", false, 25, Arrays.asList("breakfast", "food", "plate", "nutrition")));
                break;

// Level 8 – Mastery
            case 8:
                quests.add(new Quest("q57", "Clarity in Sweat", "Run or bike for 15 minutes. Show your face, gear or environment after.", "Fitness", false, 35, Arrays.asList("run", "bike", "sweat", "helmet")));
                quests.add(new Quest("q58", "The Written Core", "Write your personal values. Capture the list as your reminder.", "Mind", false, 30, Arrays.asList("write", "values", "notebook", "pen")));
                quests.add(new Quest("q59", "Strength Sequence", "Complete a set: 10 push-ups, 20 squats, 30s plank. Show yourself or a timer.", "Fitness", false, 35, Arrays.asList("exercise", "pushup", "plank", "squat")));
                quests.add(new Quest("q60", "Disconnect to Reconnect", "Be off-screen for 20 minutes. Capture an alternative peaceful activity.", "Mind", false, 25, Arrays.asList("book", "paper", "nature", "quiet")));
                quests.add(new Quest("q61", "Pose of Stillness", "Hold tree pose or other yoga stance for 1 minute. Show the balance.", "Fitness", false, 30, Arrays.asList("yoga", "balance", "pose", "stand")));
                quests.add(new Quest("q62", "Gratitude Letter", "Write a letter of thanks to someone, real or imaginary. Capture the note.", "Mind", false, 25, Arrays.asList("letter", "gratitude", "pen", "paper")));
                quests.add(new Quest("q63", "Creative Nourishment", "Prepare a colorful salad or bowl. Capture your creation from above.", "Fitness", false, 30, Arrays.asList("salad", "bowl", "vegetables", "color")));
                quests.add(new Quest("q64", "Listen and Reflect", "Listen to calming instrumental music. Show your listening setup.", "Mind", false, 20, Arrays.asList("headphones", "music", "speaker", "calm")));
                break;

// Level 9 – Refinement
            case 9:
                quests.add(new Quest("q65", "Nature Endurance", "Take a 30-minute walk or jog in a forest or natural setting. Capture your path.", "Fitness", false, 40, Arrays.asList("forest", "path", "trees", "trail")));
                quests.add(new Quest("q66", "Intentions on Paper", "Write your intentions for the next week. Photograph the boldest one.", "Mind", false, 30, Arrays.asList("intentions", "write", "focus", "pen")));
                quests.add(new Quest("q67", "Strength in Silence", "Perform 3 sets of planks and squats in silence. Show your setup or timer.", "Fitness", false, 35, Arrays.asList("plank", "squat", "timer", "core")));
                quests.add(new Quest("q68", "Echoes of Self", "Write a message to your younger self. Capture the emotional message.", "Mind", false, 30, Arrays.asList("message", "letter", "reflection", "note")));
                quests.add(new Quest("q69", "Rise and Burn", "Climb stairs or incline repeatedly for 10 minutes. Photograph the rise.", "Fitness", false, 35, Arrays.asList("stairs", "climb", "incline", "movement")));
                quests.add(new Quest("q70", "Favorite Stillness", "Go to your favorite peaceful location and photograph it.", "Mind", false, 25, Arrays.asList("peaceful", "quiet", "place", "landscape")));
                quests.add(new Quest("q71", "Posture Reset", "Practice seated posture alignment. Show a profile view or mirror reflection.", "Fitness", false, 25, Arrays.asList("pose", "posture", "mirror", "person")));
                quests.add(new Quest("q72", "Closing Breath", "Breathe deeply for 5 minutes before bed. Capture your setup.", "Mind", false, 25, Arrays.asList("lamp", "bed", "calm", "night")));
                break;

// Level 10 – Completion
            case 10:
                quests.add(new Quest("q73", "Long Road", "Complete a 45-minute walk or movement session. Capture your final view.", "Fitness", false, 50, Arrays.asList("walk", "path", "outdoor", "trail")));
                quests.add(new Quest("q74", "Wisdom Journal", "Write what you've learned across your quest journey. Capture the closing entry.", "Mind", false, 40, Arrays.asList("journal", "pen", "reflection", "page")));
                quests.add(new Quest("q75", "Final Circuit", "Perform 3 rounds of: 10 push-ups, 15 squats, 30s plank. Show timer or rep count.", "Fitness", false, 50, Arrays.asList("circuit", "pushup", "plank", "squat")));
                quests.add(new Quest("q76", "Silent Summit", "Sit in silence for 15 minutes. Photograph the space that held your stillness.", "Mind", false, 30, Arrays.asList("quiet", "room", "light", "space")));
                quests.add(new Quest("q77", "Trail Reflection", "Take a nature walk while thinking about your progress. Capture the path behind you.", "Mind", false, 30, Arrays.asList("trail", "nature", "trees", "path")));
                quests.add(new Quest("q78", "Strength Held", "Hold a one-minute plank. Capture your timer and setup.", "Fitness", false, 40, Arrays.asList("plank", "pose", "timer", "core")));
                quests.add(new Quest("q79", "Gratitude in Ink", "Write down 5 things you are grateful for today. Photograph your list.", "Mind", false, 35, Arrays.asList("gratitude", "pen", "list", "paper")));
                quests.add(new Quest("q80", "Closing Frame", "Take a photo that represents your journey’s end — a place, an object, or yourself.", "Mind", false, 50, Arrays.asList("person", "place", "symbol", "reflection")));
                break;


            default:
                quests.add(new Quest("q0", "Default Quest", "This is a fallback quest.", "Mind", false, 5, Arrays.asList("default")));
                break;
        }

        return quests;
    }
}
