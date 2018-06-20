package Records;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightRecord {

    private static int init_ID = 1000;
    private Map<String, Flight> records;

    public FlightRecord() {
        records = getInstance();
    }

    private Map<String, Flight> getInstance() {
        if (records == null) {
            records = Collections.synchronizedMap(new HashMap<String, Flight>());
        }
        return records;
    }

    public String putFlight(String departure, String destination, String date, int flight_class) {
        synchronized (records) {
            List<Flight> list = new ArrayList<Flight>(records.values());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            for (Flight f : list) {
                synchronized (f) {
                    //synchronized the flight object
                    if (departure.equalsIgnoreCase(f.getDeparture())
                            && destination.equalsIgnoreCase(f.getDestination())) {

                        if (date.equalsIgnoreCase(sdf.format(f.getDate()))) {
                            switch (flight_class) {
                                case 1:
                                    if (f.numberOfEconomy > 0) {
                                        f.numberOfEconomy--;
                                    } else {
                                        throw new RuntimeException("Error: There are not seats!");
                                    }
                                    break;
                                case 2:
                                    if (f.numberOfBusiness > 0) {
                                        f.numberOfBusiness--;
                                    } else {
                                        throw new RuntimeException("ERROR-There are not seats!");
                                    }
                                    break;
                                case 3:
                                    if (f.numberOfFirst > 0) {
                                        f.numberOfFirst--;
                                    } else {
                                        throw new RuntimeException("ERROR-There are not seats!");
                                    }
                            }
                            return f.getFlightNumber();
                        }
                    }
                }

            }
        }

        throw new RuntimeException("Error: The flight cannot be found!");
    }

    public String addFlight(String from, String to, String date, int economicSeats, int businessSeats, int firstSeats) {
        Date d;
        try {
            d = (new SimpleDateFormat("yyyy/MM/dd")).parse(date);
            int flightID = ++init_ID;
            Flight f = new Flight(flightID, from, to, d, economicSeats, businessSeats, firstSeats);
            records.put("" + flightID, f);
            synchronized (records) {
                records.put("" + flightID, f);
                if (records.containsKey(flightID)) {
                    return "Error: The flight cannot be added!";
                } else {
                    return "Successful!";
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public Boolean deleteFlightByID(String flightID) {
        try {
            synchronized (records) {
                for (Map.Entry<String, Flight> entry : records.entrySet()) {
                    if (entry.getValue().getFlightNumber().equalsIgnoreCase(flightID)) {
                        System.out.println("Hello2\n");
                        records.remove(entry.getKey());
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            return false;
        }
        return null;
    }

    public String changeFlightAttribute(String flightID, String field, String value) {
        try {
            synchronized (records) {
                for (Map.Entry<String, Flight> entry : records.entrySet()) {
                    if (entry.getValue().getFlightNumber().equalsIgnoreCase(flightID)) {
                        if (field.equalsIgnoreCase("destination")) {
                            entry.getValue().setDestination(value);
                        } else if (field.equalsIgnoreCase("date")) {
                            Date d;
                            try {
                                d = (new SimpleDateFormat("yyyy/MM/dd")).parse(value);
                            } catch (ParseException e) {
                                return "Error: The date cannot be changed!";
                            }
                            entry.getValue().setDate(d);
                        } else if (field.equalsIgnoreCase("economy")) {
                            entry.getValue().setNumberOfEconomy(Integer.parseInt(value));
                        } else if (field.equalsIgnoreCase("bussiness")) {
                            entry.getValue().setNumberOfBusiness(Integer.parseInt(value));
                        } else if (field.equalsIgnoreCase("first")) {
                            entry.getValue().setNumberOfFirst(Integer.parseInt(value));
                        } else {
                            return "Error: The flight cannot be changed!";
                        }
                        return entry.getValue().toString();
                    }
                }
            }

        } catch (Exception e) {
            return "Error";
        }
        return "Error: The flight cannot be found!";
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Flight f : new ArrayList<Flight>(records.values())) {
            s.append(f.toString() + '\n');
        }
        return s.toString();
    }
}
