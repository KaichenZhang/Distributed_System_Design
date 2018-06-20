package DFRSApp;


/**
* DFRSApp/ServerInterfaceOperations.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从E:/2014MyEclipse/niubi/ProjectTesters/CorbaAss2/DFRSApp.idl
* 2016年11月7日 星期一 下午04时09分40秒 EST
*/

public interface ServerInterfaceOperations 
{
  String bookFlight (String firstName, String lastName, String address, String telephone, String destination, String date, int flightClass);
  String getBookedFlightCounts (int recordType);
  String editRecord (String recordID, String fieldName, String newValue);
  String transferReservation (String passengerID, String currentCity, String otherCity);
} // interface ServerInterfaceOperations
