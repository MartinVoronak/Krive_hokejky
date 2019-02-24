package com.example.martin.krive_hokejky.DataObjects;

import java.io.Serializable;

public class Player implements Serializable{

    public String objectId;
    String firstName = "";
    String surname = "";
    int rating = 0;
    int goals = 0;
    int assissts = 0;

    //BACKENDLESS - Class must contain default, public, no-argument constructor.
    public Player() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssissts() {
        return assissts;
    }

    public void setAssissts(int assissts) {
        this.assissts = assissts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
