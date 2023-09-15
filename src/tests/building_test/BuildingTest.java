package tests.building_test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    Building b0;
    Building b1;
    Room r0;
    Room r1;


    @BeforeEach
    public void setupAll() {
        b0 = new Building("Balfour", "KY16 9WJ");
        b1 = new Building("NI", "KY16 9WJ");
        r0 = new Room("Flat1");
        r1 = new Room("Flat2");
    }

    @Test
    public void testExists() {
        assertNotNull(b0);
        assertNotNull(b1);
    }

    @Test
    public void testAddRoom() {
        b0.addRoom(r0);
        assertEquals(r0, b0.getRoom("Flat1"));
    }

}
