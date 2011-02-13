package tk.ue13;


/**
 * Automatically generated server stub for <code>IMandelClient</code>
 * @see tk.ue13.IMandelClient
 */
public class SrvIMandelClient extends org.mundo.rt.ServerStub
{
  public SrvIMandelClient()
  {
  }
  private static org.mundo.rt.ServerStub _obj;
  public static org.mundo.rt.ServerStub _getObject()
  {
    if (_obj==null)
    {
      _obj=new SrvIMandelClient();
    }
    return _obj;
  }
  public void invoke(Object o, org.mundo.rt.TypedMap m, org.mundo.rt.TypedMap r)
  {
    String n=m.getString("request");
    IMandelClient p=(IMandelClient)o;
    try
    {
      if (n.equals("calculationFinished") && m.getString("ptypes").equals("MandelConfig"))
      {
        p.calculationFinished((MandelConfig)m.getObject("p0"));
        return;
      }
      if (n.equals("_getMethods") && m.getString("ptypes").equals(""))
      {
        r.putString("value",
        "v calculationFinished(MandelConfig)\n"+
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