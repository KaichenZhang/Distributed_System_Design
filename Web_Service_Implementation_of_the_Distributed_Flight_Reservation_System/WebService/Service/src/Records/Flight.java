package Records;

import java.text.SimpleDateFormat;
import java.util.Date;	

public class Flight {
	public int flightID;
	public String departure;
	public String destination;
	public Date date;
	public int numberOfEconomy;
	public int numberOfBusiness;
	public int numberOfFirst;
	
	public Flight(){
		
	}
	
	public Flight(int flightID, String from, String to, Date date, int numberOfEconomy, int numberOfBusiness, int numberOfFirst){
		this.flightID = flightID;
		this.departure = from;
		this.destination = to;
		this.date = new Date(date.toString());
		this.numberOfEconomy = numberOfEconomy;
		this.numberOfBusiness = numberOfBusiness;
		this.numberOfFirst = numberOfFirst;
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

	public int getNumberOfEconomy() {
		return numberOfEconomy;
	}

	public void setNumberOfEconomy(int numberOfEconomy) {
		this.numberOfEconomy = numberOfEconomy;
	}

	public int getNumberOfBusiness() {
		return numberOfBusiness;
	}

	public void setNumberOfBusiness(int numberOfBusiness) {
		this.numberOfBusiness = numberOfBusiness;
	}

	public int getNumberOfFirst() {
		return numberOfFirst;
	}

	public void setNumberOfFirst(int numberOfFirst) {
		this.numberOfFirst = numberOfFirst;
	}
	
	@Override
	public String toString(){
		return departure+flightID + " : " + departure + " ---> " + destination + " \t" + new SimpleDateFormat("yyyy/MM/dd").format(date) + " \t " + 
		"Economy: " + numberOfEconomy + ", Business: " + numberOfBusiness + ", First: " + numberOfFirst;
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
