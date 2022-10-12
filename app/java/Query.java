package com.example.myapp;

public class Query {
    private String firstName;
    private String lastName;
    private String zoneNumber;
    private String event;
    private String description;
    private String date;
    private String email;
    private String type;

    public Query(String firstName, String lastName, String zoneNumber, String event, String description, String date, String email, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zoneNumber = zoneNumber;
        this.event = event;
        this.description = description;
        this.date = date;
        this.email = email;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZoneNumber() {
        return zoneNumber;
    }

    public void setZoneNumber(String zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "Event: " + event + "\nAttended by " + firstName + "\nResiding Zip Code: " + zoneNumber + "\n" + "Date: " + date + "\n" + description + "\n" + "Ride Request: " + type;
    }
}
