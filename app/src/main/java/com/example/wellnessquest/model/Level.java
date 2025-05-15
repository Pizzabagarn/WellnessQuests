package com.example.wellnessquest.model;

public class Level {

    public int levelNumber;
    public int cost;

    public Level (int levelNumber, int cost){
        this.levelNumber = levelNumber;
        this.cost = cost;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getCost() {
        return cost;
    }
}
