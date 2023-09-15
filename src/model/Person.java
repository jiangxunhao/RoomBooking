package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    private String email;
    private String name;
    private ArrayList<Booking> bookings;

    public Person(String email, String name) {
        this.email = email;
        this.name = name;
        this.bookings = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

}
