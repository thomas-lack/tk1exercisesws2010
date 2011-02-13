package tk.ue13;


/**
 * Automatically generated distributed object class for <code>IMandelClient</code>.
 * @see tk.ue13.IMandelClient
 */
public class DoIMandelClient extends org.mundo.rt.DoObject implements tk.ue13.IMandelClient
{
  public DoIMandelClient()
  {
  }
  public DoIMandelClient(org.mundo.rt.Session session, Object obj) throws org.mundo.rt.RMCException
  {
    _bind(session, obj);
  }
  public DoIMandelClient(org.mundo.rt.Channel channel) throws org.mundo.rt.RMCException
  {
    _setPublisher(channel.getSession().publish(channel.getZone(), channel.getName()));
  }
  public DoIMandelClient(org.mundo.rt.DoObject o)
  {
    _assign(o);
  }
  public org.mundo.rt.ServerStub _getServerStub()
  {
    return SrvIMandelClient._getObject();
  }
  public static DoIMandelClient _of(org.mundo.rt.Session session, Object obj)
  {
    DoIMandelClient cs=(DoIMandelClient)_getDoObject(session, DoIMandelClient.class, obj);
    if (cs==null)
    {
      cs=new DoIMandelClient(session, obj);
      _putDoObject(session, obj, cs);
    }
    return cs;
  }
  public static DoIMandelClient _of(org.mundo.rt.Service s)
  {
    return _of(s.getSession(), s);
  }
  public String _getInterfaceName()
  {
    return "tk.ue13.IMandelClient";
  }
  public static IMandelClient _localObject(IMandelClient obj)
  {
    if (obj instanceof org.mundo.rt.DoObject)
    {
      return (IMandelClient)((org.mundo.rt.DoObject)obj)._getLocalObject();
    }
    else
    {
      return obj;
    }
  }
  public void calculationFinished(MandelConfig p0)
  {
    if (localObj!=null) 
    {
      ((tk.ue13.IMandelClient)localObj).calculationFinished(p0);
      return;
    }
    calculationFinished(p0, SYNC);
  }
  public org.mundo.rt.AsyncCall calculationFinished(MandelConfig p0, Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "MandelConfig");
    m.putString("rtype", "");
    m.putObject("p0", p0);
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk.ue13.IMandelClient", "calculationFinished", m);
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