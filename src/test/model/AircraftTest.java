package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AircraftTest {
    private Aircraft testAircraft1;
    private Aircraft testAircraft2;

    @BeforeEach
    void runBefore() {
        testAircraft1 = new CargoAircraft("ABC",150);
        testAircraft2 = new PassengerAirline("CDE",200);
    }

    @Test
    void testConstructor() {
        assertEquals("ABC", testAircraft1.getName());
        assertEquals(150, testAircraft1.getMaxCapacity());
    }

    @Test
    void getName() {
        assertEquals("ABC", testAircraft1.getName());
    }

    @Test
    void getMaxCapacityTest() {
        assertEquals(150,testAircraft1.getMaxCapacity());
    }

    @Test
    void toStringTest() {
        assertEquals("Name of Aircraft: ABC Maximum Capacity: 150",testAircraft1.toString());
    }



}