package DFRSApp;

/**
* DFRSApp/ServerInterfaceHolder.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从E:/2014MyEclipse/niubi/ProjectTesters/CorbaAss2/DFRSApp.idl
* 2016年11月7日 星期一 下午04时09分40秒 EST
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
