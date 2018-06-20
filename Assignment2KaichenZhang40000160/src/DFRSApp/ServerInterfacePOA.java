package DFRSApp;


/**
* DFRSApp/ServerInterfacePOA.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从E:/2014MyEclipse/niubi/ProjectTesters/CorbaAss2/DFRSApp.idl
* 2016年11月7日 星期一 下午04时09分40秒 EST
*/

public abstract class ServerInterfacePOA extends org.omg.PortableServer.Servant
 implements DFRSApp.ServerInterfaceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("bookFlight", new java.lang.Integer (0));
    _methods.put ("getBookedFlightCounts", new java.lang.Integer (1));
    _methods.put ("editRecord", new java.lang.Integer (2));
    _methods.put ("transferReservation", new java.lang.Integer (3));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // DFRSApp/ServerInterface/bookFlight
       {
         String firstName = in.read_string ();
         String lastName = in.read_string ();
         String address = in.read_string ();
         String telephone = in.read_string ();
         String destination = in.read_string ();
         String date = in.read_string ();
         int flightClass = in.read_long ();
         String $result = null;
         $result = this.bookFlight (firstName, lastName, address, telephone, destination, date, flightClass);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // DFRSApp/ServerInterface/getBookedFlightCounts
       {
         int recordType = in.read_long ();
         String $result = null;
         $result = this.getBookedFlightCounts (recordType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // DFRSApp/ServerInterface/editRecord
       {
         String recordID = in.read_string ();
         String fieldName = in.read_string ();
         String newValue = in.read_string ();
         String $result = null;
         $result = this.editRecord (recordID, fieldName, newValue);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // DFRSApp/ServerInterface/transferReservation
       {
         String passengerID = in.read_string ();
         String currentCity = in.read_string ();
         String otherCity = in.read_string ();
         String $result = null;
         $result = this.transferReservation (passengerID, currentCity, otherCity);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:DFRSApp/ServerInterface:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ServerInterface _this() 
  {
    return ServerInterfaceHelper.narrow(
    super._this_object());
  }

  public ServerInterface _this(org.omg.CORBA.ORB orb) 
  {
    return ServerInterfaceHelper.narrow(
    super._this_object(orb));
  }


} // class ServerInterfacePOA
