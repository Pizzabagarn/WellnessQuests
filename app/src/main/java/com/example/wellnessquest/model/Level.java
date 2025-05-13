package com.example.wellnessquest.model;

public class Level {
    private int levelNumber;
    private int cost;

    public Level(int levelNumber, int cost) {
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
