package servers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.omg.CORBA.ORB;

import DFRSApp.ServerInterfacePOA;
import Records.FlightRecord;
import Records.Passenger;
import Records.PassengerRecord;

public abstract class FlightServer extends ServerInterfacePOA{
	protected FlightRecord flight_record;
	protected PassengerRecord passenger_record;
	protected static final int server_ports[] = { 2001, 2002, 2003 };
	protected String city;
	protected ORB orb;
	protected String filePath;
	private File logFile;
	protected static Map<String, Integer> serverPortMap = new HashMap<String, Integer>();
	protected String format = "yyyy/MM/dd";

	static {
		serverPortMap.put("MTL", 2001);
		serverPortMap.put("WDC", 2002);
		serverPortMap.put("NDL", 2003);
	}



	public FlightServer() {
		this.flight_record = new FlightRecord();
		this.passenger_record = new PassengerRecord();
		createFlightRecord(this.flight_record);

	}

	public abstract void createFlightRecord(FlightRecord f);

	public void exportServer() throws Exception {
	}

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public String bookFlight(String firstName, String lastName, String address, String telephone, String destination, String date, int seatClass) {
		Passenger i = null;
		try {
			String flight_number = this.flight_record.putFlight(this.city, destination, date, seatClass);
			i = new Passenger(firstName, lastName, address, telephone, flight_number, date, seatClass, city, destination);
			String key = (lastName.substring(0, 1)).toUpperCase();
			passenger_record.put(key, i);
			System.out.println("Passenger booked a seat: " + firstName + " "+ lastName + " to " + destination);
			System.out.println("\nCurrent status of flights: ");
			System.out.println("********************************************************************************");
			System.out.println(flight_record);
			System.out.println("********************************************************************************");
			this.writeLog("bookFlight: " + i.toString());
		} catch (RuntimeException e) {
			return e.getMessage();
		}
		return "Done-" + i.toString();
	}

	@Override
	public String getBookedFlightCounts(int recordType) {
		String reply = "";
		synchronized (passenger_record) {
				reply = getAllReservedTicketCount();
		}
		this.writeLog("getReservedTicketCount: " + reply.toString());
		return reply.toString();
	}
	
	private String getAllReservedTicketCount() {
		int mtl = 0, WDC = 0, NDL = 0;
		for (Entry<String, Integer> entry : serverPortMap.entrySet()) {
			switch (entry.getKey()) {
			case "MTL":
				mtl = getTicketCount(entry.getValue());
				break;
			case "WDC":
				WDC = getTicketCount(entry.getValue());
				break;
			case "NDL":
				NDL = getTicketCount(entry.getValue());
				break;
			}
		}
		return "MTL: " + mtl + "\t" + "WDC: " + WDC + "\t" + "NDL: " + NDL;
	}
	
	private int getTicketCount(Integer port) {
		int val = 0;
		for (int i = 0; i < 4; i++) {
			String res = getTicketCountByPort(i, port);
			String[] resArr = res.split(" ");
			val = val + Integer.parseInt(resArr[1]);
		}
		return val;
	}

