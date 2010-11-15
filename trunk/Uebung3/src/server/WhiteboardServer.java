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
import java.util.LinkedList;
import java.util.List;

import client.IWhiteboardClient;

/**
 * TK1 Exercise 3 - implementation of RMI server
 * (controller in MVC concept)
 * 
 * @author Thomas Lack
 */
public class WhiteboardServer extends UnicastRemoteObject implements IWhiteboardServer{
   private static final long serialVersionUID = 845348169309409680L;
   HashMap<String, IWhiteboardClient> registeredClients = new HashMap<String, IWhiteboardClient>();
   
   LinkedList<String> availaleColorsList = null;
   HashMap<String, Color> colorMap = null;
   HashMap<String, String> clientColorBindMap = null;
   
   public WhiteboardServer() throws RemoteException
   {
      super();
      
      colorMap = new HashMap<String, Color>();
      colorMap.put("Black", Color.BLACK);
      colorMap.put("Red", Color.RED);
      colorMap.put("Blue", Color.BLUE);
      colorMap.put("Green", Color.GREEN);
      colorMap.put("Orange", Color.ORANGE);
      colorMap.put("Magenta", Color.MAGENTA);
      colorMap.put("Gray", Color.GRAY);
      colorMap.put("Yellow", Color.YELLOW);
      
      availaleColorsList = new LinkedList<String>(colorMap.keySet());
      clientColorBindMap = new HashMap<String, String>();
   }
   
   @Override
   public void line(Point start, Point end, String id) throws RemoteException
   {
      for (Iterator<String> it = registeredClients.keySet().iterator();it.hasNext();)
      {
         IWhiteboardClient client = registeredClients.get(it.next());
         client.receiveLine(
        		 start, 
        		 end,
        		 colorMap.get(clientColorBindMap.get(id)));
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
      if (!registeredClients.containsKey(name)){
         return false;
      }
      
      System.out.println("Server: Client " + name + " logging out.");
      if(clientColorBindMap.containsKey(name)){
	      availaleColorsList.add(clientColorBindMap.get(name));
	      clientColorBindMap.remove(name);
      }
      
      registeredClients.remove(name);
      return true;
   }
   
   /**
    * @param args
    */
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
         e.printStackTrace();
      }
   }

	@Override
	public boolean bindColorToClient(String id, String color) {
		if(availaleColorsList.contains(color)){
			System.out.println("Bind " + color + " to " + id);
			availaleColorsList.remove(color);
			clientColorBindMap.put(id, color);
			return true;
		}
		
		return false;
	}

	@Override
	public List<String> getAvailableColor() {
		return availaleColorsList;
	}
}
