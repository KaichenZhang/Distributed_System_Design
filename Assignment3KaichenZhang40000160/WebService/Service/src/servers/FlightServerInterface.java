package servers;

//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
////import org.omg.CORBA.ORB;
////
////import DFRS.ServerInterface;
////import DFRS.ServerInterfacePOA;
//import Domain.Flight;
//import Domain.Passenger;
//import Domain.PassengerRecord;
//import Domain.FlightRecord;
//import Domain.PassengerEntry;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface FlightServerInterface{

    //Passenger put a flight
    @WebMethod
    public String bookFlight(String firstName, String lastName, String address, String telephone, String destination, String date, int flightOfclass);
    
    @WebMethod    
    public String getBookledFlightCount(int recordType);

    @WebMethod
    public String editRecord(String r_id, String fieldName, String newValues);
    
    @WebMethod
    public String transferReservation(String passengerID, String currentCity, String otherCity);
    

}
