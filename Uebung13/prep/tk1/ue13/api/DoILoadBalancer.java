package tk1.ue13.api;


/**
 * Automatically generated distributed object class for <code>ILoadBalancer</code>.
 * @see tk1.ue13.api.ILoadBalancer
 */
public class DoILoadBalancer extends org.mundo.rt.DoObject implements tk1.ue13.api.ILoadBalancer
{
  public DoILoadBalancer()
  {
  }
  public DoILoadBalancer(org.mundo.rt.Session session, Object obj) throws org.mundo.rt.RMCException
  {
    _bind(session, obj);
  }
  public DoILoadBalancer(org.mundo.rt.Channel channel) throws org.mundo.rt.RMCException
  {
    _setPublisher(channel.getSession().publish(channel.getZone(), channel.getName()));
  }
  public DoILoadBalancer(org.mundo.rt.DoObject o)
  {
    _assign(o);
  }
  public org.mundo.rt.ServerStub _getServerStub()
  {
    return SrvILoadBalancer._getObject();
  }
  public static DoILoadBalancer _of(org.mundo.rt.Session session, Object obj)
  {
    DoILoadBalancer cs=(DoILoadBalancer)_getDoObject(session, DoILoadBalancer.class, obj);
    if (cs==null)
    {
      cs=new DoILoadBalancer(session, obj);
      _putDoObject(session, obj, cs);
    }
    return cs;
  }
  public static DoILoadBalancer _of(org.mundo.rt.Service s)
  {
    return _of(s.getSession(), s);
  }
  public String _getInterfaceName()
  {
    return "tk1.ue13.api.ILoadBalancer";
  }
  public static ILoadBalancer _localObject(ILoadBalancer obj)
  {
    if (obj instanceof org.mundo.rt.DoObject)
    {
      return (ILoadBalancer)((org.mundo.rt.DoObject)obj)._getLocalObject();
    }
    else
    {
      return obj;
    }
  }
  public String getBestServer()
  {
    if (localObj!=null) 
    {
      return ((tk1.ue13.api.ILoadBalancer)localObj).getBestServer();
    }
    org.mundo.rt.AsyncCall call=getBestServer(SYNC);
    return call.getMap().getString("value");
  }
  public org.mundo.rt.AsyncCall getBestServer(Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "");
    m.putString("rtype", "s");
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk1.ue13.api.ILoadBalancer", "getBestServer", m);
    if (opt==ONEWAY)
    {
      call.invokeOneWay();
    }
    else if (opt==SYNC || opt==ASYNC)
    {
      call.invoke();
    }
    if (opt==SYNC)
    {
      try
      {
        if (!call.waitForReply())
        {
          throw call.getException();
        }
      }
      catch(RuntimeException x)
      {
        throw x;
      }
      catch(Exception x)
      {
        throw new org.mundo.rt.RMCException("unexpected exception", x);
      }
    }
    return call;
  }
  
}