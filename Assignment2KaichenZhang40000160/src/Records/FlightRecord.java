package Records;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FlightRecord {
	private Map<String, Flight> records;
	private FlightRecord instance;
	protected String filePath;
	private File logFile;

	public FlightRecord() {
		records = getInstance();
	}

	private Map<String, Flight> getInstance() {
		if (records == null) {
			records =  Collections.synchronizedMap(new HashMap<String, Flight>());
		}
		return records;
	}
	
	public String putFlight(String departure, String destination, String date, int flight_class) {
		List<Flight> list = new ArrayList<Flight>(records.values());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (Flight f : list) {
			synchronized (f) {
				if (departure.equalsIgnoreCase(f.getDeparture())
						&& destination.equalsIgnoreCase(f.getDestination())) {

					if (date.equalsIgnoreCase(sdf.format(f.getDate()))) {
						switch (flight_class) {
						case 1:
							if (f.numberOfFirstClass > 0) f.numberOfFirstClass--;
							else throw new RuntimeException("Error: There is no seat available!");
							break;
						case 2:
							if (f.numberOfBusinessClass > 0) f.numberOfBusinessClass--;
							else throw new RuntimeException("ERROR: There is no seat available!");							
							break;
						case 3:
							if (f.numberOfEconomyClass > 0) f.numberOfEconomyClass--;
							else throw new RuntimeException("ERROR: There is no seat available!");
						}
						return f.getFlightNumber();
					}
				}
			}
			
		}
		throw new RuntimeException("Error: The flight cannot be found!");
	}

	public String addFlight(String departure, String destination, String date, int firstClassSeats, int businessClassSeats, int economyClassSeats) {
		Date d;
		try {
			d = (new SimpleDateFormat("yyyy/MM/dd")).parse(date);
			String flightID = new RandomString().getRandomStringNum(4);
			Flight f = new Flight(flightID, departure, destination, d, firstClassSeats, businessClassSeats, economyClassSeats);
			records.put("" + flightID, f);
			synchronized (records) {
				records.put("" + flightID, f);
				if (records.containsKey(flightID))
					return "Error: The already exsits flight cannot be added!";
				else
					return "Successfully added a flight!";
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "Error";
	}

	public Boolean deleteFlightByID(String flightID) {
		try{
			synchronized (records){
				for(Map.Entry<String, Flight> entry:records.entrySet()){
					if(entry.getValue().getFlightNumber().equalsIgnoreCase(flightID)){
						System.out.println("Deleted"+"\r\n");
						records.remove(entry.getKey());
						return true;
					}
				}
			}
			
		}catch (Exception e) {
			return false;
		}
		return null;
	}
	

	public String changeFlightAttribute(String flightID, String field, String value) {
		try{
			synchronized (records){
				for(Map.Entry<String, Flight> entry:records.entrySet()){
					if(entry.getValue().getFlightNumber().equalsIgnoreCase(flightID)){
						if(field.equalsIgnoreCase("destination")){
							entry.getValue().setDestination(value);
						}else if (field.equalsIgnoreCase("date")) {
							Date d;
							try {
								d = (new SimpleDateFormat("yyyy/MM/dd")).parse(value);
							} catch (ParseException e) {
								return "Error: The date cannot be changed!";
							}
							entry.getValue().setDate(d);
						}else if (field.equalsIgnoreCase("economy")) {
							entry.getValue().setNumberOfEconomyClass(Integer.parseInt(value));
						} else if (field.equalsIgnoreCase("bussiness")) {
							entry.getValue().setNumberOfBusinessClass(Integer.parseInt(value));
						} else if (field.equalsIgnoreCase("first")) {
							entry.getValue().setNumberOfFirstClass(Integer.parseInt(value));
						} else {
							return "Error: The flight cannot be changed!";
						}
						return entry.getValue().toString();
					}
				}
			}
			
		}
		catch (Exception e) {
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
