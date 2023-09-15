package tests.bookingmodel_test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingModelTest {

    BookingModel model;

    Person p0;
    Person p1;
    Building bu0;
    Building bu1;
    Room r0;
    Room r1;
    Booking bo0;
    Booking bo1;

    LocalDate date = LocalDate.of(2022,12,30);
    LocalTime[] times0 = new LocalTime[] {LocalTime.of(8,0), LocalTime.of(11,59,59)};
    LocalTime[] times1 = new LocalTime[] {LocalTime.of(14,0), LocalTime.of(16,59,59)};
    LocalTime time0 = LocalTime.of(9,0);
    LocalTime time1 = LocalTime.of(7,0);

    @BeforeEach
    public void setup() {
        model = new BookingModel();
        p0 = new Person("123@gmail.com", "Jack");
        p1 = new Person("456@gmail.com", "Mike");
        bu0 = new Building("BF", "KY16 9WJ");
        bu1 = new Building("NI", "KY16 9WJ");
        r0 = new Room("Flat1");
        bu0.addRoom(r0);
        r1 = new Room("Flat2");
        bu1.addRoom(r1);
        bo0 = new Booking("0", p0, bu0, r0, date, times0);
        bo1 = new Booking("1", p1, bu0, r1, date, times1);

        model.addPerson(p0);
        model.addBuilding(bu0);
        model.addRoom(bu0.getName(), r0);
        model.addBooking(bo0);
    }

    @Test
    public void testAddBuilding() {
        model.addBuilding(bu1);
        assertNotNull(model.getBuilding(bu1.getName()));
    }

    @Test
    public void testRemoveBuilding() {
        model.removeBuilding(bu0.getName());
        assertTrue(model.getBuildings().isEmpty());
    }

    @Test
    public void testAddRoom() {
        model.addRoom(bu0.getName(), r1);
        assertNotNull(model.getRoom(bu0.getName(),r1.getName()));
    }

    @Test
    public void testRemoveRoom() {
        model.removeRoom(bu0.getName(), r0.getName());
        assertNull(model.getRoom(bu0.getName(),r0.getName()));
    }

    @Test
    public void testAddPerson() {
        model.addPerson(p1);
        assertNotNull(model.getPerson(p1.getEmail()));
    }

    @Test
    public void testRemovePerson() {
        model.removePerson(p0.getEmail());
        assertNull(model.getPerson(p0.getEmail()));
    }

    @Test
    public void testAddBooking() {
        model.addBooking(bo1);
        assertNotNull(model.getBooking(bo1.getPrimaryKey()));
    }

    @Test
    public void testRemoveBooking() {
        model.removeBooking(bo0.getPrimaryKey());
        assertNull(model.getBooking(bo0.getPrimaryKey()));
    }


}
