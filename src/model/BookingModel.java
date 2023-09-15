package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


import java.util.HashMap;
import java.util.Vector;

/**
 * The main model of the room booking system.
 */
public class BookingModel {
    private HashMap<String, Building> buildings;
    private HashMap<String, Person> people;
    private HashMap<String, Booking> bookings;

    private PropertyChangeSupport notifier;

    /**
     * constructor for main model.
     */
    public BookingModel() {
        this.buildings = new HashMap<>();
        this.people = new HashMap<>();
        this.bookings = new HashMap<>();
        this.notifier = new PropertyChangeSupport(this);
    }

    /**
     * add a listener to the model.
     * @param listener a listener for model
     */
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * remove a listener for the model.
     * @param listener a listener for model
     */
    public void removeListener(PropertyChangeListener listener){
        notifier.removePropertyChangeListener(listener);
    }

    /**
     * get all listeners.
     * @return all listeners
     */
    public PropertyChangeListener[] getListener() {
        return notifier.getPropertyChangeListeners();
    }

    private void updateBuilding() {
        notifier.firePropertyChange("buildings", null, buildings);
    }

    /**
     * add a building to the model.
     * @param building the building
     */
    public void addBuilding(Building building){
        String buildingName = building.getName();
        buildings.put(buildingName, building);

        updateBuilding();
    }

    /**
     * remove the building.
     * @param buildingName the building name
     */
    public void removeBuilding(String buildingName) {
        buildings.remove(buildingName);

        updateBuilding();
    }

    private void updateRoom(String buildingName) {
        notifier.firePropertyChange("rooms", null, buildings.get(buildingName));
    }

    /**
     * add a room to the model.
     * @param buildingName the building name
     * @param room the room
     */
    public void addRoom(String buildingName, Room room) {
        Building building = buildings.get(buildingName);
        building.addRoom(room);

        updateRoom(buildingName);
    }

    /**
     * remove a room.
     * @param buildingName the building name
     * @param roomName the room name
     */
    public void removeRoom(String buildingName, String roomName) {
        HashMap<String, Room> rooms = buildings.get(buildingName).getRooms();
        rooms.remove(roomName);

        updateRoom(buildingName);
    }

    private void updatePeople() {
        notifier.firePropertyChange("people", null, people);
    }

    /**
     * add a user in the model.
     * @param person the person
     */
    public void addPerson(Person person){
        String email = person.getEmail();
        people.put(email, person);

        updatePeople();
    }

    /**
     * remove a person for the model.
     * @param email the email of user
     */
    public void removePerson(String email) {
        people.remove(email);

        updatePeople();
    }

    private void updateBooking() {
        notifier.firePropertyChange("bookings", null, bookings);
    }

    /**
     * Add a booking in the model.
     * @param booking the booking
     */
    public void addBooking(Booking booking) {
        String primaryKey = booking.getPrimaryKey();
        bookings.put(primaryKey, booking);

        Person person = booking.getPerson();
        person.addBooking(booking);

        Room room = booking.getRoom();
        room.addBooking(booking);

        updateBooking();
    }

    /**
     * Remove a booking through primaryKey.
     * @param primaryKey the primary key of booking
     */
    public void removeBooking(String primaryKey) {
        bookings.remove(primaryKey);

        updateBooking();
    }

    /**
     * Set the current buildings.
     * @param buildings the current buildings
     */
    public void setBuildings(HashMap<String, Building> buildings) {
        this.buildings = buildings;

        updateBuilding();
    }

    /**
     * Set the current users.
     * @param people the current users
     */
    public void setPeople(HashMap<String, Person> people) {
        this.people = people;
        updatePeople();
    }

    /**
     * Set the current bookings.
     * @param bookings the current bookings
     */
    public void setBookings(HashMap<String, Booking> bookings) {
        this.bookings = bookings;
        updateBooking();
    }

    /**
     * Get all the users in the model.
     * @return all the users
     */
    public HashMap<String, Person> getPeople() {
        return people;
    }

    /**
     * Get the person through email.
     * @param email the email of the person
     * @return the person
     */
    public Person getPerson(String email) {
        return people.get(email);
    }

    /**
     * Get all the bookings.
     * @return all the bookings
     */
    public HashMap<String, Booking> getBookings() {
        return bookings;
    }

    /**
     * Get the booking through primary key.
     * @param primaryKey the primary key
     * @return the booking
     */
    public Booking getBooking(String primaryKey) {
        return bookings.get(primaryKey);
    }

    /**
     * Get all the buildings.
     * @return all buildings
     */
    public HashMap<String, Building> getBuildings() {
        return buildings;
    }

    /**
     * Get all the buildings name
     * @return all the buildings name
     */
    public Vector<String> getBuildingNames() {
        Vector<String> buildingNames = new Vector<>(buildings.keySet());
        return buildingNames;
    }

    /**
     * Get the building through building name.
     * @param buildingName building name
     * @return the building
     */
    public Building getBuilding(String buildingName) {
        return buildings.get(buildingName);
    }

    /**
     * Get all rooms in the building.
     * @param buildingName building name
     * @return all rooms of building
     */
    public HashMap<String, Room> getRooms(String buildingName) {
        return buildings.get(buildingName).getRooms();
    }

    /**
     * Get the room through the building name and room name.
     * @param buildingName building name
     * @param roomName room name
     * @return the room
     */
    public Room getRoom(String buildingName, String roomName) {
        return getRooms(buildingName).get(roomName);
    }
}
