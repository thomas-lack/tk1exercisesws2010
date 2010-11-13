package server;

import java.awt.Color;
import java.awt.Point;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import client.IWhiteboardClient;

public class WhiteboardServer extends UnicastRemoteObject implements IWhiteboardServer{
   private static final long serialVersionUID = 845348169309409680L;
   HashMap<String, IWhiteboardClient> registeredClients = new HashMap<String, IWhiteboardClient>();
   
   public WhiteboardServer() throws RemoteException
   {
      super();
   }
   
   @Override
   public boolean bindColor(IWhiteboardClient client, Color color) throws RemoteException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public List<Color> getAvailableColors() throws RemoteException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void line(Point start, Point end, Color color) throws RemoteException
   {
      for (Iterator<String> it = registeredClients.keySet().iterator();it.hasNext();)
      {
         IWhiteboardClient client = registeredClients.get(it.next());
         client.receiveLine(start, end, color);
      }
   }

   @Override
   public boolean login(String name, IWhiteboardClient client) throws RemoteException
   {
      if (registeredClients.containsKey(name))
      {
         return false;
      }
            
      System.out.println("Server: Client " + name + " logging in.");
      registeredClients.put(name, client);
      return true;
   }

   @Override
   public boolean logout(String name) throws RemoteException
   {
      if (registeredClients.containsKey(name))
      {
         return false;
      }
      
      System.out.println("Server: Client " + name + " logging out.");
      registeredClients.remove(name);
      return true;
   }

   public static void main(String[] args)
   {
      try
      {
         String name = "Whiteboard";
         IWhiteboardServer server = new WhiteboardServer();
         LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
         Naming.rebind(name, server);
         System.out.println("WhiteboardServer bound");
      } catch (RemoteException e)
      {
         System.err.println("WhiteboardServer exception:");
         e.printStackTrace();
      } catch (MalformedURLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
