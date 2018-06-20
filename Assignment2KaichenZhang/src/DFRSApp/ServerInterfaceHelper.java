package DFRSApp;


/**
* DFRSApp/ServerInterfaceHelper.java .
* ��IDL-to-Java ������ (����ֲ), �汾 "3.2"����
* ��E:/2014MyEclipse/niubi/ProjectTesters/CorbaAss2/DFRSApp.idl
* 2016��11��7�� ����һ ����04ʱ09��40�� EST
*/

abstract public class ServerInterfaceHelper
{
  private static String  _id = "IDL:DFRSApp/ServerInterface:1.0";

  public static void insert (org.omg.CORBA.Any a, DFRSApp.ServerInterface that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static DFRSApp.ServerInterface extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (DFRSApp.ServerInterfaceHelper.id (), "ServerInterface");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static DFRSApp.ServerInterface read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ServerInterfaceStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, DFRSApp.ServerInterface value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static DFRSApp.ServerInterface narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof DFRSApp.ServerInterface)
      return (DFRSApp.ServerInterface)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      DFRSApp._ServerInterfaceStub stub = new DFRSApp._ServerInterfaceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static DFRSApp.ServerInterface unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof DFRSApp.ServerInterface)
      return (DFRSApp.ServerInterface)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      DFRSApp._ServerInterfaceStub stub = new DFRSApp._ServerInterfaceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
