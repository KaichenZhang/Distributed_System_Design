package clients;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import auxiliary.RandomString;
import records.Passenger;
import records.PassengerRecord;
import serverInterface.DFRSInterface;

public class PassengerClient {
	
	private Passenger passenger;
	private String departureCity;
	private PassengerRecord passengerRecord;
	private void showCities(){
		System.out.println("1. Montreal");
		System.out.println("2. Washington");
		System.out.println("3. New Delhi");
	}

	public void passengerControl() {
		while(true) {

			
			boolean valid = false;
			Scanner input = new Scanner(System.in);
			System.out.println("Please input your first name:");
			String firstName = input.nextLine();
			System.out.println("Please input your last name:");
			String lastName = input.nextLine();
			System.out.println("Please input your address");
			String address = input.nextLine();
			System.out.println("Please input your phone number");
			String phoneNumber = input.nextLine();
			passenger = new Passenger(firstName, lastName, address, phoneNumber);
			
			System.out.println("Please select the departure city: ");
			showCities();
			while(!valid){
				try {
					int opt = input.nextInt();
					if(opt <= 3 && opt >0) {
						valid = true;
						switch(opt){
    					case 1:
    						departureCity="Montreal";
    						break;
    					case 2:
    						departureCity="Washington";
    						break;
    					case 3:
    						departureCity="NewDelhi";
    						break;
    					
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
			System.out.println("Please select the destination: ");
			showCities();
			valid = false;
			String destinationCity = null;
			while(!valid){
				try {
					int opt = input.nextInt();
					if(opt <= 3 && opt >0) {
						valid = true;
							switch(opt){
	    					case 1:
	    						destinationCity="Montreal";
	    						break;
	    					case 2:
	    						destinationCity="Washington";
	    						break;
	    					case 3:
	    						destinationCity="NewDelhi";
	    						break;
	    					
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
			System.out.println("Please select the class you want book: ");
			System.out.println("1.first");
			System.out.println("2.business");
			System.out.println("3.economy");
			valid = false;
			String flightClass = null;
			while(!valid){
				try {
					int opt = input.nextInt();
					if(opt <= 3 && opt >0) {
						valid = true;
							switch(opt){
	    					case 1:
	    						flightClass="first";
	    						break;
	    					case 2:
	    						flightClass="business";
	    						break;
	    					case 3:
	    						flightClass="economy";
	    						break;
	    					
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
			 
			try {
			String dateOfFlight = "";
			String departure = departureCity;	
			System.setSecurityManager(new RMISecurityManager());
			DFRSInterface server = (DFRSInterface)Naming.lookup("rmi://localhost:" + ServerInfo.getServerMaps().get(departure) + "/" + departure);
			ArrayList<String> dates = server.getAvailableDates(destinationCity);
			if(dates != null) {
				System.out.println("This is the availble dates for this flight, please select the time: ");
				for(int i = 1; i < dates.size() + 1; i++) {
					System.out.println(i + ". " + dates.get(i - 1));
				}
				Scanner reader = new Scanner(System.in); 
				int n = reader.nextInt(); 
				dateOfFlight = dates.get(n - 1);
				passengerRecord = new PassengerRecord(passenger, destinationCity, flightClass, dateOfFlight);
				server.bookFlight(passengerRecord);
				System.out.println("Successfully booked a flight!");
				System.out.println("Have a nice day!");
				showMenu();
			} else {
				System.out.println("This is the no availble dates for this flight, please correct your input.");
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		
		}
	}
	
	
	
	
	
	
	
	//test multithreading client
	public void multiClient(final int clientId){
		try
		{
		  new Thread(new Runnable()
		  {
			public void run()
			{
				String location="";
				Random random = new Random();
				int lct=random.nextInt(3);
				DFRSInterface server=null;
								
				try {
					switch (lct) {
					//random server,and consider as departure city
					case 0:
						location="Montreal";	
						server=(DFRSInterface)Naming.lookup("rmi://localhost:" + ServerInfo.getServerMaps().get(location) + "/" + location);
						break;
					case 1:
						location="Washington";	
						server=(DFRSInterface)Naming.lookup("rmi://localhost:" + ServerInfo.getServerMaps().get(location) + "/" + location);
						break;
					case 2:
						location="NewDelhi";	
						server=(DFRSInterface)Naming.lookup("rmi://localhost:" + ServerInfo.getServerMaps().get(location) + "/" + location);
						break;

					}
					
					Passenger passenger;

					//each passenger client can book n times
					int times=1;
					while(times>0){
						System.out.println("Client"+clientId+"  is booking");
						times--;
						String firstName=new RandomString().getRandomString(5);
						String lastName=new RandomString().getRandomString(3);
						String address=new RandomString().getRandomString(8);
						String phoneNumber=new RandomString().getRandomStringNum(10);
						passenger = new Passenger(firstName, lastName, address, phoneNumber);
											
						//int operation=random.nextInt(7);
						try {
							String destinationCity=null;
							String flightClass=null;
							
							switch(random.nextInt(3)){
	    					case 0:
	    						destinationCity="Montreal";
	    						break;
	    					case 1:
	    						destinationCity="Washington";
	    						break;
	    					case 2:
	    						destinationCity="NewDelhi";
	    						break;
	    					
	    					}
							
							switch(random.nextInt(3)){
	    					case 0:
	    						flightClass="economy";
	    						break;
	    					case 1:
	    						flightClass="business";
	    						break;
	    					case 2:
	    						flightClass="fit";
	    						break;
	    					}
							
							
							String dateOfFlight = "";
							System.setSecurityManager(new RMISecurityManager());
							ArrayList<String> dates = server.getAvailableDates(destinationCity);
							if(dates != null) {
								
								int opt=random.nextInt(dates.size());//0 to k-1, k = dates.size()
								dateOfFlight = dates.get(opt);
								passengerRecord = new PassengerRecord(passenger, destinationCity, flightClass, dateOfFlight);
								server.bookFlight(passengerRecord);
								System.out.println("Client"+clientId+":Successfully book a flight!");
								break;
							} else {
								System.out.println("Client"+clientId+"'s booking is cancled:not such flight.");
							}
							} catch(Exception e) {
								e.printStackTrace();
							}
							
						
						}
						
						
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		  }).start();
		}
		catch (Exception e)
		{
		  System.out.println("Thread Exception" + e.getMessage());
		}
		
	}
	
	public void showMenu() {
		System.out.println("***********  Passenger ***********");
		System.out.println("Please input corresponding integer: ");
		System.out.println("1. Multithreading passenger test");
		System.out.println("2. Passenger control test");
		System.out.println("3. Exit");
		int userInput = 0;
		Scanner input = new Scanner(System.in);
		userInput = input.nextInt();
		switch(userInput) {
		case 1:
			System.out.println("Processing Multithreading passenger test:");
			for(int i=0;i<30;i++){
				new PassengerClient().multiClient(i);
			}
			break;
			
		case 2:
			System.out.println("Processing passenger control test:");
			new PassengerClient().passengerControl();
			break;
			
		case 3:
			System.out.println("Have a nice day!");
			System.exit(0);

		}
		
	}
	
	
	public static void main(String[] args){
		while(true){
			new PassengerClient().showMenu();
		}	
	}
	

}
