package tests.person_test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class PersonTest {

    Person p0;
    Building building;
    Room room;
    Booking booking;
    LocalDate date = LocalDate.of(2022,12,31);
    LocalTime[] times = new LocalTime[] {LocalTime.of(10,0), LocalTime.of(12,0)};

    @BeforeEach
    public void setupAll() {
        p0 = new Person("jiang@gmail.com", "jiang");
        building = new Building("Balfour", "KY16 9WJ");
        room = new Room("Flat1");
        booking = new Booking("1",p0,building,room,date, times);
    }

    @Test
    public void testPersonExists() {
        assertNotNull(p0);
    }

    @Test
    public void testGetEmail() {
        assertEquals("jiang@gmail.com",p0.getEmail());
    }

    @Test
    public void testGetName() {
        assertEquals("jiang", p0.getName());
    }

    @Test
    public void testAddBooking() {
        p0.addBooking(booking);
        assertNotNull(p0.getBookings());
    }
}
