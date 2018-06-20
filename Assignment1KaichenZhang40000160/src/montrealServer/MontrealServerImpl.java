package montrealServer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import auxiliary.DataIO;
import auxiliary.WriteLogFile;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import records.FlightRecord;
import records.FlightRecordsList;
import records.PassengerRecord;
import records.PassengerRecordsList;
import serverInterface.DFRSInterface;

public class MontrealServerImpl implements DFRSInterface {
	private static final int port = 2000;
	private static final int UDP_PORT = 2010;
	private static final int UDP_PORT_WST = 2011;
	private static final int UDP_PORT_NDL = 2012;
	private DatagramSocket aSocket = null;
	private static final String serverName = "Montreal";
	private static final String HOST_NAME = "localhost";

	private HashMap<String, PassengerRecordsList> passengerRecordsMap = new HashMap<>();
	private FlightRecordsList flightRecords;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	
	public static void main(String[] args) {
		try {
			MontrealServerImpl server = new MontrealServerImpl();
			server.exprotServer();
			System.out.println(serverName + " server is running and listening to port " + port);
			server.UDPServer();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exprotServer() throws Exception {
		Remote remoteObj = UnicastRemoteObject.exportObject(this, port);
		Registry r = LocateRegistry.createRegistry(port);
		r.bind(serverName, remoteObj);
	}

	@Override
	public synchronized void bookFlight(PassengerRecord passengerRecord) throws RemoteException {
		
		try {
			passengerRecordsMap = new Gson().fromJson(new BufferedReader(new FileReader("src/montrealServer/passengerRecords.json")), new TypeToken<HashMap<String, PassengerRecordsList>>() {}.getType());
			if(passengerRecordsMap == null) passengerRecordsMap = new HashMap<>();
			
			String c = Character.toString(Character.toLowerCase(passengerRecord.getPassenger().getLastName().charAt(0)));
			
			if(passengerRecordsMap.get(c) == null) {
				PassengerRecordsList passengerRecords = new PassengerRecordsList();
				passengerRecords.addPassengerRecord(passengerRecord);
				passengerRecordsMap.put(c, passengerRecords);
			} else {
				passengerRecordsMap.get(c).addPassengerRecord(passengerRecord);
			}
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		DataIO.sharedInstance().writeToJsonFile("src/montrealServer/passengerRecords.json", passengerRecordsMap);
		
		Date now = new Date();
		String passengerID = "{Passenger}: " + passengerRecord.getRecordID();
		String operation = "booked a ticket: Montreal --> " + passengerRecord.getDestination() + "< " + passengerRecord.getDateOfFlight() + " >";
		new WriteLogFile(sdf.format(now), passengerID, operation).writeToLog("src/montrealServer/log.txt");
			
	}
	
	@Override
	public synchronized void addFlightRecord(FlightRecord fr) throws RemoteException {
		
		flightRecords = DataIO.sharedInstance().readFromJsonFile("src/montrealServer/flightRecords.json", FlightRecordsList.class);
		if(flightRecords == null) flightRecords = new FlightRecordsList();
		flightRecords.addFlightRecord(fr);
		

		DataIO.sharedInstance().writeToJsonFile("src/montrealServer/flightRecords.json", flightRecords);	
		Date now = new Date();
		String operator = "{Manager}: ";
		String operation = "added a new flight: Montreal --> " + fr.getDestination() + "< " + fr.getDateOfFlight() + " >";
		new WriteLogFile(sdf.format(now), operator, operation).writeToLog("src/montrealServer/log.txt");
		
	}

	@Override
	public void editFlightRecord(FlightRecord fr, String recordID) throws RemoteException {
		flightRecords = DataIO.sharedInstance().readFromJsonFile("src/montrealServer/flightRecords.json", FlightRecordsList.class);
		if(flightRecords == null) flightRecords = new FlightRecordsList();
		
		ArrayList<FlightRecord> records = flightRecords.getFlightRecords();
		ListIterator<FlightRecord> iterator = records.listIterator();
		
		while(iterator.hasNext()) {
			FlightRecord record = (FlightRecord) iterator.next();
			if(record.getRecordID().equals(recordID)){
				iterator.remove();
			}
		}
		
		flightRecords.addFlightRecord(fr);
		DataIO.sharedInstance().writeToJsonFile("src/montrealServer/flightRecords.json", flightRecords);
		
		
		Date now = new Date();
		String operator = "{Manager}: ";
		String operation = "edited the flight with ID" + recordID + " : Montreal --> " + fr.getDestination().toString() + "< " + fr.getDateOfFlight() +" >";
		new WriteLogFile(sdf.format(now), operator, operation).writeToLog("src/montrealServer/log.txt");
	}
	

	

	@Override
	public ArrayList<String> getAvailableDates(String dest) throws RemoteException {
		ArrayList<String> dates = new ArrayList<>();
		if(flightRecords == null) flightRecords = new FlightRecordsList();
		flightRecords = DataIO.sharedInstance().readFromJsonFile("src/montrealServer/flightRecords.json", FlightRecordsList.class);
		if(flightRecords != null) {
			for(FlightRecord fr: flightRecords.getFlightRecords()) {
				if(dest.equals(fr.getDestination()) && serverName.equals(fr.getDeparture())) {
					dates.add(fr.getDateOfFlight());
				}
			}
 		}
		
		Date now = new Date();
		String operator = "{Passenger}: ";
		String operation = "searching available dates from Montreal to " + dest;
		new WriteLogFile(sdf.format(now), operator, operation).writeToLog("src/montrealServer/log.txt");
		
		if(dates.size() != 0) return dates;
		else return null;
	}

	@Override
	public String getBookedTicketCounts() throws RemoteException {
		DatagramSocket aSocket = null;
		DatagramSocket bSocket = null;
		try {
			aSocket = new DatagramSocket();
			bSocket = new DatagramSocket();
			
			String message = "Montreal Server";
			byte[] m  = message.getBytes();
			InetAddress aHost = InetAddress.getByName(HOST_NAME);
			 
			DatagramPacket requestToWST = new DatagramPacket(m, message.length(), aHost, UDP_PORT_WST);
			aSocket.send(requestToWST);
			
			DatagramPacket requestToNDL = new DatagramPacket(m, message.length(), aHost, UDP_PORT_NDL);
			bSocket.send(requestToNDL);
			
			byte[] buffer = new byte[1000];
			byte[] buffer2 = new byte[1000];
			DatagramPacket replyFromWST = new DatagramPacket(buffer, buffer.length);
			DatagramPacket replyFromNDL = new DatagramPacket(buffer2, buffer2.length);
			aSocket.receive(replyFromWST);
			bSocket.receive(replyFromNDL);
			
			String WSTCount = new String(replyFromWST.getData());
			String NDLCount = new String(replyFromNDL.getData());
 
			StringBuilder sb = new StringBuilder("MTL" + getCountsFromDB());
			sb.append("\n");
			sb.append(WSTCount);
			sb.append("\n");
			sb.append(NDLCount);
			
			Date now = new Date();
			String operator = "{Manager}: ";
			String operation = "counting the number of all the flight records";
			new WriteLogFile(sdf.format(now), operator, operation).writeToLog("src/montrealServer/log.txt");
			
			return sb.toString();
		} catch(SocketException e){
			System.out.println("socket error");
			return null;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("UnknownHostException error");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException error");
			return null;
		} finally {
			if(aSocket != null) aSocket.close();
		}
	}
	
	
	private void UDPServer() {
		try {	 
			aSocket = new DatagramSocket(UDP_PORT);
			byte[] buffer = new byte[1000];
			while(true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				
				String MTLCount = "MTL" + getCountsFromDB();
				byte[] sendBack = MTLCount.getBytes();
				DatagramPacket reply = new DatagramPacket(sendBack, sendBack.length, 
						request.getAddress(), request.getPort());
				aSocket.send(reply);
			}
		} catch(SocketException e){
			System.out.println("SocketException error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 
	private int getCountsFromDB() {
		// TODO Auto-generated method stub
		int count = 0;
		
		HashMap<String, PassengerRecordsList> passengerRecordsMap;
		try {
			passengerRecordsMap = new Gson().fromJson(new BufferedReader(new FileReader("src/montrealServer/passengerRecords.json")),
					new TypeToken<HashMap<String, PassengerRecordsList>>() {}.getType());
			if(passengerRecordsMap == null) return 0;
			
			for (Map.Entry<String, PassengerRecordsList> entry : passengerRecordsMap.entrySet()) {
				PassengerRecordsList passengerRecords = entry.getValue();
				count += passengerRecords.count();
			}
			return count;
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}	
	}
	
}









