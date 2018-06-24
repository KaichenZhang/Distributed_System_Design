package clients;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import auxiliary.DataIO;
import auxiliary.WriteLogFile;
import records.FlightRecord;
import records.Manager;
import records.ManagerRecordsList;
import serverInterface.DFRSInterface;

public class ManagerClient {
	public static String serverIP= "localHost";
	public static int portMTL=2031;
	public static int portLVL=2032;
	public static int portDDO=2033;
	
	
	private Manager manager;
	private Calendar inputDate = Calendar.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	private void showCities(){
		System.out.println("1. Montreal");
		System.out.println("2. Washington");
		System.out.println("3. New Delhi");
	}
	
	private int[] parseInput(String string) {
		String[] temp = new String[5];
		int[] result = new int[5];
		temp = string.split("-");
		for(int i = 0; i < temp.length; i++){
			result[i] = Integer.parseInt(temp[i]);
		}
		return result;
	}

	private void managerControl(){
		while (true) {
			System.out.println("***********  Maneger: " + manager.getManagerID() + " ***********");
			System.out.println("Conneting to " + manager.getCity() + " server");
			System.out.println("Please input corresponding integer: ");
			System.out.println("1. Add a new flight");
			System.out.println("2. Get booked flight count");
			System.out.println("3. Edit an existing flight");
			System.out.println("4. Exit");
			
			
			Date now = new Date();
			String managerID = manager.getManagerID();
			
			Scanner input = new Scanner(System.in);
	        Boolean valid = false;
	        int userInput = 0;
	        while(!valid)
	        {
	            try {
	            	userInput = input.nextInt();
	                valid = true;
	            }
	            catch(Exception e) {
	                System.out.println("Invalid Input, please enter an Integer");
	                valid = false;
	                input.nextLine();
	            }
	        }
	        valid = false;
	        String departure = manager.getCity();
        	String destination = null;
	        switch(userInput) {
	        case 1: 
	    		System.out.println("Please choose the destination city:");
	    		showCities();
	    		while(!valid){
	    			try {
	    				int  opt = input.nextInt();
	    				if(opt <= 3 && opt >0) {
	    					valid = true;
	    					
	    					switch(opt){
	    					case 1:
	    						destination="Montreal";
	    						break;
	    					case 2:
	    						destination="Washington";
	    						break;
	    					case 3:
	    						destination="NewDelhi";
	    						break;
	    					
	    					}
	    					if(destination == departure) {
	    						System.out.println("Destination and Departure are the same.");
	    						destination = null;
	    					}
	    				} else {
	    					 valid = false;
	    					 System.out.println("Invalid Input, please enter 1, 2 or 3 ");
	    					 input.nextLine();
	    				}
	    				
	    			} catch(Exception e) {
	    				System.out.println("Invalid Input, please enter an Integer");
	                    valid = false;
	                    input.nextLine();
	    			}
	    		}	
	    		
	    		String dateOfFlight = null;
	    		System.out.println("Please input the date: (example: 2016-10-15-20-30)");
	    	    String userInputDate = "";
	    	    userInputDate = input.next();
	        	int[] date = parseInput(userInputDate);
	        	inputDate.set(date[0], date[1] - 1, date[2], date[3], date[4]);
	        	dateOfFlight = sdf.format(inputDate.getTime());
	        	
	        	FlightRecord fr = new FlightRecord(departure, destination, dateOfFlight);
	        	String departureCity = departure;
	        	try {
	    			System.setSecurityManager(new RMISecurityManager());
	    			DFRSInterface server = (DFRSInterface)Naming.lookup("rmi://localhost:" + ServerInfo.getServerMaps().get(departureCity) + "/" + departureCity); 			
	    			server.addFlightRecord(fr);
	    	       	System.out.println("Suecesfully add or edit the record!");
	    	       	
	    	        now = new Date();
	    	        managerID = manager.getManagerID();
	    	        String operation = "add a flight from " + departureCity + " to " + destination + " at " + dateOfFlight;
	    			new WriteLogFile(sdf.format(now), managerID, operation).writeToLog("src/clients/managerLogs/" + managerID + ".txt");
	    	       	
	    		} catch(Exception e) {
	    			e.printStackTrace();
	    		}
	        	break;
	        	
	        case 2:
	        	try {
	        		departureCity = departure;
	    			System.setSecurityManager(new RMISecurityManager());
	    			DFRSInterface server = (DFRSInterface)Naming.lookup("rmi://localhost:" + ServerInfo.getServerMaps().get(departureCity) + "/" + departureCity); 			
	    			System.out.println(server.getBookedTicketCounts());
	    	       	 
	    			now = new Date();
	    			managerID = manager.getManagerID();
	    			String operation = "count the number of all the flight records";
	    			new WriteLogFile(sdf.format(now), managerID, operation).writeToLog("src/clients/managerLogs/" + managerID + ".txt");
	    			
	    		} catch(Exception e) {
	    			e.printStackTrace();
	    		}
	        	break;
	        	
	        case 3:
	        	System.out.println("Please input the flight record ID(from Json file)");
	        	input = new Scanner(System.in);
	        	String recordID = input.nextLine();

	        	System.out.println("Please choose the destination city:");
	    		showCities();
	    		while(!valid){
	    			try {
	    				int  opt = input.nextInt();
	    				if(opt <= 3 && opt >0) {
	    					valid = true;
	    					
	    					switch(opt){
	    					case 1:
	    						destination="Montreal";
	    						break;
	    					case 2:
	    						destination="Washington";
	    						break;
	    					case 3:
	    						destination="NewDelhi";
	    						break;
	    					
	    					}
	    					if(destination == departure) {
	    						System.out.println("Destination and Departure are the same.");
	    						destination = null;
	    					}
	    				} else {
	    					 valid = false;
	    					 System.out.println("Invalid Input, please enter 1, 2 or 3 ");
	    					 input.nextLine();
	    				}
	    				
	    			} catch(Exception e) {
	    				System.out.println("Invalid Input, please enter an Integer");
	                    valid = false;
	                    input.nextLine();
	    			}
	    		}	
	    		
	    	 dateOfFlight = null;
	    		System.out.println("Please input the date: (example: 2016-10-15-20-30)");
	    	    userInputDate = "";
	    	    userInputDate = input.next();
	        	 date = parseInput(userInputDate);
	        	inputDate.set(date[0], date[1] - 1, date[2], date[3], date[4]);
	        	dateOfFlight = sdf.format(inputDate.getTime());
	        	
	        	 fr = new FlightRecord(departure, destination, dateOfFlight);
	        	 departureCity = departure;
	        	try {
	    			System.setSecurityManager(new RMISecurityManager());
	    			DFRSInterface server = (DFRSInterface)Naming.lookup("rmi://localhost:" + ServerInfo.getServerMaps().get(departureCity) + "/" + departureCity); 			
	    			server.editFlightRecord(fr, recordID);
	    	       	System.out.println("Suecesfully add or edit the record!");
	    	       	
	    	        now = new Date();
	    	        managerID = manager.getManagerID();
	    	        String operation = "add a flight from " + departureCity + " to " + destination + " at " + dateOfFlight;
	    			new WriteLogFile(sdf.format(now), managerID, operation).writeToLog("src/clients/managerLogs/" + managerID + ".txt");
	    	       	
	    		} catch(Exception e) {
	    			e.printStackTrace();
	    		}
	        	break;

	        	
	        case 4:
	        	System.out.println("Have a nice day!");
	        	System.exit(0);	        	
	        }
		}
	}
	
	public void showMenu() {
		System.out.println("*********** Manager Client ***********");
		System.out.println("Please input your ManagerID:");
		String userInput = "";
		Scanner input = new Scanner(System.in);
		while(true)
        {
            Boolean valid = false;
            while(!valid)
            {
            	userInput = input.next();
            	ManagerRecordsList ml = DataIO.sharedInstance().readFromJsonFile("src/clients/managers.json", ManagerRecordsList.class);
                for(Manager m: ml.getManagerList()){
                	if(userInput.equalsIgnoreCase(m.getManagerID())) {
                		manager = m;
                		valid = true;
                		managerControl();
                	}
                	else continue;
                }
                valid = false;
                System.out.println("Can't find a record in DB. Please try again");
                input.nextLine();
            }
        }
	}
	
	
	public static void main(String[] args){
		while(true){
			new ManagerClient().showMenu();
		}
		
	}
	

}
