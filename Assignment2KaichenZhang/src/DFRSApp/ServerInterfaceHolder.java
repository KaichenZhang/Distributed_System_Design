package DFRSApp;

/**
* DFRSApp/ServerInterfaceHolder.java .
* ��IDL-to-Java ������ (����ֲ), �汾 "3.2"����
* ��E:/2014MyEclipse/niubi/ProjectTesters/CorbaAss2/DFRSApp.idl
* 2016��11��7�� ����һ ����04ʱ09��40�� EST
*/

public final class ServerInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public DFRSApp.ServerInterface value = null;

  public ServerInterfaceHolder ()
  {
  }

  public ServerInterfaceHolder (DFRSApp.ServerInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DFRSApp.ServerInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DFRSApp.ServerInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DFRSApp.ServerInterfaceHelper.type ();
  }

}
