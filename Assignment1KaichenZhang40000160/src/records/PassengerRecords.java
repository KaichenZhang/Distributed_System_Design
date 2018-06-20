package records;

import java.util.ArrayList;

public class PassengerRecords {
private ArrayList<PassengerRecord> passengerRecords;
	
	public PassengerRecords(){
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
