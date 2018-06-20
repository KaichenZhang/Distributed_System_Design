package client;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import DFRSApp.ServerInterface;
import DFRSApp.ServerInterfaceHelper;

public class Client {
	private File logFile;
	private String filePath;
	private String logFileName;
	static final String servers[] = { "MTL", "WDC", "NDL" };
	private static String[] corbaArr = null;
			
	//Main menu
	public static int mainMenu() {		
		Scanner scan = new Scanner(System.in);
		int option;
		do {
			System.out.println("---------------------------------");
			System.out.println("Please choose the role:");
		    System.out.println("1. Passenger");
		    System.out.println("2. Manager");
			System.out.println("3. Exit");
			System.out.println("---------------------------------");
			option = Integer.parseInt(scan.nextLine());	
		} while (option < 1 || option > 3);	
		return option;	
	}

	//Passenger	
	public static void passengerMenu() {
		Scanner scan = new Scanner(System.in);
		boolean fin = false;
		Client cl = new Client("-------------------------------Passenger---------------------------------");
		do {
			System.out.println("\r\n"+"---------------------------------");
			System.out.println("Hello deer passenger,please choose the number:");
			System.out.println("1. Book a Flight");
			System.out.println("2. Exit");
			int option = Integer.parseInt(scan.nextLine());
			switch (option) {
			case 1:
				fin = bookFlightMenu(cl);
				break;
				
			case 2:
				fin = true;
				
			}
			
		} while (!fin);
		
	}

	public static boolean bookFlightMenu(Client cl) {
		Scanner scan = new Scanner(System.in);
		int departure, destination;
		int seatClass;
		String firstName, lastName, address, telephone, date;
		boolean done = false;
		do {
			System.out.println("Please choose the departure city");
			System.out.println("1. Montreal (MTL)");
			System.out.println("2. Washington (WDC)");
			System.out.println("3. New Dehli (NDL)");
			System.out.println("4. Back");
			int option = Integer.parseInt(scan.nextLine());
			if (option > 0 && option < 4) {
				do {
					departure = option;
					System.out.println("Please choose the destination city");
					switch (option) {	
					case 1:
						System.out.println("2. Washington (WDC)");
						System.out.println("3. New Dehli (NDL)");
						break;
					case 2:
						System.out.println("1. Montreal (MTL)");
						System.out.println("3. New Dehli (NDL)");
						break;
					case 3:
						System.out.println("1. Montreal (MTL)");
						System.out.println("2. Washington (WDC)");
					}
					System.out.println("4. Back");
					option = Integer.parseInt(scan.nextLine());					
				} while (option == departure || option < 1 || option > 4);
				if (option != 4) {
					destination = option;
					System.out.println("Please input your information: ");
					System.out.print("First Name: ");
					firstName = scan.nextLine();
					System.out.print("Last Name: ");
					lastName = scan.nextLine();
					System.out.print("Address: ");
					address = scan.nextLine();
					System.out.print("Phone: ");
					telephone = scan.nextLine();
					System.out.print("Desired Travel Date(YYYY/MM/DD): ");
					date = scan.nextLine();
					System.out.println("\nClass:");
					System.out.println("1. First Class");
					System.out.println("2. Business Class");
					System.out.println("3. Economy Class");
					seatClass = Integer.parseInt(scan.nextLine());
					String confirmationCode =cl.bookPassengerFlight(departure, destination, firstName, lastName, address, telephone, date,seatClass);
					cl.writeLog(confirmationCode);
					System.out.println(confirmationCode);
					
					return false;					
				}			
			} else if (option == 4)				
				return false;			
		} while (done);
		return done;		
	}

