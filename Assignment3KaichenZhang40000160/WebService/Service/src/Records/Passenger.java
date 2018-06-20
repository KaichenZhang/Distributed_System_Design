package Records;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Passenger {
	private static int recordNumber=1;
	private String recordID;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String destination;
	private String departure;
	private Date date;
	private String flightID;
	private int flightOfClass;
	
	
	public Passenger(String firstName, String lastName, String address, String phone, String flightID, Date date, int flightOfClass, String departure, String destination) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.flightID = flightID;
		this.date = date;
		this.flightOfClass = flightOfClass;
		this.departure = departure;
		this.destination = destination;
		this.recordID = generateId();
	}
	
	public Passenger(String firstName, String lastName, String address, String phone, String flightID, String date, int flightOfClass, String departure, String destination) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.flightID = flightID;
		try {
			this.date = (new SimpleDateFormat("yyyy/MM/dd")).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.flightOfClass = flightOfClass;
		this.departure = departure;
		this.destination = destination;
		this.recordID = generateId();
	}
	
	
	public Passenger(Passenger i){
		super();
		this.firstName = i.firstName;
		this.lastName = i.lastName;
		this.address = i.address;
		this.phone = i.phone;
		this.flightID = i.flightID;
		this.date = i.date;
		this.flightOfClass = i.flightOfClass;
		this.departure = i.departure;
		this.destination = i.destination;
	}
	
	private String generateId(){
		return Passenger.recordNumber++ + this.flightID+"-"+flightOfClass;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return phone;
	}
	public void setTelephone(String phone) {
		this.phone = phone;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getFlightClass() {
		return flightOfClass;
	}
	public void setFlightClass(int flightOfClass) {
		this.flightOfClass = flightOfClass;
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
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getRecordID()); sb.append(" | ");
		sb.append(this.flightID); sb.append(" | ");
		sb.append(this.departure + " ---> " + this.destination + " | ");
		sb.append((new SimpleDateFormat("yyyy/MM/dd")).format(this.date) + " | ");
		sb.append(this.lastName.toUpperCase() +", " + this.firstName.toUpperCase() + " | ");
		sb.append("Class: "+ this.flightOfClass);
		return sb.toString();
	}

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

}
