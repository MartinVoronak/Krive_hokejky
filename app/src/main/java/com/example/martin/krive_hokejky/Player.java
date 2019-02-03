package com.example.martin.krive_hokejky;

public class Player {

    //TODO goals and assissts
    String firstName = "";
    String surname = "";
    int rating = 0;

    //BACKENDLESS - Class must contain default, public, no-argument constructor.
    public Player() {
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
