package Records;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PassengerRecord {
	
	private Map<String, ArrayList<Passenger>> passengerRecords;
	protected String filePath;
	private File logFile;

	public PassengerRecord() {
		passengerRecords = getInstance();
	}


	private Map<String, ArrayList<Passenger>> getInstance() {
		if (passengerRecords == null) {
			passengerRecords = new HashMap<String, ArrayList<Passenger>>();
			populateHashMap();
		}
		return passengerRecords;
	}

	public void put(String key, Passenger value) {
		getPassengerListByKey(key).add(value);
	}

	private List<Passenger> getPassengerListByKey(String c) {
		return passengerRecords.get(c.toUpperCase());
	}
	
	public Integer getRecordByClass(int flightClass) {
		int counter = 0;
		for (Passenger i : getValues()) {
			if (i.getFlightClass() == flightClass)
				counter++;
		}
		return counter;
	}

	public List<Passenger> getValues() {
		List<Passenger> values = new ArrayList<Passenger>();
		for (char i = 'A'; i <= 'Z'; i++) {
			values.addAll((Collection<? extends Passenger>) passengerRecords
					.get("" + i));
		}
		return values;
	}

	private void populateHashMap() {
		for (char i = 'A'; i <= 'Z'; i++) {
			ArrayList<Passenger> list = new ArrayList<Passenger>();
			String key = ((char) i + "");
			passengerRecords.put(key, list);
		}
	}

	public String removePassengerById(String id) {
		String msg = "Passenger is not deleted";
		for (char i = 'A'; i <= 'Z'; i++) {
			String key = (char) i + "";
			ArrayList<Passenger> list = getPassengerRecords().get(key);
			if (list != null && !list.isEmpty()) {
				for (Passenger passenger : list) {
					if (passenger != null)
						if (passenger != null
								&& id.equalsIgnoreCase(passenger.getRecordID())) {
							getPassengerRecords().get(key).remove(passenger);
							msg = "Passenger with ID : " + id
									+ " remove successfully";
							break;
						}
				}
			}

		}
		return msg;
	}
	
	public Map<String, ArrayList<Passenger>> getPassengerRecords() {
		return passengerRecords;
	}

	public void setPassengerRecords(
			Map<String, ArrayList<Passenger>> passengerRecords) {
		this.passengerRecords = passengerRecords;
	}

}
