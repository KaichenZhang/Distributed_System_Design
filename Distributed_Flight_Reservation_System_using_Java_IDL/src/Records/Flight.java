package Records;

import java.text.SimpleDateFormat;
import java.util.Date;	

public class Flight {
	public String flightID;
	public String departure;
	public String destination;
	public Date date;
	public int numberOfFirstClass;
	public int numberOfBusinessClass;
	public int numberOfEconomyClass;
	
	public Flight(){
		
	}
	
	public Flight(String flightID, String departure, String destination, Date date, int numberOfFirst, int numberOfBusiness, int numberOfEconomy){
		this.flightID = flightID;
		this.departure = departure;
		this.destination = destination;
		this.date = date;
		this.numberOfFirstClass = numberOfFirst;
		this.numberOfBusinessClass = numberOfBusiness;
		this.numberOfEconomyClass = numberOfEconomy;
	}


	public String getFlightNumber(){
		return departure.toUpperCase()+flightID;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumberOfFirstClass() {
		return numberOfFirstClass;
	}

	public void setNumberOfFirstClass(int numberOfFirst) {
		this.numberOfFirstClass = numberOfFirst;
	}

	public int getNumberOfBusinessClass() {
		return numberOfBusinessClass;
	}

	public void setNumberOfBusinessClass(int numberOfBusiness) {
		this.numberOfBusinessClass = numberOfBusiness;
	}

	public int getNumberOfEconomyClass() {
		return numberOfEconomyClass;
	}

	public void setNumberOfEconomyClass(int numberOfEconomy) {
		this.numberOfEconomyClass = numberOfEconomy;
	}
	
	@Override
	public String toString(){
		return departure+flightID + " : " + departure + " ---> " + destination + " \t" + new SimpleDateFormat("yyyy/MM/dd").format(date) + " \t " + 
		"FirstClass: " + numberOfFirstClass + ", BusinessClass: " + numberOfBusinessClass + ", EconomyClass: " + numberOfEconomyClass+"\r\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		try{
			Flight flight = (Flight) obj;
			return (flight.flightID == this.flightID) ? true : false;
		}catch(Exception e){
			return false;
		}
	}
	
	
}
