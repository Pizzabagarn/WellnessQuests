package com.example.wellnessquest.model;

public class Profile {
     private String name;
     private int age;
     private String purpose;

        // Constructor, getters, and setters

    public Profile(String name, int age, String purpose) {
        this.name = name;
        this.age = age;
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
