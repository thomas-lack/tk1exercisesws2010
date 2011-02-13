package tk.ue13.agent;


/**
 * Automatically generated distributed object class for <code>IMandelAgent</code>.
 * @see tk.ue13.agent.IMandelAgent
 */
public class DoIMandelAgent extends org.mundo.rt.DoObject implements tk.ue13.agent.IMandelAgent
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
    return "tk.ue13.agent.IMandelAgent";
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
  public void run(String p0, String p1, tk.ue13.MandelConfig p2)
  {
    if (localObj!=null) 
    {
      ((tk.ue13.agent.IMandelAgent)localObj).run(p0, p1, p2);
      return;
    }
    run(p0, p1, p2, SYNC);
  }
  public org.mundo.rt.AsyncCall run(String p0, String p1, tk.ue13.MandelConfig p2, Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "s,s,tk.ue13.MandelConfig");
    m.putString("rtype", "");
    m.putString("p0", p0);
    m.putString("p1", p1);
    m.putObject("p2", p2);
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk.ue13.agent.IMandelAgent", "run", m);
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
  
  public void atLoadBalancer()
  {
    if (localObj!=null) 
    {
      ((tk.ue13.agent.IMandelAgent)localObj).atLoadBalancer();
      return;
    }
    atLoadBalancer(SYNC);
  }
  public org.mundo.rt.AsyncCall atLoadBalancer(Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "");
    m.putString("rtype", "");
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk.ue13.agent.IMandelAgent", "atLoadBalancer", m);
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
  
  public void atCalcServer()
  {
    if (localObj!=null) 
    {
      ((tk.ue13.agent.IMandelAgent)localObj).atCalcServer();
      return;
    }
    atCalcServer(SYNC);
  }
  public org.mundo.rt.AsyncCall atCalcServer(Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "");
    m.putString("rtype", "");
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk.ue13.agent.IMandelAgent", "atCalcServer", m);
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
  
  public void atClient()
  {
    if (localObj!=null) 
    {
      ((tk.ue13.agent.IMandelAgent)localObj).atClient();
      return;
    }
    atClient(SYNC);
  }
  public org.mundo.rt.AsyncCall atClient(Options opt) 
  {
    org.mundo.rt.TypedMap m=new org.mundo.rt.TypedMap();
    m.putString("ptypes", "");
    m.putString("rtype", "");
    org.mundo.rt.AsyncCall call=new org.mundo.rt.AsyncCall(this, "tk.ue13.agent.IMandelAgent", "atClient", m);
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