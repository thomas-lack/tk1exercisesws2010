package tk1.ue13.api;


/**
 * Automatically generated server stub for <code>IMandelApp</code>
 * @see tk1.ue13.api.IMandelApp
 */
public class SrvIMandelApp extends org.mundo.rt.ServerStub
{
  public SrvIMandelApp()
  {
  }
  private static org.mundo.rt.ServerStub _obj;
  public static org.mundo.rt.ServerStub _getObject()
  {
    if (_obj==null)
    {
      _obj=new SrvIMandelApp();
    }
    return _obj;
  }
  public void invoke(Object o, org.mundo.rt.TypedMap m, org.mundo.rt.TypedMap r)
  {
    String n=m.getString("request");
    IMandelApp p=(IMandelApp)o;
    try
    {
      if (n.equals("setMandelImage") && m.getString("ptypes").equals("tk1.ue13.agent.MandelConfig,i["))
      {
        p.setMandelImage((tk1.ue13.agent.MandelConfig)m.getObject("p0"), (int[])m.getObject("p1"));
        return;
      }
      if (n.equals("_getMethods") && m.getString("ptypes").equals(""))
      {
        r.putString("value",
        "v setMandelImage(tk1.ue13.agent.MandelConfig,i[)\n"+
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