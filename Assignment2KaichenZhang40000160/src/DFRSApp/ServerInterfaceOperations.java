package DFRSApp;


/**
* DFRSApp/ServerInterfaceOperations.java .
* ��IDL-to-Java ������ (����ֲ), �汾 "3.2"����
* ��E:/2014MyEclipse/niubi/ProjectTesters/CorbaAss2/DFRSApp.idl
* 2016��11��7�� ����һ ����04ʱ09��40�� EST
*/

public interface ServerInterfaceOperations 
{
  String bookFlight (String firstName, String lastName, String address, String telephone, String destination, String date, int flightClass);
  String getBookedFlightCounts (int recordType);
  String editRecord (String recordID, String fieldName, String newValue);
  String transferReservation (String passengerID, String currentCity, String otherCity);
} // interface ServerInterfaceOperations
