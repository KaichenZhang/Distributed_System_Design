package records;

import java.io.Serializable;
import java.util.Date;

import auxiliary.RandomString;

public class PassengerRecord implements Serializable {


	private static final long serialVersionUID = 1L;
	private String recordID;
	private Passenger passenger;
	private String destination;
	private String flightClass;
	private String dateOfFlight;
	
	public PassengerRecord(Passenger passenger, String destination, String flightClass, String dateOfFlight) {
		this.recordID = new RandomString().getRandomString(4) + new RandomString().getRandomStringNum(6);
		this.passenger = passenger;
		this.destination = destination;
		this.flightClass = flightClass;
		this.dateOfFlight = dateOfFlight;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public String getDestination() {
		return destination;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public String getDateOfFlight() {
		return dateOfFlight;
	}
	
	public String getRecordID(){
		return recordID;
	}
	
	
	
}
