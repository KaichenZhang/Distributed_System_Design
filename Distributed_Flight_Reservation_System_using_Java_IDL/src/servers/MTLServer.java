package servers;

import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import DFRSApp.ServerInterface;
import DFRSApp.ServerInterfaceHelper;
import Records.FlightRecord;

public class MTLServer extends FlightServer {
	static final int UDP_PORT = 2001;
	
	public MTLServer(){
		super();
		super.city = "MTL";
		super.createLogFile();
		try {
			this.exportServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createFlightRecord(FlightRecord f){
		f.addFlight("MTL", "WDC", "2016/11/01", 10, 30, 100);
		f.addFlight("MTL", "NDL", "2016/11/01", 10, 30, 100);
		f.addFlight("MTL", "WDC", "2016/11/02", 20, 40, 200);
		f.addFlight("MTL", "NDL", "2016/11/02", 20, 40, 200);
		f.addFlight("MTL", "WDC", "2016/11/03", 30, 50, 300);
		f.addFlight("MTL", "NDL", "2016/11/03", 30, 50, 300);
	}
	
	public static void main(String args[]){
		try{
			final MTLServer server = new MTLServer();
			Properties props = new Properties();
		    props.put("org.omg.CORBA.ORBInitialPort", "1050");    
		    props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1"); 
		    ORB orb = ORB.init(args, props);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			server.setORB(orb);
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(server);
			ServerInterface href = ServerInterfaceHelper.narrow(ref);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			NameComponent path[] = ncRef.to_name("MTL");
			ncRef.rebind(path, href);
			
			
			System.out.println("Montreal Server is Running");
			server.writeLog("-----------------------Initial Flights------------------------- ");
			server.writeLog(server.flight_record.toString());
			server.writeLog("-----------------------------Server----------------------------");
			new Thread(new Runnable() {
				@Override
				public void run() {
					server.UDPServer(UDP_PORT);
				}
			}).start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
