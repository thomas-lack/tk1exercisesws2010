package tk.ue13.agent;


/**
 * Automatically generated server stub for <code>IMandelAgent</code>
 * @see tk.ue13.agent.IMandelAgent
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
      if (n.equals("run") && m.getString("ptypes").equals("s,s,tk.ue13.MandelConfig"))
      {
        p.run(m.getString("p0"), m.getString("p1"), (tk.ue13.MandelConfig)m.getObject("p2"));
        return;
      }
      if (n.equals("atLoadBalancer") && m.getString("ptypes").equals(""))
      {
        p.atLoadBalancer();
        return;
      }
      if (n.equals("atCalcServer") && m.getString("ptypes").equals(""))
      {
        p.atCalcServer();
        return;
      }
      if (n.equals("atClient") && m.getString("ptypes").equals(""))
      {
        p.atClient();
        return;
      }
      if (n.equals("_getMethods") && m.getString("ptypes").equals(""))
      {
        r.putString("value",
        "v run(s,s,tk.ue13.MandelConfig)\n"+
        "v atLoadBalancer()\n"+
        "v atCalcServer()\n"+
        "v atClient()\n"+
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