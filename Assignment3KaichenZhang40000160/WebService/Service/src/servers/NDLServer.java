package servers;

import javax.jws.WebService;

import Records.Flight;
import Records.FlightRecord;
import Records.Passenger;
import Records.PassengerList;
import Records.PassengerRecord;

@WebService(endpointInterface = "servers.FlightServerInterface",
        serviceName = "NDLService",
        portName = "NDLServerPort",
        targetNamespace = "http://ndlservice")

public class NDLServer extends FlightServer {

    static final int UDP_PORT = 30000;

    public NDLServer() {
        super();
        super.city = "NDL";
        super.RMIPort = 1097;
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
            System.out.println("NDL Server Running");
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
//		f.addFlight("NDL", "WDC", "2016/11/01", 10, 30, 100);
//		f.addFlight("NDL", "MTL", "2016/11/01", 10, 30, 100);
//		f.addFlight("NDL", "WDC", "2016/11/02", 20, 40, 200);
//		f.addFlight("NDL", "MTL", "2016/11/02", 20, 40, 200);
//		f.addFlight("NDL", "WDC", "2016/11/03", 30, 50, 300);
//		f.addFlight("NDL", "MTL", "2016/11/03", 30, 50, 300);
		f.addFlight("NDL", "WDC", "2016/11/07", 30, 50, 300);
    }

}
