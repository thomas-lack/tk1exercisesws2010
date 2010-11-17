package server;

import java.awt.Color;
import java.awt.Point;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import client.IWhiteboardClient;

/**
 * TK1 Exercise 3 - implementation of RMI server
 * (controller in MVC concept)
 * 
 * @author Thomas Lack
 */
public class WhiteboardServer extends UnicastRemoteObject implements 
		IWhiteboardServer, LineDataListModel.ILineDataListener{
	
   private static final long serialVersionUID = 845348169309409680L;
   HashMap<String, IWhiteboardClient> registeredClients = new HashMap<String, IWhiteboardClient>();
   
   LinkedList<String> availaleColorsList = null;
   HashMap<String, Color> colorMap = null;
   HashMap<String, String> clientColorBindMap = null;
   
   LineDataListModel lineDataListModel = null;
   
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
      
      lineDataListModel = new LineDataListModel();
      lineDataListModel.registerListener(this);
   }
   
   @Override
   public void line(Point start, Point end, String id) throws RemoteException
   {
	  lineDataListModel.addElement(
			  new LineData(
					  start, 
					  end, 
					  colorMap.get(clientColorBindMap.get(id))));
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
      if (!registeredClients.containsKey(name))
         return false;
      
      // Unbind color
      if(clientColorBindMap.containsKey(name)){
	      availaleColorsList.add(clientColorBindMap.get(name));
	      clientColorBindMap.remove(name);
      }
      
      System.out.println("Server: Client " + name + " logging out.");
      registeredClients.remove(name);
      
      // No client connected -> clear data
      if(0 == registeredClients.size())
    	  lineDataListModel.removeAll();
      
      return true;
   }

	@Override
	public boolean bindColorToClient(String id, String color) {
		if(availaleColorsList.contains(color)){
			System.out.println("Server: Bind " + color + " to " + id);
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

	@Override
	public void onDataChanged(LineData lineData) {
		for (IWhiteboardClient client: registeredClients.values()) {
			try {
				client.receiveLine(
						lineData.start, 
						lineData.end, 
						lineData.color);
			} catch (RemoteException e) {
				System.err.println("Server: Problem with client " + client.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void requestRedraw(String id) {
		System.out.println(
				"Server: Client requests stored lines to redraw them");
		IWhiteboardClient client = registeredClients.get(id);
		Collection<LineData> lineDataCollection = 
			lineDataListModel.getAllElements();
	      for (LineData lineData : lineDataCollection){
	    	  try {
					client.receiveLine(
							lineData.start, 
							lineData.end, 
							lineData.color);
				} catch (RemoteException e) {
					System.err.println(
							"Server: Problem with client " + 
							client.toString());
					e.printStackTrace();
				}
	      }
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
			System.out.println("Server: WhiteboardServer bound");
		} catch (RemoteException e)
		{
			System.err.println("Server: WhiteboardServer exception:");
			e.printStackTrace();
			System.exit(1);
		} catch (MalformedURLException e)
		{
			System.exit(1);
			e.printStackTrace();
		}
	}
}
