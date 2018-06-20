package records;

import java.util.ArrayList;

public class PassengerRecordsList {
private ArrayList<PassengerRecord> passengerRecords;
	
	public PassengerRecordsList(){
		passengerRecords = new ArrayList<>();
	}
	
	public void addPassengerRecord(PassengerRecord pr){
		passengerRecords.add(pr);
	}
	
	public void removePassengerRecord(PassengerRecord pr){
		passengerRecords.remove(pr);
	}

	public ArrayList<PassengerRecord> getPassengerRecords() {
		return passengerRecords;
	}
	
	public int count() {
		return passengerRecords.size();
	}
}
