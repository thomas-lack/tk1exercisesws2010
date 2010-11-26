package client;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import server.IWhiteboardServer;

/**
 * TK1 Exercise 3 - Implementation of whiteboard client
 * somewhat the model in the MVC pattern and a communication layer to the controller (server)
 * 
 * @author Thomas Lack, Florian Mueller
 */
public class WhiteboardClient extends UnicastRemoteObject implements IWhiteboardClient
{
	private static final long serialVersionUID = -227030971339158140L;
	private IWhiteboardServer server;
	private String clientID = UUID.randomUUID().toString();
	private WhiteboardGUI gui = null;
	private enum State { CONNECTED, DISCONNECTED }
	private State state = State.DISCONNECTED;
   
   	public WhiteboardClient() throws RemoteException {
		super();
		gui = new WhiteboardGUI(this);
	}
   
   	@Override 	
   	public void receiveLine(Point start, Point end, Color color) throws RemoteException
	{
		gui.drawLine(start,end,color);
	}
	
	/**
	 * sends information about a user drawn line to the server
	 * 
	 * @param start
	 * @param end
	 * @throws RemoteException
	 */
   	public void sendLine(Point start, Point end) throws RemoteException
	{
	   if (isConnected())
	      server.line(start, end, getClientID());
	}
	
	/**
	 * render according client gui
	 */
	public void renderGUI()
	{
      gui.setVisible(true);
	}
	
   /**
    * getter for the client ID
    * 
    * @return this client objects UUID
    */
   public String getClientID()
   {
      return clientID;
   }
   
   public IWhiteboardServer getServer(){
	   return server;
   }
   
   public boolean connect(String addresss, int port){
	   try {
		   LocateRegistry.getRegistry(port);
		   server = (IWhiteboardServer) Naming.lookup(
				   "rmi://" + addresss + "/" + IWhiteboardServer.SERVICE_NAME);
		   server.login(getClientID(), this);
		   state = State.CONNECTED;
		   return true;
	   } catch (Exception e) {
		   e.printStackTrace();
	   } 
	   
	   return false;
   }
   
   public boolean isConnected(){
	   return null != server && State.CONNECTED == state;
   }
   
   public void disconnect(){
	   try {
		   server.logout(getClientID());
		   server = null;
		   state = State.DISCONNECTED;
	   } catch (RemoteException e) {
		   e.printStackTrace();
	   }
   }
   
	/**
    * @param args the command line arguments
    */
   public static void main(String args[]) 
   {	  
		try {
			WhiteboardClient client = new WhiteboardClient();
			client.renderGUI();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
   }
}
