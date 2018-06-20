package client;


import java.util.Random;

import Records.RandomString;

public class MultiThreadTest {
	public static void main(String[] args) {
		
		
		System.out.println("Processing Multithreading passenger test:");
		for(int i=0;i<30;i++){
			multiClient(i);
		}
	}
	
	//test multithreading client
		public static void multiClient(final int clientId){
			final Client mcl = new Client( new RandomString().getRandomString(2));
			try{
			  new Thread(new Runnable(){
				public void run(){
					int location = 0;
					Random random = new Random();
					int lct=random.nextInt(3);				
					switch (lct) {
					//random server,and consider as departure city
					case 0:
						location=1;								
						break;
					case 1:
						location=2;		
						break;
					case 2:
						location=3;	
						break;
					}			

					//each passenger client can book n times
					int times=2;
					while(times>0){
						System.out.println("Client"+clientId+"  is booking");
						times--;
						String firstName=new RandomString().getRandomString(5);
						String lastName=new RandomString().getRandomString(3);
						String address=new RandomString().getRandomString(8);
						String phoneNumber=new RandomString().getRandomStringNum(10);													
						try {
							int destinationCity = 0;
							int flightClass = 0;
							if(location==1){
								switch(random.nextInt(2)){
								case 0:
									destinationCity=2;
									break;
								case 1:
									destinationCity=3;
									break;
								}
							}else if(location ==2){
								switch(random.nextInt(2)){
								case 0:
									destinationCity=1;
									break;
								case 1:
									destinationCity=3;
									break;
								}
							}else{
								switch(random.nextInt(2)){
								case 0:
									destinationCity=1;
									break;
								case 1:
									destinationCity=2;
									break;
								}
							}
							
							
							switch(random.nextInt(3)){
							case 0:
								flightClass=1;
								break;
							case 1:
								flightClass=2;
								break;
							case 2:
								flightClass=3;
								break;
							}
												
							String dateOfFlight = "";
							switch(random.nextInt(3)){
							case 0:
								dateOfFlight="2016/11/01";
								break;
							case 1:
								dateOfFlight="2016/11/02";
								break;
							case 2:
								dateOfFlight="2016/11/03";
								break;

							}
							
							mcl.bookPassengerFlight(location, destinationCity, firstName, lastName, address, phoneNumber, dateOfFlight, flightClass);

							
							
							} catch(Exception e) {
								e.printStackTrace();
							}					
						
						}
				}
			  }).start();
			}
			catch (Exception e){
			  System.out.println("Thread Exception" + e.getMessage());
			}
			
		}

}
