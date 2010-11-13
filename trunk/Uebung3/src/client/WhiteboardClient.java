package client;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import server.IWhiteboardServer;

/**
 * TK1 Exercise 3 - Implementation of whiteboard client
 * somewhat the model in the MVC pattern and a communication layer to the controller (server)
 * 
 * @author Thomas Lack
 */
public class WhiteboardClient extends UnicastRemoteObject implements IWhiteboardClient
{
   private static final long serialVersionUID = -227030971339158140L;
   private Color currentColor = Color.BLACK; // standard color
   private IWhiteboardServer server;
   private String clientID = UUID.randomUUID().toString();
   private WhiteboardGUI gui = null;
   private enum State { CONNECTED, DISCONNECTED }
   private State state = State.DISCONNECTED;
   
   protected WhiteboardClient(IWhiteboardServer server) throws RemoteException
   {
      super();
      
      // save server object
      this.setServer(server);
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
	   if (state == State.CONNECTED)
	   {
	      server.line(start, end, this.getCurrentColor());
	   }
	}
	
	/**
	 * render according client gui
	 */
	public void renderGUI()
	{
      if (gui == null)
      {
         gui = new WhiteboardGUI(this);
      }
      gui.setVisible(true);
	}
	
	/**
    * changes the color currently in use
    * @param color
    */
	public void setCurrentColor(Color color)
   {
      this.currentColor = color;
   }
	
	/**
	 * getter for currently set color
	 * 
	 * @return current color set by the user
	 */
   public Color getCurrentColor()
   {
      return currentColor;
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
   
   /**
    * setter for the RMI server
    * 
    * @param server
    */
   public void setServer(IWhiteboardServer server)
   {
      this.server = server;
   }
   
   /**
    * getter for the server object
    * 
    * @return RMI server
    */
   public IWhiteboardServer getServer()
   {
      return server;
   }
   
   /**
    * log client into server
    * 
    * @return boolean
    */
   public boolean connect()
   {
      try
      {
         if (server.login(getClientID(), this))
         {
            state = State.CONNECTED;
            return true;
         }
      } catch (RemoteException e)
      {
         e.printStackTrace();
      }
      return false;
   }
   
   /**
    * logout client from server
    */
   public boolean disconnect()
   {
      try
      {
         if (server.logout(getClientID()))
         {
            state = State.DISCONNECTED;
            return true;
         }
      } catch (RemoteException e)
      {
         e.printStackTrace();
      }
      return false;
   }
   
	/**
    * @param args the command line arguments
    */
   public static void main(String args[]) 
   {
      try
      {
         String name = "Whiteboard";
         LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
         IWhiteboardServer server = (IWhiteboardServer) Naming.lookup("rmi://127.0.0.1/" + name);
         WhiteboardClient client = new WhiteboardClient(server);
         if (client.connect())
         {
            client.renderGUI();
         }
      } catch (Exception e)
      {
         System.err.println("WhiteboardClient Exception:");
         e.printStackTrace();
      }
   }
}
