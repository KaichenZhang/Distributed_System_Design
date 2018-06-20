package Records;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Passenger {
	private String recordID;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String destination;
	private String departure;
	private Date date;
	private String flightID;
	private int seatClass;
	
	
	public Passenger(String firstName, String lastName, String address, String phone, String flightID, Date date, int seatClass, String departure, String destination) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.flightID = flightID;
		this.date = date;
		this.seatClass = seatClass;
		this.departure = departure;
		this.destination = destination;
		this.recordID = "Pasg@" + departure + new RandomString().getRandomStringNum(3);
	}
	
	public Passenger(String firstName, String lastName, String address, String phone, String flightID, String date, int seatClass, String departure, String destination) {
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
		this.seatClass = seatClass;
		this.departure = departure;
		this.destination = destination;
		this.recordID = "Pasg@" + departure + new RandomString().getRandomStringNum(3);
	}
	
	
	public Passenger(Passenger pasg){
		super();
		this.firstName = pasg.firstName;
		this.lastName = pasg.lastName;
		this.address = pasg.address;
		this.phone = pasg.phone;
		this.flightID = pasg.flightID;
		this.date = pasg.date;
		this.seatClass = pasg.seatClass;
		this.departure = pasg.departure;
		this.destination = pasg.destination;
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
		return seatClass;
	}
	public void setFlightClass(int flightOfClass) {
		this.seatClass = flightOfClass;
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
	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getRecordID()); sb.append(" | ");
		sb.append(this.flightID); sb.append(" | ");
		sb.append(this.departure + " ---> " + this.destination + " | ");
		sb.append((new SimpleDateFormat("yyyy/MM/dd")).format(this.date) + " | ");
		sb.append(this.lastName.toUpperCase() +", " + this.firstName.toUpperCase() + " | ");
		sb.append("Class: "+ this.seatClass);
		return sb.toString();
	}

}