	public String bookPassengerFlight(int departure, int destination,String firstName, String lastName, String address, String telephone, String date,int seatClass) {
		try {
			ServerInterface server = lookupServerByCity(servers[departure - 1]);
			return server.bookFlight(firstName, lastName, address, telephone, servers[destination - 1], date, seatClass);			
		} catch (Exception e) {
			return e.getMessage();		
		}		
	}

	
	//Manager
	public static void managerMenu() {
		String prefix;
		int option;
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter your manager ID(Start with city abstraction): ");
		prefix = scan.nextLine();
		Client cl = new Client(prefix);
		String location = prefix.substring(0, 3);
		try {
			System.out.println("Loaction: " + location);
			ServerInterface server = cl.lookupServerByCity(servers[0]);
			System.out.println("---------------------------Manager----------------------------------\n");
			do {
				System.out.println("");
				System.out.println("Please chose:");
				System.out.println("1. Manage Flight ");
				System.out.println("2. Get record counts by UDP messages");
				System.out.println("3. Transfer Reservation");
				System.out.println("4. Exit");
				option = Integer.parseInt(scan.nextLine());
				switch (option) {
				case 1:
					cl.manageFlightRecords(server, location);
					break;
				case 2:
					cl.getNumberOfBookedFlights(server);
					break;
				case 3:
					cl.transferPassengertMenu(server);					
				}				
			} while (option != 4);			
		} catch (MalformedURLException e) {
			System.err.println("Error: ManagerID is not right!");			
		} catch (Exception e) {
			System.err.println("Error: ManagerID is not right!");	
		}
	}

	
	public void getNumberOfBookedFlights(ServerInterface server) {
		int option=0;
		try {
			String reply = server.getBookedFlightCounts(option);
			System.out.println(reply);
		} catch (Exception e) {
			e.printStackTrace();	
		}	
	}

	public void manageFlightRecords(ServerInterface server, String managerLocation) {
		Scanner input = new Scanner(System.in);
		int option = 0;
		do {
			System.out.println("Select one of you want:");
			System.out.println("1. Check all Flights available at your server");
			System.out.println("2. Edit a Flight");
			System.out.println("3. Back");
			option = Integer.parseInt(input.nextLine());
			switch (option) {
			case 1:
				printAllFlight(server);
				break;
			case 2:
				editFlight(server, managerLocation);		
			}	
		} while (option != 3);
	}

		
	public void printAllFlight(ServerInterface server) {		
		String code = "";		
		try {		
			code = server.editRecord("", "getall", "");	
		} catch (Exception e) {
			e.printStackTrace();			
		}
		String state = code.substring(0, 5);//RIGHT OR ERROR
		String information = code.substring(6, code.length());
		if (state.equalsIgnoreCase("Error")) {
			System.err.println("Error: There are some problems in here: " + information);			
		} else if (state.startsWith("RIGHT")) {
			System.out.println(information);			
		}
		writeLog(code);		
	}


