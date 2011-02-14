package tk1.ue13.api;


/**
 * Automatically generated server stub for <code>IMandelAgent</code>
 * @see tk1.ue13.api.IMandelAgent
 */
public class SrvIMandelAgent extends org.mundo.rt.ServerStub
{
  public SrvIMandelAgent()
  {
  }
  private static org.mundo.rt.ServerStub _obj;
  public static org.mundo.rt.ServerStub _getObject()
  {
    if (_obj==null)
    {
      _obj=new SrvIMandelAgent();
    }
    return _obj;
  }
  public void invoke(Object o, org.mundo.rt.TypedMap m, org.mundo.rt.TypedMap r)
  {
    String n=m.getString("request");
    IMandelAgent p=(IMandelAgent)o;
    try
    {
      if (n.equals("run") && m.getString("ptypes").equals("tk1.ue13.agent.MandelConfig"))
      {
        p.run((tk1.ue13.agent.MandelConfig)m.getObject("p0"));
        return;
      }
      if (n.equals("_getMethods") && m.getString("ptypes").equals(""))
      {
        r.putString("value",
        "v run(tk1.ue13.agent.MandelConfig)\n"+
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