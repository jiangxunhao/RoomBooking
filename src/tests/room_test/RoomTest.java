package tests.room_test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class RoomTest {
    Person p0;
    Building building;
    Room room;
    Booking booking0;
    Booking booking1;

    LocalDate date = LocalDate.of(2022,12,30);
    LocalTime[] times0 = new LocalTime[] {LocalTime.of(8,0), LocalTime.of(11,59,59)};
    LocalTime[] times1 = new LocalTime[] {LocalTime.of(14,0), LocalTime.of(16,59,59)};
    LocalTime time0 = LocalTime.of(9,0);
    LocalTime time1 = LocalTime.of(7,0);

    @BeforeEach
    public void setupAll() {
        p0 = new Person("jiang@gmail.com", "jiang");
        building = new Building("Balfour", "KY16 9WJ");
        room = new Room("Flat1");
        building.addRoom(room);
        booking0 = new Booking("1",p0,building,room,date, times0);
        booking1 = new Booking("2",p0,building,room,date, times1);
        room.addBooking(booking0);
    }

    @Test
    public void testExists() {
        assertNotNull(room);
    }

    @Test
    public void testAddBooking() {
        room.addBooking(booking1);
        assertEquals(booking1, room.getBookings().get(1));
    }

    @Test
    public void testGetOccupiedOfDay() {
        assertEquals(times0, room.getOccupiedOfDay(date).get(0));
    }

    @Test
    public void testGetBookings() {
        assertEquals(booking0, room.getBookings().get(0));
    }

    @Test
    public void testAvailableOfDay() {
        LocalTime[] free0 = {LocalTime.of(0,0,0), LocalTime.of(7,59,59)};
        LocalTime[] free1 = {LocalTime.of(12,0,0), LocalTime.of(23,59,59)};
        assertTrue( room.getAvailableOfDay(date).get(0)[0].equals(free0[0]) );
        assertTrue( room.getAvailableOfDay(date).get(0)[1].equals(free0[1]) );
        assertTrue( room.getAvailableOfDay(date).get(1)[0].equals(free1[0]) );
        assertTrue( room.getAvailableOfDay(date).get(1)[1].equals(free1[1]) );
    }

    @Test
    public void testIsAvailable() {
        assertFalse(room.isAvailable(date,time0));
        assertTrue(room.isAvailable(date,time1));
        assertFalse(room.isAvailable(date,time1,time0));
    }



}
