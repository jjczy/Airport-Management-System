package persistence;

import model.Aircraft;
import model.Flight;
import model.Passenger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(DefaultListModel<Passenger> listOfPassengers,
                      DefaultListModel<Aircraft> listOfAircraft,
                      DefaultListModel<Flight> listOfFlights) {
        JSONObject json = new JSONObject();
        json.put("listOfPassengers", new JSONArray(listOfPassengers.toArray()));
        json.put("listOfAircraft", new JSONArray(listOfAircraft.toArray()));
        json.put("listOfFlights", new JSONArray(listOfFlights.toArray()));
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
