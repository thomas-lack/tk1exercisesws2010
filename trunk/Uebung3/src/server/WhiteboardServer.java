package server;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

import client.IWhiteboardClient;

public class WhiteboardServer extends UnicastRemoteObject implements IWhiteboardServer{
   private static final long serialVersionUID = 845348169309409680L;
   Vector<IWhiteboardClient> registeredClients = new Vector<IWhiteboardClient>();
   
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
      // TODO Auto-generated method stub
      
   }

   @Override
   public boolean login(String name, IWhiteboardClient client) throws RemoteException
   {
      System.out.println("Server: Client " + client + " logging in.");
      return false;
   }

   @Override
   public boolean logout(String name) throws RemoteException
   {
      // TODO Auto-generated method stub
      return false;
   }

   public static void main(String[] args)
   {
      /*
      if (System.getSecurityManager() == null)
      {
         System.setSecurityManager(new RMISecurityManager());
      }
      */
      
      try
      {
         String name = "Whiteboard";
         IWhiteboardServer server = new WhiteboardServer();
         //IWhiteboardServer stub = (IWhiteboardServer) UnicastRemoteObject.exportObject(server, 0);
         //Registry registry = LocateRegistry.getRegistry();
         //Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
         //registry.rebind(name, stub);
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
