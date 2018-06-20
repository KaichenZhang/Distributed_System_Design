package servers;

import javax.jws.WebService;

import Records.Flight;
import Records.FlightRecord;
import Records.Passenger;
import Records.PassengerList;
import Records.PassengerRecord;

@WebService(endpointInterface = "servers.FlightServerInterface",
        serviceName = "MTLService",
        portName = "MTLServerPort",
        targetNamespace = "http://mtlservice")

public class MTLServer extends FlightServer {

    static final int UDP_PORT = 10000;

    public MTLServer() {
        super();
        super.city = "MTL";
        super.RMIPort = 1096;
        super.createLogFile();

        try {
            this.exportServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        run();
    }

    public void run() {
        Thread thread = null;
        try {
            System.out.println("MTL Server Running");
            writeLog("Flights Available: ");
            writeLog("-----------------------Initial Flights------------------------- ");
            writeLog("Flight No. Form --> To\t\tDate/Time\tSeatings");
            writeLog(flight_record.toString());
            writeLog("-----------------------------Server----------------------------");

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    createServer(UDP_PORT);
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createFlightRecord(FlightRecord f) {
//		f.addFlight("MTL", "WDC", "2016/11/01", 10, 30, 100);
//		f.addFlight("MTL", "NDL", "2016/11/01", 10, 30, 100);
//		f.addFlight("MTL", "WDC", "2016/11/02", 20, 40, 200);
//		f.addFlight("MTL", "NDL", "2016/11/02", 20, 40, 200);
//		f.addFlight("MTL", "WDC", "2016/11/03", 30, 50, 300);
//		f.addFlight("MTL", "NDL", "2016/11/03", 30, 50, 300);
		f.addFlight("MTL", "WDC", "2016/11/07", 30, 50, 300);
    }

}
