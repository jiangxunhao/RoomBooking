package tests.booking_test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingTest {

    Person p0;
    Building building;
    Room room;
    Booking booking0;

    LocalDate date = LocalDate.of(2022,12,30);
    LocalTime[] times0 = new LocalTime[] {LocalTime.of(8,0), LocalTime.of(11,59,59)};

    LocalTime time0 = LocalTime.of(8,0);
    LocalTime time1 = LocalTime.of(11,59,59);

    @BeforeEach
    public void setupAll() {
        p0 = new Person("jiang@gmail.com", "jiang");
        building = new Building("Balfour", "KY16 9WJ");
        room = new Room("Flat1");
        building.addRoom(room);
        booking0 = new Booking("1",p0,building,room,date, times0);
    }

    @Test
    public void testGetPerson() {
        assertEquals(p0,booking0.getPerson());
    }

    @Test
    public void testGetBuilding() {
        assertEquals(building,booking0.getBuilding());
    }

    @Test
    public void testGetRoom() {
        assertEquals(room,booking0.getRoom());
    }

    @Test
    public void testGetDate() {
        assertEquals(date,booking0.getDate());
    }

    @Test
    public void testGetTime() {
        assertEquals(times0,booking0.getTimes());
        assertEquals(time0,booking0.getStartTime());
        assertEquals(time1,booking0.getEndTime());
    }
}
