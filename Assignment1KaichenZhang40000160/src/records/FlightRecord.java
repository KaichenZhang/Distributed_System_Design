package records;

import java.io.Serializable;

import auxiliary.RandomString;

public class FlightRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recordID;
	private String departure;
	private String destination;
	
	private String firstSeats;
	private String businessSeats;
	private String economySeats;
	
	
	
	private String dateOfFlight;
	
 
	
	public FlightRecord(String departure, String destination, String dateOfFlight) {
		this.departure = departure;
		this.destination = destination;
		this.dateOfFlight = dateOfFlight;
		this.recordID = new RandomString().getRandomString(3) + new RandomString().getRandomStringNum(5);
		this.firstSeats = new RandomString().getRandomStringNum(1);
		this.businessSeats = "2" +new RandomString().getRandomStringNum(1);
		this.economySeats = "1" + new RandomString().getRandomStringNum(2);
		
		
		
	}

	



	public String getRecordID() {
		return recordID;
	}

	public String getDeparture() {
		return departure;
	}

	public String getDestination() {
		return destination;
	}

	public String getDateOfFlight() {
		return dateOfFlight;
	}
	
	public String getSeatsOfFirstClass() {
		return firstSeats;
	}
	
	public String getSeatsOfBusinessClass() {
		return businessSeats;
	}
	
	public String getSeatsOfEconomyClass() {
		return economySeats;
	}
	
	
	
	
	
	
	
	
}