	private String getTicketCountByPort(int recordType, int port) {
		DatagramSocket socket = null;
		String reply = "";
		try {
			socket = new DatagramSocket();
			byte[] message = Integer.toString(recordType).getBytes("UTF-8");
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket req = new DatagramPacket(message, message.length,
					host, port);
			socket.send(req);
			byte buffer[] = new byte[100];
			DatagramPacket p = new DatagramPacket(buffer, buffer.length);
			socket.receive(p);
			reply = ((new String(p.getData())).trim());
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		return reply.toString();
	}

	@Override
	public String editRecord(String recordId, String fieldName, String newValues) {
		String reply = "";
		if (fieldName.equalsIgnoreCase("getall")) {
			reply = this.flight_record.toString();
		} else if (fieldName.contains("edit")) {
			String changedFields[] = fieldName.substring(4).split("$");
			String changedValues[] = newValues.split(",");
			if (changedFields.length == changedValues.length) {
				String result = "";
				for (int i = 0; i < changedFields.length; i++) {
					result = this.flight_record.changeFlightAttribute(recordId,changedFields[i], changedValues[i]);
				}
				if (result.contains("Error"))
					return result;
				else
					reply = result;
			} else {

			}
		}
	
		this.writeLog("editRecord: " + reply.toString());
		return "RIGHT-" + reply;
	}

	public void UDPServer(int port) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port);
			System.out.println("UDP Server Started at port: " + port);
			System.out.println("Waiting for client...");
			while (true) {
				byte[] buffer = new byte[100];
				DatagramPacket request = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(request);
				String req = new String(request.getData());
				this.writeLog(req);
				if (req != null && req.startsWith("bookFlight")) {
					bookFlightForUDPReq(req);
				} else if (req != null && req.startsWith("cancelFlight")) {
					cancelFlightForUDPReq(req);
				} else {

					int requestData = Integer.parseInt((new String(request
							.getData()).trim()).toString());
					buffer = (city.toUpperCase() + " " + this.passenger_record
							.getRecordByClass(requestData)).toString()
							.getBytes("UTF-8");
				}
				DatagramPacket reply = new DatagramPacket(buffer,
						buffer.length, request.getAddress(), request.getPort());
				socket.send(reply);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
				System.out.println("UDP Server Closed");
			}
		}
	}

	
	@Override
	public String transferReservation(String passengerID, String currentCity, String otherCity) {
		String msg = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		writeLog("transferReservation: " + passengerID + " | " + currentCity
				+ " | " + otherCity);
		Passenger passenger = getPassengerById(passengerID);
		if (passenger != null) {
			synchronized (passenger) {
				String req = "bookFlight&" + currentCity + "&" + passengerID
						+ "&" + passenger.getFirstName() + "&"
						+ passenger.getLastName() + "&"
						+ passenger.getAddress() + "&"
						+ passenger.getTelephone() + "&"
						+ passenger.getDestination() + "&"
						+ simpleDateFormat.format(passenger.getDate()) + "&"
						+ passenger.getFlightClass();
				writeLog("transferReservation: " + req);
				sendUdpMessage(req, serverPortMap.get(otherCity));
				msg = "Passenger Record Transfor Completed!";
			}

		} else {
			msg = "No passenger found for transfer.";
		}
		return msg;

	}

	public Passenger getPassengerById(String id) {
		Passenger travelRoute = null;
		for (char i = 'A'; i <= 'Z'; i++) {
			String key = (char) i + "";
			ArrayList<Passenger> list = passenger_record
					.getPassengerRecords().get(key);
			if (list != null && !list.isEmpty()) {
				for (Passenger passenger : list) {
					if (passenger != null)
						writeLog("passenger.getId() " + passenger.getRecordID());
					if (passenger != null && id.equalsIgnoreCase(passenger.getRecordID())) {
						travelRoute = passenger;
						writeLog("getPassengerById: match found" + id);

						break;
					}
				}
			}
		}

		return travelRoute;
	}


	public String sendUdpMessage(String message, int serverPort) {
		String serverMsg = "";
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
			byte[] sendData = new byte[1000];
			sendData = message.getBytes();
			InetAddress clientHost = InetAddress.getByName("localhost");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientHost, serverPort);
			clientSocket.send(sendPacket);

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (clientSocket != null) {
				clientSocket.close();
				clientSocket = null;
			}
		}
		return serverMsg;
	}


	protected void bookFlightForUDPReq(String msg) {
		String[] values = msg.split("&");
		String res = this.bookFlight(values[3], values[4], values[5],values[6], values[7], values[8],Integer.parseInt(values[9].trim()));
		writeLog(res);
		if (res.startsWith("Booking for transfored passenger:")) {
			String req = "cancelFlight&" + values[2];
			sendUdpMessage(req, serverPortMap.get(values[1]));
			writeLog(req);

		}
	}


	protected void cancelFlightForUDPReq(String msg) {
		String res;
		String[] values = msg.split("$");
		synchronized (passenger_record) {
			res = this.passenger_record
					.removePassengerById(values[1].trim());
		}
	}


	public void createLogFile() {
		filePath = "ServerLog/" + city + ".txt";
		logFile = new File(filePath);
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(filePath), true);
			if (!logFile.exists()) {
				logFile.createNewFile();
			} else {
				
				pw.write("");
			}
			pw.println();
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void writeLog(String msg) {
		PrintWriter pw;
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		String date = simpleDateFormat.format(new Date());
		try {
			pw = new PrintWriter(new FileWriter(filePath, true));
			pw.printf("%-20s %-80s", date, msg);
			pw.println();
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
