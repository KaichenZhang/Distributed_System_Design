package records;

import java.util.ArrayList;

public class FlightRecordsList {
	ArrayList<FlightRecord> flightRecords;
	
	public FlightRecordsList(){
		flightRecords = new ArrayList<>();
	}
	
	public void addFlightRecord(FlightRecord fr){
		flightRecords.add(fr);
	}
	
	public void removeFlightRecord(FlightRecord fr){
		flightRecords.remove(fr);
	}

	public ArrayList<FlightRecord> getFlightRecords() {
		return flightRecords;
	}
}
