package model;

/* This class represents a flight. A flight is made of an aircraft, an origin, a destination,
    the duration of flight, and the list of passengers registered on this flight. */

import org.json.JSONObject;
import persistence.Event;
import persistence.EventLog;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;

public class Flight implements Writable {
    private final String flightID;
    private final Aircraft aircraft;
    private Airports origin;
    private Airports destination;
    private int duration;
    private final ArrayList<Passenger> passengersOnFlight;

    public Flight(String flightID, Aircraft aircraft, Airports origin, Airports destination, int duration) {
        this.flightID = flightID;
        this.aircraft = aircraft;
        this.origin = origin;
        this.destination = destination;
        this.duration = duration;
        this.passengersOnFlight = new ArrayList<>();
    }

    public String getFlightID() {
        return flightID;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Airports getOrigin() {
        return origin;
    }

    public Airports getDestination() {
        return destination;
    }

    public int getDuration() {
        return duration;
    }

    // EFFECTS: returns the list of passengers on this flight
    public ArrayList<Passenger> getPassengersOnFlight() {
        return passengersOnFlight;
    }

    // EFFECTS: add a passenger to flight
    public String addPassenger(Passenger passenger) {
        if (getCurrentCapacity() < aircraft.getMaxCapacity()) {
            EventLog.getInstance().logEvent(new Event("Added passenger (on flight): " + passenger.getFirstName()));
            passengersOnFlight.add(passenger);
            passenger.getBookedFlights().add(this);
            return passenger.getFirstName()
                    + " " + passenger.getLastName()
                    + " has been added to this flight";
        } else {
            return "The aircraft for this flight is at maximum capacity.";
        }
    }

    // EFFECTS: remove a passenger from flight
    public String removePassenger(int passengerID) {
        Iterator<Passenger> p = passengersOnFlight.iterator();
        while (p.hasNext()) {
            Passenger passenger = p.next();
            if (passenger.getPassengerID() == passengerID) {
                EventLog.getInstance().logEvent(new Event("Removed passenger: " + passenger.getFirstName()));
                p.remove();
                return passenger.getFirstName()
                        + " "
                        + passenger.getLastName()
                        + " has been removed from this flight";
            }
        }
        return "Passenger " + passengerID + " is not found on this flight";

    }

    // EFFECTS: checks if a passenger is registered on the aircraft
    public boolean isPassengerOnFlight(int passengerID) {
        for (Passenger passenger : passengersOnFlight) {
            if (passenger.getPassengerID() == passengerID) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the size of passenger on the aircraft
    public int getCurrentCapacity() {
        return passengersOnFlight.size();
    }

    // EFFECTS: returns the number of seats available on the aircraft
    public int getAvailableSeats() {
        return aircraft.getMaxCapacity() - passengersOnFlight.size();
    }

    // EFFECTS: sets the origin to given origin
    public void setOrigin(Airports origin) {
        this.origin = origin;
    }

    // EFFECTS: sets destination to given destination
    public void setDestination(Airports destination) {
        this.destination = destination;
        this.duration += 2; // or chgDuration(); (calculate this, would be a cool feature)
    }

    // EFFECTS: return the flight string
    @Override
    public String toString() {
        return "Flight ID: "
                + flightID
                + " Aircraft: "
                + aircraft
                + " Origin: "
                + origin
                + " Destination: "
                + destination
                + " Duration: "
                + duration;
    }

    // EFFECTS: returns the json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("flightID", this.flightID);
        json.put("aircraft", this.aircraft.toJson());
        json.put("origin", this.origin);
        json.put("destination", this.destination);
        json.put("duration", this.duration);
        json.put("passengersOnFlight", this.passengersOnFlight);
        return json;
    }
}
