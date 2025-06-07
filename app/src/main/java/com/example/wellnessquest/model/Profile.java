package com.example.wellnessquest.model;

/**
 * Represents a user profile within the WellnessQuest application.
 * Stores basic personal information such as name, age, and wellness purpose.
 * @author Gen Félix Teramoto
 */
public class Profile {
     private String name;
     private int age;
     private String purpose;

    /**
     * Constructs a new Profile with the specified name, age, and purpose.
     *
     * @param name    the user's name
     * @param age     the user's age
     * @param purpose the user's purpose or goal for using the application
     */
    public Profile(String name, int age, String purpose) {
        this.name = name;
        this.age = age;
        this.purpose = purpose;
    }

    /**
     * Gets the user's name.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the name to set for the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's age.
     *
     * @return the age of the user
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the user's age.
     *
     * @param age the age to set for the user
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the user's purpose for using the application.
     *
     * @return the wellness goal or purpose of the user
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the user's purpose for using the application.
     *
     * @param purpose the purpose or goal to set for the user
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}