	public void editFlight(ServerInterface server, String managerLocation) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the flight ID AND Edit it: ");
		String flightID = scan.nextLine();
		int option = 0;
		StringBuilder requestHeader = new StringBuilder();
		requestHeader.append("edit");
		StringBuilder requestBody = new StringBuilder();
		System.out.println("Select you want changed information:");
		do {
			System.out.println("1. Destination");
			System.out.println("2. Date");
			System.out.println("3. The number of economy class seats");
			System.out.println("4. The number of business class seats");
			System.out.println("5. The number of first class seats");
			System.out.println("6. Submit");
			option = Integer.parseInt(scan.nextLine());
			int destination, newSeats;
			String date;
			switch (option) {
			case 1:
				System.out.println("Select a new destionation:");
				if (managerLocation.equalsIgnoreCase(servers[0])) {
					System.out.println("\t2. Washington (WDC)");
					System.out.println("\t3. New Dehli (NDL)");
				} else if (managerLocation.equalsIgnoreCase(servers[1])) {
					System.out.println("\t1. Montreal (MTL)");
					System.out.println("\t3. New Dehli (NDL)");
				} else if (managerLocation.equalsIgnoreCase(servers[2])) {
					System.out.println("\t1. Montreal (MTL)");
					System.out.println("\t2. Washington (WDC)");
				}
				destination = Integer.parseInt(scan.nextLine());
				requestHeader.append("destination&");
				requestBody.append(servers[destination - 1]).append(",");
				break;
			case 2:
				System.out.print("What is the new date: ");
				date = scan.nextLine();
				requestHeader.append("date&");
				requestBody.append(date).append(",");
				break;
			case 3:
				System.out.print("Enter the new number of economy class seats: ");
				newSeats = Integer.parseInt(scan.nextLine());
				requestHeader.append("economy&");
				requestBody.append(newSeats).append(",");
				break;
			case 4:
				System.out.print("Enter the new number of business class seats: ");
				newSeats = Integer.parseInt(scan.nextLine());
				requestHeader.append("bussiness&");
				requestBody.append(newSeats).append(",");
				break;
			case 5:
				System.out.print("Enter the new number of first class seats: ");
				newSeats = Integer.parseInt(scan.nextLine());
				requestHeader.append("first&");
				requestBody.append(newSeats).append(",");
				break;
			case 6:
				if (requestBody.length() == 0) {
					System.out.println("You did not edit it.");
					option = 0;
					break;
				} else {
					requestBody.setLength(requestBody.length() - 1);
					requestHeader.setLength(requestHeader.length() - 1);			
				}				
			}
			
		} while (option != 6);
		String code = "";
		try {
			code = server.editRecord(flightID, requestHeader.toString(), requestBody.toString());
			writeLog(code);			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		String state = code.substring(0, 5);//RIGHT OR ERROR
		String information = code.substring(6, code.length());
		if (state.equalsIgnoreCase("Error")) {
			System.err.println("Error: There are some problems in here:  " + information);		
		} else if (state.startsWith("RIGHT")) {
			System.out.println("The fLight is edited on the list:" + information);		
		}		
	}

	
	public ServerInterface lookupServerByCity(String city) throws MalformedURLException, RemoteException, NotBoundException {
		Properties props = new Properties();
	    props.put("org.omg.CORBA.ORBInitialPort", "1050");    
	    props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1"); 
	    ORB orb = ORB.init(corbaArr, props);
	    org.omg.CORBA.Object objRef = null;
	    try {
	    	objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			ServerInterface server = (ServerInterface) ServerInterfaceHelper.narrow(ncRef.resolve_str(city.toUpperCase()));
			return server;	
	    } catch (Exception e) {
	    	System.err.println("Error: Unable to find server: "+ e.getMessage());			
	    }
	    return null;	    
	}

	
	public boolean transferPassengertMenu(ServerInterface server) {
		Scanner scan = new Scanner(System.in);
		int current, other;
		boolean done = false;
		do {
			System.out.println("Please choose your server location");
			System.out.println("1. Montreal");
			System.out.println("2. Washington");
			System.out.println("3. New Dehli");
			System.out.println("4. Back");
			int option = Integer.parseInt(scan.nextLine());
			if (option > 0 && option < 4) {
				do {
					current = option;
					System.out.println("Please choose other city to transfor record");
					
					switch (option) {
					case 1:
						System.out.println("2. Washington");
						System.out.println("3. New Dehli");
						break;
					case 2:
						System.out.println("1. Montreal");
						System.out.println("3. New Dehli");
						break;
					case 3:
						System.out.println("1. Montreal");
						System.out.println("2. Washington");
						
					}
					System.out.println("4. Back");
					option = Integer.parseInt(scan.nextLine());
					
				} while (option == current || option < 1 || option > 4);		
				if (option != 4) {
					other = option;
					System.out.println("Please give the Passenger ID:");
					String passnegerID = scan.nextLine();
					String msg = null;
					try {
						msg = transferReservation(passnegerID, servers[current - 1], servers[other - 1]);						
					} catch (Exception e) {
						e.printStackTrace();		
					}
					writeLog(msg);
					System.out.println(msg);
					return false;			
				}		
			} else if (option == 4)
				return false;	
		} while (done);
		return done;		
	}
		
	
	
	public String transferReservation(String passengerID, String currentCity, String otherCity) {
		String status = null;
		try {
			status = lookupServerByCity(currentCity).transferReservation(passengerID, currentCity, otherCity);	
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return status;	
	}

	public Client(String logFileName) {
		this.logFileName = logFileName;
		createLogFile(logFileName);
	}
	
	private void createLogFile(String location) {
		filePath = "ClientLog/" + location + ".txt";
		logFile = new File(filePath);
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(filePath), true);
			if (!logFile.exists()) {
				logFile.createNewFile();
			} else {
					pw.write("");			
			}
			pw.flush();
			pw.close();	
		} catch (IOException e) {
			e.printStackTrace();	
		}		
	}

	public void writeLog(String msg) {
		PrintWriter pw;
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		try {
			pw = new PrintWriter(new FileWriter(filePath, true));
			pw.print(date+ msg);
			pw.println();
			pw.flush();
			pw.close();	
		} catch (IOException e) {
			e.printStackTrace();		
		}	
	}
	
	public String getLogFileName() {
		return logFileName;	
	}
		
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	
	public static void main(String args[]) {
		try {
			int option = mainMenu();
			switch (option) {
			case 1:
				passengerMenu();
				break;
			case 2:
				managerMenu();
				break;		
			case 3:
				System.out.println("Have a nice day!");					
			}		
		} catch (Exception e) {
					e.printStackTrace();						
		}			
	}
}
