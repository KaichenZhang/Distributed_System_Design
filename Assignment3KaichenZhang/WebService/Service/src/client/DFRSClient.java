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
import java.util.Scanner;
import javax.xml.ws.Service;

import client.NDL.NDLService;
import client.MTL.MTLService;
import client.WDC.WDCService;
 

public class DFRSClient {

	private File logFile;
    private String filePath;
    private String logFileName;
    static final String servers[] = {"MTL", "WDC", "NDL"};

    public static MTLService mtlService;
    public static WDCService wdcService;
    public static NDLService NDLService;

    client.MTL.FlightServerInterface mtlServer = null;
    client.WDC.FlightServerInterface wdcServer = null;
    client.NDL.FlightServerInterface ndlServer = null;

    public DFRSClient(String logFileName) {
        this.logFileName = logFileName;
        createLogFile(logFileName);
        mtlService = new MTLService();
        wdcService = new WDCService();
        NDLService = new NDLService();

        mtlServer = mtlService.getMTLServerPort();
        wdcServer = wdcService.getWDCServerPort();
        ndlServer = NDLService.getNDLServerPort();

    }

 

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
        boolean done = false;
        DFRSClient cl = new DFRSClient("Passenger");
        do {
			System.out.println("\r\n"+"---------------------------------");
			System.out.println("Hello deer passenger,please choose the number:");
			System.out.println("1. Book a Flight");
			System.out.println("2. Exit");
            int option = Integer.parseInt(scan.nextLine());
            switch (option) {
                case 1:
                    done = bookFlightMenu(cl);
                    break;
                case 2:
                    done = true;
            }
        } while (!done);
    }


    public static boolean bookFlightMenu(DFRSClient cl) {
        Scanner scan = new Scanner(System.in);
        int departure, destination;
        int flightClass;
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
                    System.out.println("Where you want to go?");
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
                    System.out.print("Travel Date(YYYY/MM/DD): ");
                    date = scan.nextLine();
					System.out.println("\nClass:");
					System.out.println("1. First Class");
					System.out.println("2. Business Class");
					System.out.println("3. Economy Class");
                    flightClass = Integer.parseInt(scan.nextLine());
                    //Book passenger's flight
                    String confirmationCode = cl.bookPassengerFlight(departure, destination, firstName, lastName, address, telephone, date, flightClass);
                    cl.writeLog(confirmationCode);
                    System.out.println(confirmationCode);
                    return false;
                }
            } else if (option == 4) {
                return false;
            }
        } while (done);
        return done;
    }

    //Put passenger information to flight
    public String bookPassengerFlight(int departure, int destination,
            String firstName, String lastName, String address, String telephone, String date,
            int flight_class) {
        try {
        	ServerInterface server = lookupServerByCity(servers[departure - 1]);
        	
        	if(server instanceof client.MTL.FlightServerInterface) {
        		client.MTL.FlightServerInterface mtlServer = (client.MTL.FlightServerInterface) server;
        		return mtlServer.bookFlight(firstName, lastName, address, telephone, servers[destination - 1], date, flight_class);
        	} else if (server instanceof client.WDC.FlightServerInterface) {
        		client.WDC.FlightServerInterface wdcServer = (client.WDC.FlightServerInterface) server;
        		return wdcServer.bookFlight(firstName, lastName, address, telephone, servers[destination - 1], date, flight_class);
        	} else if (server instanceof client.NDL.FlightServerInterface) {
        		client.NDL.FlightServerInterface ndlServer = (client.NDL.FlightServerInterface) server;
        		return ndlServer.bookFlight(firstName, lastName, address, telephone, servers[destination - 1], date, flight_class);
        	} else {
        		return null;
        	}

            
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /*=============================== Manager Menu =====================================*/
    public static void managerMenu() {
        String credential;
        int option;
        Scanner scan = new Scanner(System.in);
        System.out.print("Login: ");
        credential = scan.nextLine();
        DFRSClient cl = new DFRSClient(credential);
        String location = credential.substring(0, 3);
        try {
            System.out.println("Loaction: " + location);
            ServerInterface server = cl.lookupServerByCity(location);
            System.out.println("Welcom Manager!\n");
            do {
                System.out.println("What you want?");
                System.out.println("1. Manage Flight ");
                System.out.println("2. Get the number of flights of class");
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
        int option;
        Scanner scan = new Scanner(System.in);

        System.out.println("Get Number of Flights for: ");
        System.out.println("1. Economic Class");
        System.out.println("2. Business Class");
        System.out.println("3. First Class");
        System.out.println("4. All");
        option = Integer.parseInt(scan.nextLine());
        try {
        	
        	if(server instanceof client.MTL.FlightServerInterface) {
        		client.MTL.FlightServerInterface mtlServer = (client.MTL.FlightServerInterface) server;
        		String reply = mtlServer.getBookledFlightCount(option);
        		System.out.println(reply);
        	} else if (server instanceof client.WDC.FlightServerInterface) {
        		client.WDC.FlightServerInterface wdcServer = (client.WDC.FlightServerInterface) server;
        		String reply = wdcServer.getBookledFlightCount(option);
        		System.out.println(reply);
        	} else if (server instanceof client.NDL.FlightServerInterface) {
        		client.NDL.FlightServerInterface ndlServer = (client.NDL.FlightServerInterface) server;
        		String reply = ndlServer.getBookledFlightCount(option);
        		System.out.println(reply);
        	} else {
        		String reply = null;
        	}
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Manage flight
    public void manageFlightRecords(ServerInterface server, String managerLocation) {
        Scanner input = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("Select one of you want:");
            System.out.println("1. Check all Flights");
            System.out.println("2. Add a new Flight");
            System.out.println("3. Delete a Flight");
            System.out.println("4. Edit a Flight");
            System.out.println("5. Back");
            option = Integer.parseInt(input.nextLine());
            switch (option) {
                case 1:
                    printAllFlight(server);
                    break;
                case 2:
                    addFlight(server, managerLocation);
                    break;
                case 3:
                    deleteFlight(server);
                    break;
                case 4:
                    editFlight(server, managerLocation);
            }
        } while (option != 5);
    }

    //Print all flights in a city
    public void printAllFlight(ServerInterface server) {
        String code = "";
        try {
            // Match "getall" to run this method through server interface.
        	if(server instanceof client.MTL.FlightServerInterface) {
        		client.MTL.FlightServerInterface mtlServer = (client.MTL.FlightServerInterface) server;
        		code = mtlServer.editRecord("", "getall", "");
        	} else if (server instanceof client.WDC.FlightServerInterface) {
        		client.MTL.FlightServerInterface wdcServer = (client.MTL.FlightServerInterface) server;
        		code = wdcServer.editRecord("", "getall", "");
        	} else if (server instanceof client.NDL.FlightServerInterface) {
        		client.MTL.FlightServerInterface ndlServer = (client.MTL.FlightServerInterface) server;
        		code = ndlServer.editRecord("", "getall", "");
        	} else {
        		code = null;
        	}
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

    public void addFlight(ServerInterface server, String managerLocation) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Select a new destionation:");
        if (managerLocation.equalsIgnoreCase(servers[0])) {
            System.out.println("\t2. Washington (WDC)");
            System.out.println("\t3. New Dehli (ndl)");
        } else if (managerLocation.equalsIgnoreCase(servers[1])) {
            System.out.println("\t1. Montreal (MTL)");
            System.out.println("\t3. New Dehli (NDL)");
        } else if (managerLocation.equalsIgnoreCase(servers[2])) {
            System.out.println("\t1. Montreal (MTL)");
            System.out.println("\t2. Washington (WDC)");
        }
        int destination = Integer.parseInt(scan.nextLine());

        //Add the new date, and the number of each class
        System.out.print("Select a date (yyyy/mm/dd): ");
        String date = scan.nextLine();

        System.out.print("Enter the number of economy class seats: ");
        int econ = Integer.parseInt(scan.nextLine());

        System.out.print("Enter the number of business class seats: ");
        int bus = Integer.parseInt(scan.nextLine());

        System.out.print("Enter the number of first class seats: ");
        int first = Integer.parseInt(scan.nextLine());

        //Print and using "&" to separate 
        StringBuilder sb = new StringBuilder();
        sb.append(servers[destination - 1]).append("&").append(date)
                .append("&").append(econ).append("&").append(bus).append("&")
                .append(first);

        String code = "";
        try {
            // Match "add" to run this method through server interface in CORBA.
            // Write the new flight information to this city server log file.
        	if(server instanceof client.MTL.FlightServerInterface) {
        		client.MTL.FlightServerInterface mtlServer = (client.MTL.FlightServerInterface) server;
        		code = mtlServer.editRecord("", "add", sb.toString());
        	} else if (server instanceof client.WDC.FlightServerInterface) {
        		client.MTL.FlightServerInterface wdcServer = (client.MTL.FlightServerInterface) server;
        		code = wdcServer.editRecord("", "add", sb.toString());
        	} else if (server instanceof client.NDL.FlightServerInterface) {
        		client.MTL.FlightServerInterface ndlServer = (client.MTL.FlightServerInterface) server;
        		code = ndlServer.editRecord("", "add", sb.toString());
        	} else {
        		code = null;
        	}
        	
            writeLog(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String state = code.substring(0, 5);//RIGHT OR ERROR
        String information = code.substring(6, code.length());
        if (state.equalsIgnoreCase("Error")) {
            System.err.println("Error: There are some problems in here:" + information);
        } else if (state.startsWith("RIGHT")) {
            System.out.println("The new fLight is added on the list: " + information);
        }

    }

    //Delete a flight
    public void deleteFlight(ServerInterface server) {
        Scanner input = new Scanner(System.in);
        //Input a unique flightID.
        System.out.println("Enter the flight ID And Delete it: ");
        String flightID = input.nextLine();

        String code = "";
        try {
        	
        	if(server instanceof client.MTL.FlightServerInterface) {
        		client.MTL.FlightServerInterface mtlServer = (client.MTL.FlightServerInterface) server;
        		code = mtlServer.editRecord(flightID, "delete", "");
        	} else if (server instanceof client.WDC.FlightServerInterface) {
        		client.MTL.FlightServerInterface wdcServer = (client.MTL.FlightServerInterface) server;
        		code = wdcServer.editRecord(flightID, "delete", "");
        	} else if (server instanceof client.NDL.FlightServerInterface) {
        		client.MTL.FlightServerInterface ndlServer = (client.MTL.FlightServerInterface) server;
        		code = ndlServer.editRecord(flightID, "delete", "");
        	} else {
        		code = null;
        	}
        	
            // Match "delete" to run this method through server interface in CORBA FILE.
        } catch (Exception e) {
            e.printStackTrace();
        }
        String state = code.substring(0, 5);//RIGHT OR ERROR
        String information = code.substring(6, code.length());
        if (state.equalsIgnoreCase("Error")) {
            System.err.println("Error: Unknown Problem." + information);
        } else if (state.startsWith("RIGHT")) {
            System.out.println("Flight deleted successfully!");
        }
        writeLog(code);
    }

    // Edit a flight
    public void editFlight(ServerInterface server, String managerLocation) {
        Scanner scan = new Scanner(System.in);
        //Input a unique flightID
        System.out.println("Enter the flight ID AND Edit it: ");
        String flightID = scan.nextLine();
        int option = 0;
        StringBuilder requestHeader = new StringBuilder();
        requestHeader.append("edit");
        StringBuilder requestBody = new StringBuilder();
        System.out.println("Select you want changed information:");
        do {
            //Every time when change a value, 1st submit, then change 2ed time.
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
                    System.out
                            .print("Enter the new number of business class seats: ");
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
        	
        	if(server instanceof client.MTL.FlightServerInterface) {
        		client.MTL.FlightServerInterface mtlServer = (client.MTL.FlightServerInterface) server;
        		code = mtlServer.editRecord(flightID, requestHeader.toString(), requestBody.toString());
        	} else if (server instanceof client.WDC.FlightServerInterface) {
        		client.WDC.FlightServerInterface wdcServer = (client.WDC.FlightServerInterface) server;
        		code = wdcServer.editRecord(flightID, requestHeader.toString(), requestBody.toString());
        	} else if (server instanceof client.NDL.FlightServerInterface) {
        		client.NDL.FlightServerInterface ndlServer = (client.NDL.FlightServerInterface) server;
        		code = ndlServer.editRecord(flightID, requestHeader.toString(), requestBody.toString());
        	} else {
        		code = null;
        	}
        	
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

    // look up flight server though Web Services.
    public ServerInterface lookupServerByCity(String city)
            throws MalformedURLException, RemoteException, NotBoundException {

    	ServerInterface server = null;
        try {
            if(city.compareToIgnoreCase("MTL") == 0) server = mtlServer;
            else if(city.compareToIgnoreCase("WDC") == 0) server = wdcServer;
            else if(city.compareToIgnoreCase("NDL") == 0) server = ndlServer;
            
        } catch (Exception e) {
            System.err.println("Error: Unable to find server: "
                    + e.getMessage());
        }

        return server;
    }

    // This transfer passenger menu method.
    public boolean transferPassengertMenu(ServerInterface server) {
        Scanner scan = new Scanner(System.in);
        int current, other;
        boolean done = false;
        do {
            System.out.println("Your current city");
            System.out.println("1. Montreal (MTL)");
            System.out.println("2. Washington (WDC)");
            System.out.println("3. New Dehli (NDL)");
            System.out.println("4. Back");
            int option = Integer.parseInt(scan.nextLine());
            if (option > 0 && option < 4) {
                do {
                    current = option;
                    System.out
                            .println("Where you want to transfer the Passenger");
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
            } else if (option == 4) {
                return false;
            }
        } while (done);
        return done;
    }

    // transferReservation method
    public String transferReservation(String passengerID, String currentCity, String otherCity) {
        String status = null;
       
        try {
        	 ServerInterface server = lookupServerByCity(currentCity);
        	if(server instanceof client.MTL.FlightServerInterface) {
        		client.MTL.FlightServerInterface mtlServer = (client.MTL.FlightServerInterface) server;
        		status = mtlServer.transferReservation(passengerID, currentCity, otherCity);
        	} else if (server instanceof client.WDC.FlightServerInterface) {
        		client.WDC.FlightServerInterface wdcServer = (client.WDC.FlightServerInterface) server;
        		status = wdcServer.transferReservation(passengerID, currentCity, otherCity);
        	} else if (server instanceof client.NDL.FlightServerInterface) {
        		client.NDL.FlightServerInterface ndlServer = (client.NDL.FlightServerInterface) server;
        		status = ndlServer.transferReservation(passengerID, currentCity, otherCity);
        	} else {
        		status = null;
        	}
   
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // Create log for a manager
    private void createLogFile(String location) {

        // Logger File Name for Manager.
        filePath = "ClientLog/" + location + ".txt";

        logFile = new File(filePath);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filePath), true);
            // create a new file
            if (!logFile.exists()) {
                logFile.createNewFile();
            } else {
                // clear the old file contents
                pw.write("");
            }
            pw.printf("%-20s %-80s", "Date", "Operation Status");
            pw.println();
            pw.write("------------------------------------------------------------------------------");
            pw.println();
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
            pw.printf("%-20s %-80s", date, msg);
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
                    System.out.println("Thank you for travelling with us!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
