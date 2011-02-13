package tk.ue13.server;


/**
 * Automatically generated server stub for <code>IMandelCalcServer</code>
 * @see tk.ue13.server.IMandelCalcServer
 */
public class SrvIMandelCalcServer extends org.mundo.rt.ServerStub
{
  public SrvIMandelCalcServer()
  {
  }
  private static org.mundo.rt.ServerStub _obj;
  public static org.mundo.rt.ServerStub _getObject()
  {
    if (_obj==null)
    {
      _obj=new SrvIMandelCalcServer();
    }
    return _obj;
  }
  public void invoke(Object o, org.mundo.rt.TypedMap m, org.mundo.rt.TypedMap r)
  {
    String n=m.getString("request");
    IMandelCalcServer p=(IMandelCalcServer)o;
    try
    {
      if (n.equals("calculateMandelImage") && m.getString("ptypes").equals("tk.ue13.MandelConfig"))
      {
        r.putObject("value", p.calculateMandelImage((tk.ue13.MandelConfig)m.getObject("p0")));
        return;
      }
      if (n.equals("_getMethods") && m.getString("ptypes").equals(""))
      {
        r.putString("value",
        "tk.ue13.MandelConfig calculateMandelImage(tk.ue13.MandelConfig)\n"+
        "");
        return;
      }
    }
    catch(Exception x)
    {
      exceptionOccured(x, o, m, r);
    }
  }
}