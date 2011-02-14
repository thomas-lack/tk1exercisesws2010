package tk1.ue13.api;


/**
 * Automatically generated distributed object class for <code>IMandelAgent</code>.
 * @see tk1.ue13.api.IMandelAgent
 */
public class DoIMandelAgent extends org.mundo.rt.DoObject implements tk1.ue13.api.IMandelAgent
{
  public DoIMandelAgent()
  {
  }
  public DoIMandelAgent(org.mundo.rt.Session session, Object obj) throws org.mundo.rt.RMCException
  {
    _bind(session, obj);
  }
  public DoIMandelAgent(org.mundo.rt.Channel channel) throws org.mundo.rt.RMCException
  {
    _setPublisher(channel.getSession().publish(channel.getZone(), channel.getName()));
  }
  public DoIMandelAgent(org.mundo.rt.DoObject o)
  {
    _assign(o);
  }
  public org.mundo.rt.ServerStub _getServerStub()
  {
    return SrvIMandelAgent._getObject();
  }
  public static DoIMandelAgent _of(org.mundo.rt.Session session, Object obj)
  {
    DoIMandelAgent cs=(DoIMandelAgent)_getDoObject(session, DoIMandelAgent.class, obj);
    if (cs==null)
    {
      cs=new DoIMandelAgent(session, obj);
      _putDoObject(session, obj, cs);
    }
    return cs;
  }
  public static DoIMandelAgent _of(org.mundo.rt.Service s)
  {
    return _of(s.getSession(), s);
  }
  public String _getInterfaceName()
  {
    return "tk1.ue13.api.IMandelAgent";
  }
  public static IMandelAgent _localObject(IMandelAgent obj)
  {
    if (obj instanceof org.mundo.rt.DoObject)
    {
      return (IMandelAgent)((org.mundo.rt.DoObject)obj)._getLocalObject();
    }
    else
    {
      return obj;
    }
  }
  public void run(tk1.ue13.agent.MandelConfig p0)
  {
    if (localObj!=null) 
    {
      ((tk1.ue13.api.IMandelAgent)localObj).run(p0);
      return;
    }
    run(p0, SYNC);
  }
  public org.mundo.rt.AsyncCall run(tk1.ue13.agent.MandelConfig p0, Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "tk1.ue13.agent.MandelConfig");
    m.putString("rtype", "");
    m.putObject("p0", p0);
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk1.ue13.api.IMandelAgent", "run", m);
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