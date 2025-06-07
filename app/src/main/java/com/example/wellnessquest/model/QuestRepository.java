package com.example.wellnessquest.model;

import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.Quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for generating levels and associated quests
 * for the WellnessQuest application. Each level includes a set of quests
 * and a coin cost required to unlock that level.
 *
 * Author: Alexander
 */

public class QuestRepository {

    /**
     * Returns a Level object with its corresponding quests and cost.
     *
     * @param levelNumber the level number (1-10)
     * @return Level object
     */
    public static Level getLevel(int levelNumber) {
        List<Quest> quests = generateQuestsForLevel(levelNumber);
        int cost = calculateLevelCost(levelNumber);
        return new Level(levelNumber, cost, quests);
    }


    /**
     * Returns a list of all levels from 1 to 10.
     *
     * @return list of all Level objects
     */
    public static List<Level> getAllLevels() {
        List<Level> levels = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            levels.add(getLevel(i));
        }
        return levels;
    }

    /**
     * Calculates the unlock cost of a level based on its number.
     * The cost increases with each subsequent level.
     *
     * @param levelNumber the level number
     * @return the cost in coins
     */
    private static int calculateLevelCost(int levelNumber) {

        return 60 + (levelNumber - 1) * 20;
    }

    /**
     * Generates a list of quests associated with a given level.
     * Includes quest descriptions, categories, rewards, and image tags.
     *
     * @param level the level number (1-10)
     * @return list of Quest objects
     */
    private static List<Quest> generateQuestsForLevel(int level) {
        List<Quest> quests = new ArrayList<>();

        switch (level) {
//Level 1 - Foundation
            case 1:
                quests.add(new Quest("q1", "Water of Clarity",
                        "Drink a full glass of water and capture it in your hand.",
                        "Fitness", false, 10, Arrays.asList(
                        "glass", "bottle", "cup", "hand", "water", "drink", "transparent",
                        "liquid", "kitchen", "table", "hydration"
                )));

                quests.add(new Quest("q2", "Arms Toward the Sky",
                        "Stretch both arms upward and hold.",
                        "Fitness", false, 15, Arrays.asList(
                        "arms", "stretch", "standing", "pose", "exercise", "sky", "outside",
                        "fitness", "person", "torso", "legs", "shoulder", "balance", "motion"
                )));

                quests.add(new Quest("q3", "The First Step",
                        "Step outside and capture your shoes on the ground.",
                        "Fitness", false, 5, Arrays.asList(
                        "shoes", "feet", "outdoor", "sidewalk", "asphalt", "legs", "concrete",
                        "walk", "pants", "shadow", "street", "step"
                )));

                quests.add(new Quest("q4", "Reflection in Stillness",
                        "Stand before a mirror and smile with peace.",
                        "Mind", false, 10, Arrays.asList(
                        "mirror", "reflection", "face", "smile", "person", "bathroom",
                        "room", "lighting", "eyes", "hair", "selfie", "shoulders", "hand"
                )));

                quests.add(new Quest("q5", "Roots in the Earth",
                        "Sit calmly near nature. Photograph a tree, grass, or sky.",
                        "Mind", false, 10, Arrays.asList(
                        "tree", "grass", "sky", "outdoor", "leaves", "nature", "sunlight",
                        "park", "cloud", "plant", "ground", "relax", "green"
                )));

                quests.add(new Quest("q6", "The Word Within",
                        "Write a word that captures how you feel.",
                        "Mind", false, 10, Arrays.asList(
                        "paper", "pen", "notebook", "writing", "handwriting", "table",
                        "desk", "hand", "words", "text", "journal", "stationery"
                )));

                quests.add(new Quest("q7", "Skyward Vision",
                        "Admire the sky and capture the clouds or clear blue.",
                        "Mind", false, 15, Arrays.asList(
                        "sky", "cloud", "blue", "sun", "light", "open", "air", "outdoor",
                        "skyline", "weather", "sunlight", "atmosphere"
                )));

                quests.add(new Quest("q8", "Motion of Awakening",
                        "Do 10 jumping jacks. Capture your stance.",
                        "Fitness", false, 5, Arrays.asList(
                        "jump", "person", "movement", "arms", "legs", "pose", "exercise",
                        "muscle", "fitness", "room", "motion", "athlete", "sports",
                        "balance", "stretch", "action", "energy"
                )));
                break;
// Level 2 – Awakening
            case 2:
                quests.add(new Quest("q9", "Steps of Progress", "Take 500 steps today and show your journey with a picture of your walking path.", "Fitness", false, 15, Arrays.asList(
                        "path", "street", "shoes", "walk", "legs", "sidewalk", "feet", "motion", "outdoor", "concrete", "exercise", "asphalt", "urban", "journey", "activity", "sneakers", "ground", "road", "route", "pavement", "lane", "gravel"
                )));
                quests.add(new Quest("q10", "Grateful Marks", "Write two things you're thankful for and photograph your written note.", "Mind", false, 10, Arrays.asList(
                        "note", "journal", "pen", "gratitude", "paper", "handwriting", "table", "writing", "words", "stationery", "ink", "thankful", "desk", "message", "notepad", "letter", "document", "sheet", "paperwork", "memo"
                )));
                quests.add(new Quest("q11", "The Seated Breath", "Sit calmly and breathe deeply for 3 minutes. Let your posture be captured.", "Mind", false, 10, Arrays.asList(
                        "sitting", "chair", "person", "calm", "relax", "pose", "breathing", "room", "legs", "posture", "meditation", "peace", "stillness", "floor", "sofa", "rest", "yoga", "cushion", "quiet", "interior"
                )));
                quests.add(new Quest("q12", "Lower Ground", "Complete 10 squats. Capture your mid-squat form or your preparation.", "Fitness", false, 15, Arrays.asList(
                        "squat", "fitness", "legs", "pose", "exercise", "motion", "strength", "muscle", "person", "workout", "training", "gym", "balance", "athlete", "bodyweight", "action", "stance", "lower body", "movement"
                )));
                quests.add(new Quest("q13", "Colorful Fuel", "Prepare a healthy snack. Photograph the colors on your plate.", "Fitness", false, 15, Arrays.asList(
                        "food", "snack", "plate", "fruit", "vegetables", "colorful", "meal", "bowl", "kitchen", "nutrition", "dish", "healthy", "lunch", "table", "cuisine", "greens", "serving", "dining", "recipe", "fresh"
                )));
                quests.add(new Quest("q14", "Eyes of Curiosity", "Observe something natural: a bird, a leaf, or a cloud. Photograph what you see.", "Mind", false, 10, Arrays.asList(
                        "bird", "leaf", "tree", "sky", "nature", "outdoor", "cloud", "feathers", "green", "branch", "plant", "blue", "daylight", "environment", "landscape", "flying", "sunlight", "air", "flora", "canopy"
                )));
                quests.add(new Quest("q15", "Smile Shared", "Smile with someone and capture your faces together, genuine and kind.", "Mind", false, 10, Arrays.asList(
                        "smile", "people", "face", "together", "friends", "happy", "expression", "teeth", "eyes", "group", "selfie", "portrait", "emotion", "joy", "laughing", "crowd", "mouth", "close-up", "pose", "connection"
                )));
                quests.add(new Quest("q16", "Written Intention", "Write one goal for the day and show your handwritten words.", "Mind", false, 10, Arrays.asList(
                        "paper", "goal", "pen", "note", "writing", "desk", "hand", "planner", "words", "notebook", "ink", "focus", "stationery", "letter", "message", "schedule", "journal", "diary", "to-do", "planning"
                )));
                break;

// Level 3 – Grounding
            case 3:
                quests.add(new Quest("q17", "Path of Green", "Walk in a park or near trees. Photograph nature surrounding your path.", "Fitness", false, 15, Arrays.asList(
                        "tree", "grass", "path", "park", "nature", "outdoor", "green", "trail", "leaves", "walk", "sunlight", "landscape", "plants", "bush", "environment", "daylight", "shadows", "soil", "route", "flora"
                )));
                quests.add(new Quest("q18", "Balanced Frame", "Hold a yoga pose for 30 seconds and capture your form and stability.", "Fitness", false, 15, Arrays.asList(
                        "yoga", "pose", "balance", "person", "exercise", "stretch", "calm", "legs", "arms", "standing", "core", "fitness", "movement", "training", "meditation", "mat", "gym", "outdoor", "body", "breath"
                )));
                quests.add(new Quest("q19", "Energy in a Glass", "Make a smoothie from fruit or greens and photograph the drink.", "Fitness", false, 15, Arrays.asList(
                        "smoothie", "fruit", "glass", "drink", "healthy", "colorful", "cup", "juice", "berries", "liquid", "refreshment", "kitchen", "beverage", "nutrition", "straw", "blender", "vibrant", "fresh", "breakfast", "refresh"
                )));
                quests.add(new Quest("q20", "Memory Scroll", "Write a memory from this week. Photograph your journal page.", "Mind", false, 10, Arrays.asList(
                        "journal", "notebook", "memory", "pen", "paper", "writing", "desk", "words", "note", "reflection", "stationery", "letter", "ink", "book", "diary", "thoughts", "memoir", "page", "script", "lines"
                )));
                quests.add(new Quest("q21", "Silence Within", "Meditate for 5 minutes and capture your seated stillness.", "Mind", false, 15, Arrays.asList(
                        "meditation", "calm", "pose", "eyes closed", "person", "sitting", "floor", "peace", "breath", "relaxation", "silence", "room", "yoga", "stillness", "focus", "quiet", "mindfulness", "tranquil", "body", "interior"
                )));
                quests.add(new Quest("q22", "Strength in Lunge", "Do 15 lunges. Capture one mid-lunge in side view.", "Fitness", false, 20, Arrays.asList(
                        "lunge", "leg", "exercise", "balance", "fitness", "training", "pose", "movement", "strength", "gym", "stretch", "muscle", "outdoor", "core", "athlete", "body", "active", "workout", "action", "stance"
                )));
                quests.add(new Quest("q23", "Fading Light", "Watch the sunset and photograph the horizon as day ends.", "Mind", false, 10, Arrays.asList(
                        "sunset", "sky", "orange", "light", "horizon", "nature", "outdoor", "sun", "clouds", "evening", "color", "twilight", "scenery", "landscape", "sunlight", "glow", "dusk", "vibrant", "peaceful", "skyline"
                )));
                quests.add(new Quest("q24", "Plated Harmony", "Prepare a healthy meal. Photograph the complete plate before eating.", "Fitness", false, 20, Arrays.asList(
                        "meal", "vegetables", "plate", "food", "healthy", "colorful", "cuisine", "dish", "nutrition", "kitchen", "table", "greens", "lunch", "dinner", "bowl", "serving", "presentation", "diet", "fresh", "homecooked"
                )));
                break;


// Level 4 – Strength and Stillness
            case 4:
                quests.add(new Quest("q25", "The Long Path", "Walk 2,000 steps with focus. Capture your final destination as a sign of persistence.", "Fitness", false, 20, Arrays.asList( "walk", "shoes", "distance", "path", "street", "route", "legs", "sidewalk", "feet", "trail", "urban", "outdoor", "journey", "motion", "activity", "asphalt", "exercise", "sneakers", "pavement", "landscape")));
                quests.add(new Quest("q26", "Rooted Push", "Do 20 push-ups. Capture your position from a side angle to show grounded strength.", "Fitness", false, 20, Arrays.asList(  "pushup", "arms", "pose", "exercise", "strength", "fitness", "chest", "training", "muscle", "plank", "core", "upper body", "floor", "person", "gym", "balance", "motion", "action", "workout", "bodyweight")));
                quests.add(new Quest("q27", "Challenge Reflected", "Write about a challenge you faced this week. Photograph your honest words.", "Mind", false, 15, Arrays.asList("journal", "writing", "note", "pen", "paper", "letter", "reflection", "desk", "handwriting", "memo", "notebook", "thoughts", "script", "stationery", "page", "document", "ink", "words", "paragraph", "diary")));
                quests.add(new Quest("q28", "Forest Glimpse", "Enter a green space and photograph the natural elements around you.", "Mind", false, 15, Arrays.asList("tree", "leaf", "outdoor", "nature", "green", "branch", "sky", "canopy", "forest", "plants", "environment", "sunlight", "bush", "flora", "landscape", "light", "daylight", "shadows", "natural", "scene")));
                quests.add(new Quest("q29", "Brew of Calm", "Make yourself herbal tea or warm lemon water. Capture the steam rising from your cup.", "Mind", false, 15, Arrays.asList("tea", "cup", "steam", "drink", "hot", "beverage", "mug", "table", "relax", "calm", "kitchen", "warm", "liquid", "herbal", "ceramic", "saucer", "teabag", "vapor", "comfort", "drinkware")));
                quests.add(new Quest("q30", "Steady as Stone", "Hold a plank pose for 30 seconds. Photograph the moment or aftermath.", "Fitness", false, 25, Arrays.asList("plank", "core", "pose", "strength", "floor", "exercise", "arms", "body", "training", "balance", "workout", "fitness", "position", "focus", "motion", "gym", "abdominal", "endurance", "bodyweight", "effort")));
                quests.add(new Quest("q31", "Open Book", "Read 5 pages of a book. Take a photo of your reading spot.", "Mind", false, 10, Arrays.asList("book", "reading", "pages", "quiet", "literature", "open", "desk", "table", "study", "words", "text", "paper", "knowledge", "learning", "lamp", "pen", "notebook", "calm", "chair", "reader")));
                quests.add(new Quest("q32", "Smile and Scribble", "Smile and hold up a note with a positive thought. Capture both.", "Mind", false, 15, Arrays.asList( "smile", "paper", "handwriting", "person", "note", "pen", "face", "happy", "expression", "letter", "writing", "document", "selfie", "portrait", "desk", "journal", "message", "words", "greeting", "drawing")));
                break;
// Level 5 – Expansion
            case 5:
                quests.add(new Quest("q33", "Ten-Minute Run", "Run or jog for 10 minutes. Capture your shoes or path after finishing.", "Fitness", false, 25, Arrays.asList(
                        "run", "shoes", "outdoor", "sweat", "trail", "legs", "path", "sneakers", "exercise", "athlete", "motion", "asphalt", "feet", "jog", "road", "route", "race", "training", "sport", "urban"
                )));
                quests.add(new Quest("q34", "Meal of Balance", "Prepare a balanced meal with protein, vegetables, and carbs. Show the full plate.", "Fitness", false, 25, Arrays.asList(
                        "meal", "plate", "food", "vegetables", "protein", "carbs", "dish", "nutrition", "healthy", "cuisine", "lunch", "dinner", "greens", "rice", "meat", "salad", "bowl", "table", "kitchen", "colorful"
                )));
                quests.add(new Quest("q35", "The Power Chair", "Hold a wall sit for 45 seconds. Photograph your form from the side or reflection.", "Fitness", false, 25, Arrays.asList(
                        "wall", "legs", "pose", "chair", "exercise", "fitness", "strength", "training", "body", "stance", "sit", "position", "back", "core", "balance", "floor", "mirror", "interior", "focus", "muscle"
                )));
                quests.add(new Quest("q36", "Clear Reflection", "Write a paragraph on how you've grown in the last 5 days. Photograph your text.", "Mind", false, 20, Arrays.asList(
                        "reflection", "journal", "write", "pen", "paper", "paragraph", "notebook", "words", "letter", "desk", "handwriting", "memo", "document", "ink", "page", "thoughts", "growth", "diary", "message", "script"
                )));
                quests.add(new Quest("q37", "Deep Focus", "Meditate in silence or with calm sounds for 10 minutes. Capture your seated posture or view.", "Mind", false, 20, Arrays.asList(
                        "meditation", "eyes closed", "calm", "person", "sitting", "yoga", "focus", "peace", "relax", "silence", "pose", "floor", "room", "interior", "mindfulness", "breath", "stillness", "tranquil", "seated", "quiet"
                )));
                quests.add(new Quest("q38", "Balance of One", "Stand on one leg with hands overhead for 30 seconds. Photograph the pose.", "Fitness", false, 25, Arrays.asList(
                        "balance", "yoga", "pose", "standing", "exercise", "leg", "fitness", "arms", "stretch", "motion", "position", "focus", "training", "athlete", "body", "breath", "core", "concentration", "still", "form"
                )));
                quests.add(new Quest("q39", "Fruit Mosaic", "Create a colorful fruit bowl. Photograph the presentation before eating.", "Fitness", false, 20, Arrays.asList(
                        "fruit", "bowl", "color", "snack", "food", "healthy", "berries", "plate", "kitchen", "fresh", "nutrition", "sweet", "grapes", "banana", "citrus", "serving", "breakfast", "natural", "dish", "presentation"
                )));
                quests.add(new Quest("q40", "Still Frame", "Find a quiet place that brings peace. Photograph it as a symbol of rest.", "Mind", false, 20, Arrays.asList(
                        "peaceful", "quiet", "place", "landscape", "nature", "calm", "interior", "outdoor", "room", "scene", "spot", "light", "sunlight", "green", "floor", "cozy", "window", "tranquil", "view", "relax"
                )));
                break;


// Level 6 – Consolidation
            case 6:
                quests.add(new Quest("q41", "Hillside Breath", "Walk uphill or on an incline for 10 minutes. Capture the incline or horizon ahead.", "Fitness", false, 30, Arrays.asList(
                        "hill", "slope", "path", "shoes", "trail", "incline", "road", "walk", "legs", "nature", "trees", "climb", "steps", "outdoor", "elevation", "fitness", "ground", "exercise", "gravel", "ascent"
                )));
                quests.add(new Quest("q42", "Reflective Brew", "Prepare a warm drink and sit quietly reflecting for 5 minutes. Capture the drink and setting.", "Mind", false, 20, Arrays.asList(
                        "tea", "cup", "steam", "calm", "drink", "mug", "reflection", "table", "warm", "hot", "kitchen", "coffee", "ceramic", "liquid", "relax", "beverage", "cozy", "interior", "rest", "focus"
                )));
                quests.add(new Quest("q43", "Bridge of Strength", "Hold a bridge pose for 30 seconds. Capture your form from a safe angle.", "Fitness", false, 25, Arrays.asList(
                        "bridge", "pose", "exercise", "core", "fitness", "legs", "strength", "training", "stretch", "person", "floor", "back", "movement", "workout", "yoga", "glutes", "body", "form", "balance", "stability"
                )));
                quests.add(new Quest("q44", "Letters to Tomorrow", "Write a note to your future self. Photograph the note or the pen in motion.", "Mind", false, 20, Arrays.asList(
                        "paper", "pen", "writing", "letter", "note", "desk", "journal", "handwriting", "message", "words", "notebook", "memo", "ink", "document", "stationery", "script", "reflection", "planner", "table", "thoughts"
                )));
                quests.add(new Quest("q45", "Mountain Stillness", "Meditate for 12 minutes in total silence. Capture your seated space.", "Mind", false, 25, Arrays.asList(
                        "person", "pose", "eyes closed", "calm", "meditation", "sitting", "floor", "peace", "relaxation", "silence", "room", "interior", "stillness", "focus", "mindfulness", "quiet", "seated", "breath", "tranquil", "space"
                )));
                quests.add(new Quest("q46", "Fruitful Journey", "Create a meal or snack with 3+ fruits. Photograph the colorful spread.", "Fitness", false, 20, Arrays.asList(
                        "fruit", "bowl", "plate", "color", "snack", "healthy", "berries", "grapes", "apple", "banana", "orange", "citrus", "fresh", "kitchen", "nutrition", "meal", "sweet", "dish", "serving", "presentation"
                )));
                quests.add(new Quest("q47", "Flow and Form", "Practice yoga with three transitions. Capture one transition mid-motion.", "Fitness", false, 30, Arrays.asList(
                        "yoga", "pose", "motion", "exercise", "fitness", "movement", "balance", "stretch", "person", "core", "training", "action", "flow", "breath", "arms", "legs", "focus", "form", "gym", "routine"
                )));
                quests.add(new Quest("q48", "Quiet Horizon", "Watch a sunrise or early morning light. Capture the view and its colors.", "Mind", false, 20, Arrays.asList(
                        "sunrise", "sky", "light", "morning", "sun", "horizon", "orange", "blue", "nature", "outdoor", "dawn", "colors", "clouds", "landscape", "peaceful", "view", "early", "scene", "glow", "tranquil"
                )));
                break;


// Level 7 – Resilience
            case 7:
                quests.add(new Quest("q49", "Steadfast Footing", "Walk 3,000 steps and photograph your route or worn shoes.", "Fitness", false, 30, Arrays.asList(
                        "walk", "shoes", "step", "route", "trail", "legs", "sneakers", "path", "feet", "fitness", "movement", "outdoor", "exercise", "activity", "road", "ground", "gravel", "journey", "motion", "travel"
                )));
                quests.add(new Quest("q50", "Wisdom Poured", "Write a lesson you've learned this month. Capture the reflection in writing.", "Mind", false, 25, Arrays.asList(
                        "write", "lesson", "pen", "notebook", "paper", "journal", "note", "message", "desk", "words", "thoughts", "reflection", "stationery", "learning", "text", "ink", "page", "wisdom", "study", "document"
                )));
                quests.add(new Quest("q51", "Legs of Stone", "Hold a wall sit for 1 minute. Capture the effort in your posture.", "Fitness", false, 30, Arrays.asList(
                        "wall", "legs", "pose", "strength", "sit", "fitness", "core", "balance", "exercise", "training", "squat", "chair", "static", "endurance", "stance", "floor", "person", "muscle", "effort", "tension"
                )));
                quests.add(new Quest("q52", "Still Center", "Meditate for 15 minutes. Show the calm space you created.", "Mind", false, 25, Arrays.asList(
                        "meditation", "space", "still", "quiet", "room", "peaceful", "calm", "pose", "seated", "floor", "breathing", "relaxation", "mindfulness", "interior", "solitude", "corner", "focus", "tranquil", "eyes closed", "light"
                )));
                quests.add(new Quest("q53", "Create Light", "Light a candle and sit for 5 minutes. Photograph the candle's flame.", "Mind", false, 20, Arrays.asList(
                        "candle", "flame", "light", "focus", "wax", "fire", "burn", "glow", "relax", "calm", "table", "warmth", "peaceful", "room", "night", "interior", "soft", "illumination", "ceramic", "glass"
                )));
                quests.add(new Quest("q54", "Push Forward", "Complete 25 push-ups. Photograph the final rep or aftermath.", "Fitness", false, 30, Arrays.asList(
                        "pushup", "strength", "exercise", "pose", "fitness", "arms", "floor", "core", "training", "plank", "chest", "muscle", "workout", "form", "person", "effort", "routine", "hard", "body", "tension"
                )));
                quests.add(new Quest("q55", "Walking Reflection", "Take a long reflective walk and photograph something you notice anew.", "Mind", false, 20, Arrays.asList(
                        "path", "walk", "nature", "detail", "reflection", "trail", "ground", "trees", "leaves", "observation", "journey", "outdoor", "calm", "scene", "surroundings", "exploration", "noticing", "focus", "road", "earth"
                )));
                quests.add(new Quest("q56", "Fuel with Purpose", "Prepare a nutrient-rich breakfast. Capture its color and balance.", "Fitness", false, 25, Arrays.asList(
                        "breakfast", "food", "plate", "nutrition", "fruit", "meal", "healthy", "dish", "cereal", "toast", "juice", "eggs", "bowl", "morning", "balanced", "snack", "presentation", "table", "colorful", "start"
                )));
                break;


// Level 8 – Mastery
            case 8:
                quests.add(new Quest("q57", "Clarity in Sweat", "Run or bike for 15 minutes. Show your face, gear or environment after.", "Fitness", false, 35, Arrays.asList(
                        "run", "bike", "sweat", "helmet", "exercise", "fitness", "motion", "training", "outdoor", "bicycle", "path", "road", "sport", "legs", "person", "ride", "activity", "speed", "movement", "cycling"
                )));
                quests.add(new Quest("q58", "The Written Core", "Write your personal values. Capture the list as your reminder.", "Mind", false, 30, Arrays.asList(
                        "write", "values", "notebook", "pen", "paper", "text", "journal", "handwriting", "words", "desk", "note", "script", "stationery", "reflection", "document", "ink", "message", "page", "table", "letter"
                )));
                quests.add(new Quest("q59", "Strength Sequence", "Complete a set: 10 push-ups, 20 squats, 30s plank. Show yourself or a timer.", "Fitness", false, 35, Arrays.asList(
                        "exercise", "pushup", "plank", "squat", "fitness", "training", "floor", "pose", "legs", "arms", "core", "strength", "movement", "workout", "routine", "timer", "body", "form", "muscle", "gym"
                )));
                quests.add(new Quest("q60", "Disconnect to Reconnect", "Be off-screen for 20 minutes. Capture an alternative peaceful activity.", "Mind", false, 25, Arrays.asList(
                        "book", "paper", "nature", "quiet", "calm", "outdoor", "peaceful", "activity", "mindfulness", "sunlight", "writing", "relax", "focus", "trees", "leaves", "pen", "scene", "reading", "journal", "solitude"
                )));
                quests.add(new Quest("q61", "Pose of Stillness", "Hold tree pose or other yoga stance for 1 minute. Show the balance.", "Fitness", false, 30, Arrays.asList(
                        "yoga", "balance", "pose", "stand", "fitness", "stretch", "core", "body", "legs", "arms", "exercise", "movement", "focus", "breath", "training", "form", "person", "tree", "still", "motion"
                )));
                quests.add(new Quest("q62", "Gratitude Letter", "Write a letter of thanks to someone, real or imaginary. Capture the note.", "Mind", false, 25, Arrays.asList(
                        "letter", "gratitude", "pen", "paper", "note", "handwriting", "journal", "desk", "script", "reflection", "message", "write", "stationery", "document", "thank you", "ink", "words", "card", "text", "emotion"
                )));
                quests.add(new Quest("q63", "Creative Nourishment", "Prepare a colorful salad or bowl. Capture your creation from above.", "Fitness", false, 30, Arrays.asList(
                        "salad", "bowl", "vegetables", "color", "plate", "healthy", "food", "nutrition", "greens", "meal", "kitchen", "dish", "fruit", "ingredients", "presentation", "colorful", "top view", "serving", "fresh", "tomato"
                )));
                quests.add(new Quest("q64", "Listen and Reflect", "Listen to calming instrumental music. Show your listening setup.", "Mind", false, 20, Arrays.asList(
                        "headphones", "music", "speaker", "calm", "audio", "sound", "listening", "relax", "room", "setup", "table", "device", "reflection", "tech", "quiet", "peaceful", "playlist", "earphones", "electronics", "instrument"
                )));
                break;


// Level 9 – Refinement
            case 9:
                quests.add(new Quest("q65", "Nature Endurance", "Take a 30-minute walk or jog in a forest or natural setting. Capture your path.", "Fitness", false, 40, Arrays.asList(
                        "forest", "path", "trees", "trail", "nature", "walk", "jog", "outdoor", "green", "leaves", "dirt", "exercise", "legs", "fitness", "natural", "route", "environment", "landscape", "motion", "ground"
                )));
                quests.add(new Quest("q66", "Intentions on Paper", "Write your intentions for the next week. Photograph the boldest one.", "Mind", false, 30, Arrays.asList(
                        "intentions", "write", "focus", "pen", "paper", "note", "desk", "journal", "message", "handwriting", "document", "planner", "goals", "stationery", "thoughts", "notebook", "page", "ink", "script", "text"
                )));
                quests.add(new Quest("q67", "Strength in Silence", "Perform 3 sets of planks and squats in silence. Show your setup or timer.", "Fitness", false, 35, Arrays.asList(
                        "plank", "squat", "timer", "core", "exercise", "floor", "pose", "training", "fitness", "strength", "legs", "arms", "routine", "form", "workout", "body", "motion", "stillness", "muscle", "balance"
                )));
                quests.add(new Quest("q68", "Echoes of Self", "Write a message to your younger self. Capture the emotional message.", "Mind", false, 30, Arrays.asList(
                        "message", "letter", "reflection", "note", "pen", "paper", "journal", "write", "emotion", "script", "handwriting", "text", "words", "stationery", "ink", "desk", "memo", "feelings", "page", "meaning"
                )));
                quests.add(new Quest("q69", "Rise and Burn", "Climb stairs or incline repeatedly for 10 minutes. Photograph the rise.", "Fitness", false, 35, Arrays.asList(
                        "stairs", "climb", "incline", "movement", "legs", "exercise", "steps", "up", "path", "training", "fitness", "action", "ascent", "cardio", "route", "motion", "effort", "walk", "balance", "stairs case"
                )));
                quests.add(new Quest("q70", "Favorite Stillness", "Go to your favorite peaceful location and photograph it.", "Mind", false, 25, Arrays.asList(
                        "peaceful", "quiet", "place", "landscape", "nature", "scene", "outdoor", "calm", "relaxation", "environment", "view", "serenity", "tranquil", "area", "spot", "retreat", "sunlight", "shade", "location", "solitude"
                )));
                quests.add(new Quest("q71", "Posture Reset", "Practice seated posture alignment. Show a profile view or mirror reflection.", "Fitness", false, 25, Arrays.asList(
                        "pose", "posture", "mirror", "person", "seated", "fitness", "alignment", "form", "exercise", "reflection", "legs", "back", "balance", "body", "yoga", "chair", "calm", "profile", "core", "muscle"
                )));
                quests.add(new Quest("q72", "Closing Breath", "Breathe deeply for 5 minutes before bed. Capture your setup.", "Mind", false, 25, Arrays.asList(
                        "lamp", "bed", "calm", "night", "room", "quiet", "space", "breathe", "setup", "relaxation", "interior", "light", "dark", "peace", "sleep", "pillow", "soft", "still", "routine", "comfort"
                )));
                break;


// Level 10 – Completion
            case 10:
                quests.add(new Quest("q73", "Long Road", "Complete a 45-minute walk or movement session. Capture your final view.", "Fitness", false, 50, Arrays.asList(
                        "walk", "path", "outdoor", "trail", "route", "exercise", "legs", "fitness", "movement", "distance", "ground", "view", "landscape", "journey", "nature", "shoes", "progress", "motion", "effort", "steps"
                )));
                quests.add(new Quest("q74", "Wisdom Journal", "Write what you've learned across your quest journey. Capture the closing entry.", "Mind", false, 40, Arrays.asList(
                        "journal", "pen", "reflection", "page", "write", "message", "lesson", "document", "handwriting", "notebook", "paper", "text", "stationery", "thoughts", "ink", "script", "record", "learning", "memo", "entry"
                )));
                quests.add(new Quest("q75", "Final Circuit", "Perform 3 rounds of: 10 push-ups, 15 squats, 30s plank. Show timer or rep count.", "Fitness", false, 50, Arrays.asList(
                        "circuit", "pushup", "plank", "squat", "exercise", "fitness", "routine", "timer", "strength", "form", "body", "training", "motion", "workout", "floor", "repetition", "action", "core", "legs", "arms"
                )));
                quests.add(new Quest("q76", "Silent Summit", "Sit in silence for 15 minutes. Photograph the space that held your stillness.", "Mind", false, 30, Arrays.asList(
                        "quiet", "room", "light", "space", "calm", "still", "interior", "seating", "relaxation", "environment", "solitude", "peaceful", "minimal", "setup", "chair", "floor", "lamp", "tranquil", "scene", "spot"
                )));
                quests.add(new Quest("q77", "Trail Reflection", "Take a nature walk while thinking about your progress. Capture the path behind you.", "Mind", false, 30, Arrays.asList(
                        "trail", "nature", "trees", "path", "reflection", "walk", "journey", "environment", "outdoor", "leaves", "ground", "steps", "scenery", "progress", "solitude", "route", "landscape", "backward", "motion", "peace"
                )));
                quests.add(new Quest("q78", "Strength Held", "Hold a one-minute plank. Capture your timer and setup.", "Fitness", false, 40, Arrays.asList(
                        "plank", "pose", "timer", "core", "strength", "exercise", "fitness", "routine", "floor", "form", "body", "training", "position", "arms", "legs", "motion", "still", "setup", "balance", "endurance"
                )));
                quests.add(new Quest("q79", "Gratitude in Ink", "Write down 5 things you are grateful for today. Photograph your list.", "Mind", false, 35, Arrays.asList(
                        "gratitude", "pen", "list", "paper", "write", "note", "journal", "handwriting", "ink", "text", "thoughts", "message", "page", "stationery", "reflection", "words", "script", "document", "memo", "thankful"
                )));
                quests.add(new Quest("q80", "Closing Frame", "Take a photo that represents your journey’s end — a place, an object, or yourself.", "Mind", false, 50, Arrays.asList(
                        "person", "place", "symbol", "reflection", "object", "landscape", "scene", "emotion", "final", "memory", "photo", "meaning", "journey", "end", "peace", "pose", "setting", "expression", "closure", "moment"
                )));
                break;


            default:
                quests.add(new Quest("q0", "The Journey Begins", "Hey! You found your way to the questlog, great!" +
                        " Begin by taking a selfie of yourself, either by using an existing photo or by taking a new one." +
                        " Once this quest is completed you will be rewarded enough coins to" +
                        " advance to level 1 where you will find more quests. To advance in levels you have to navigate to the map." +
                        "By clicking on the next node you will get asked if you want to buy the level", "Mind", false, 60, Arrays.asList("face, selfie, nose, eye, eyes, " +
                        "skin", "room", "nature", "tree", "sun", "furniture", "hair", "nails", "eyebrows", "person", "smile", "mouth", "lips", "body", "clothes", "picture", "cool", "dude", "beard", "eyelashes")));
                break;
        }

        return quests;
    }
}
