package tk1.ue13.api;


/**
 * Automatically generated distributed object class for <code>IMandelApp</code>.
 * @see tk1.ue13.api.IMandelApp
 */
public class DoIMandelApp extends org.mundo.rt.DoObject implements tk1.ue13.api.IMandelApp
{
  public DoIMandelApp()
  {
  }
  public DoIMandelApp(org.mundo.rt.Session session, Object obj) throws org.mundo.rt.RMCException
  {
    _bind(session, obj);
  }
  public DoIMandelApp(org.mundo.rt.Channel channel) throws org.mundo.rt.RMCException
  {
    _setPublisher(channel.getSession().publish(channel.getZone(), channel.getName()));
  }
  public DoIMandelApp(org.mundo.rt.DoObject o)
  {
    _assign(o);
  }
  public org.mundo.rt.ServerStub _getServerStub()
  {
    return SrvIMandelApp._getObject();
  }
  public static DoIMandelApp _of(org.mundo.rt.Session session, Object obj)
  {
    DoIMandelApp cs=(DoIMandelApp)_getDoObject(session, DoIMandelApp.class, obj);
    if (cs==null)
    {
      cs=new DoIMandelApp(session, obj);
      _putDoObject(session, obj, cs);
    }
    return cs;
  }
  public static DoIMandelApp _of(org.mundo.rt.Service s)
  {
    return _of(s.getSession(), s);
  }
  public String _getInterfaceName()
  {
    return "tk1.ue13.api.IMandelApp";
  }
  public static IMandelApp _localObject(IMandelApp obj)
  {
    if (obj instanceof org.mundo.rt.DoObject)
    {
      return (IMandelApp)((org.mundo.rt.DoObject)obj)._getLocalObject();
    }
    else
    {
      return obj;
    }
  }
  public void setMandelImage(tk1.ue13.agent.MandelConfig p0, int[] p1)
  {
    if (localObj!=null) 
    {
      ((tk1.ue13.api.IMandelApp)localObj).setMandelImage(p0, p1);
      return;
    }
    setMandelImage(p0, p1, SYNC);
  }
  public org.mundo.rt.AsyncCall setMandelImage(tk1.ue13.agent.MandelConfig p0, int[] p1, Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "tk1.ue13.agent.MandelConfig,i[");
    m.putString("rtype", "");
    m.putObject("p0", p0);
    m.putObject("p1", p1);
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk1.ue13.api.IMandelApp", "setMandelImage", m);
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