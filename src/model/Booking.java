package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Booking implements Serializable {
    private String primaryKey;
    private Person person;
    private Building building;
    private Room room;
    private LocalDate date;
    private LocalTime[] times;

    public Booking(String primaryKey, Person person, Building building, Room room, LocalDate date, LocalTime[] times) {
        this.primaryKey = primaryKey;
        this.person = person;
        this.building = building;
        this.room = room;
        this.date = date;
        this.times = times;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimes(LocalTime[] times) {
        this.times = times;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Person getPerson() {
        return person;
    }

    public String getEmail() {
        return person.getEmail();
    }

    public Building getBuilding() {
        return building;
    }

    public String getBuildingName() {
        return building.getName();
    }

    public Room getRoom() {
        return room;
    }

    public String getRoomName() {
        return room.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime[] getTimes() {
        return times;
    }

    public LocalTime getStartTime() {
        return times[0];
    }

    public LocalTime getEndTime() {
        return times[1];
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

}
