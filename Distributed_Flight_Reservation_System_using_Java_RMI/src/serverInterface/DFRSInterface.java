package serverInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import records.FlightRecord;
import records.PassengerRecord;

public interface DFRSInterface extends Remote {


	public void bookFlight(PassengerRecord passengerRecord) throws RemoteException;
	
	public void addFlightRecord (FlightRecord fr) throws RemoteException;
	
	//edit existing flight with its record ID
	public void editFlightRecord (FlightRecord fr, String recordID) throws RemoteException;
	
	public ArrayList<String> getAvailableDates(String dest) throws RemoteException;
			
	public String getBookedTicketCounts() throws RemoteException;
	
}
