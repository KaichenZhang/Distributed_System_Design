package DFRSApp;


/**
* DFRSApp/_ServerInterfaceStub.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从E:/2014MyEclipse/niubi/ProjectTesters/CorbaAss2/DFRSApp.idl
* 2016年11月7日 星期一 下午04时09分40秒 EST
*/

public class _ServerInterfaceStub extends org.omg.CORBA.portable.ObjectImpl implements DFRSApp.ServerInterface
{

  public String bookFlight (String firstName, String lastName, String address, String telephone, String destination, String date, int flightClass)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("bookFlight", true);
                $out.write_string (firstName);
                $out.write_string (lastName);
                $out.write_string (address);
                $out.write_string (telephone);
                $out.write_string (destination);
                $out.write_string (date);
                $out.write_long (flightClass);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return bookFlight (firstName, lastName, address, telephone, destination, date, flightClass        );
            } finally {
                _releaseReply ($in);
            }
  } // bookFlight

  public String getBookedFlightCounts (int recordType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getBookedFlightCounts", true);
                $out.write_long (recordType);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getBookedFlightCounts (recordType        );
            } finally {
                _releaseReply ($in);
            }
  } // getBookedFlightCounts

  public String editRecord (String recordID, String fieldName, String newValue)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("editRecord", true);
                $out.write_string (recordID);
                $out.write_string (fieldName);
                $out.write_string (newValue);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return editRecord (recordID, fieldName, newValue        );
            } finally {
                _releaseReply ($in);
            }
  } // editRecord

  public String transferReservation (String passengerID, String currentCity, String otherCity)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("transferReservation", true);
                $out.write_string (passengerID);
                $out.write_string (currentCity);
                $out.write_string (otherCity);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return transferReservation (passengerID, currentCity, otherCity        );
            } finally {
                _releaseReply ($in);
            }
  } // transferReservation

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:DFRSApp/ServerInterface:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ServerInterfaceStub
