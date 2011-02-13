package tk.ue13.server;


/**
 * Automatically generated distributed object class for <code>IMandelCalcServer</code>.
 * @see tk.ue13.server.IMandelCalcServer
 */
public class DoIMandelCalcServer extends org.mundo.rt.DoObject implements tk.ue13.server.IMandelCalcServer
{
  public DoIMandelCalcServer()
  {
  }
  public DoIMandelCalcServer(org.mundo.rt.Session session, Object obj) throws org.mundo.rt.RMCException
  {
    _bind(session, obj);
  }
  public DoIMandelCalcServer(org.mundo.rt.Channel channel) throws org.mundo.rt.RMCException
  {
    _setPublisher(channel.getSession().publish(channel.getZone(), channel.getName()));
  }
  public DoIMandelCalcServer(org.mundo.rt.DoObject o)
  {
    _assign(o);
  }
  public org.mundo.rt.ServerStub _getServerStub()
  {
    return SrvIMandelCalcServer._getObject();
  }
  public static DoIMandelCalcServer _of(org.mundo.rt.Session session, Object obj)
  {
    DoIMandelCalcServer cs=(DoIMandelCalcServer)_getDoObject(session, DoIMandelCalcServer.class, obj);
    if (cs==null)
    {
      cs=new DoIMandelCalcServer(session, obj);
      _putDoObject(session, obj, cs);
    }
    return cs;
  }
  public static DoIMandelCalcServer _of(org.mundo.rt.Service s)
  {
    return _of(s.getSession(), s);
  }
  public String _getInterfaceName()
  {
    return "tk.ue13.server.IMandelCalcServer";
  }
  public static IMandelCalcServer _localObject(IMandelCalcServer obj)
  {
    if (obj instanceof org.mundo.rt.DoObject)
    {
      return (IMandelCalcServer)((org.mundo.rt.DoObject)obj)._getLocalObject();
    }
    else
    {
      return obj;
    }
  }
  public tk.ue13.MandelConfig calculateMandelImage(tk.ue13.MandelConfig p0)
  {
    if (localObj!=null) 
    {
      return ((tk.ue13.server.IMandelCalcServer)localObj).calculateMandelImage(p0);
    }
    org.mundo.rt.AsyncCall call=calculateMandelImage(p0, SYNC);
    return (tk.ue13.MandelConfig)call.getObj();
  }
  public org.mundo.rt.AsyncCall calculateMandelImage(tk.ue13.MandelConfig p0, Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "tk.ue13.MandelConfig");
    m.putString("rtype", "tk.ue13.MandelConfig");
    m.putObject("p0", p0);
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk.ue13.server.IMandelCalcServer", "calculateMandelImage", m);
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