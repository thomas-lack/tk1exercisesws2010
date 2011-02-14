package tk1.ue13.api;


/**
 * Automatically generated server stub for <code>ILoadBalancer</code>
 * @see tk1.ue13.api.ILoadBalancer
 */
public class SrvILoadBalancer extends org.mundo.rt.ServerStub
{
  public SrvILoadBalancer()
  {
  }
  private static org.mundo.rt.ServerStub _obj;
  public static org.mundo.rt.ServerStub _getObject()
  {
    if (_obj==null)
    {
      _obj=new SrvILoadBalancer();
    }
    return _obj;
  }
  public void invoke(Object o, org.mundo.rt.TypedMap m, org.mundo.rt.TypedMap r)
  {
    String n=m.getString("request");
    ILoadBalancer p=(ILoadBalancer)o;
    try
    {
      if (n.equals("getBestServer") && m.getString("ptypes").equals(""))
      {
        r.putString("value", p.getBestServer());
        return;
      }
      if (n.equals("_getMethods") && m.getString("ptypes").equals(""))
      {
        r.putString("value",
        "s getBestServer()\n"+
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