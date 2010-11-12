package client;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.IWhiteboardServer;

public class WhiteboardClient extends UnicastRemoteObject implements IWhiteboardClient
{
   private static final long serialVersionUID = -227030971339158140L;
   Color currentColor = Color.BLACK; // standard color
   IWhiteboardServer server;
   
   protected WhiteboardClient(IWhiteboardServer server) throws RemoteException
   {
      super();
      
      // save server object
      this.server = server;
      
      // render according client gui
      WhiteboardGUI gui = new WhiteboardGUI(this);
      gui.setVisible(true);
   }
   
	@Override
	public void receiveLine(Point start, Point end, Color color) throws RemoteException
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * changes the color currently in use
	 * @param color
	 */
	public void changeColor(Color color)
	{
	   currentColor = color;
	}
	
	/**
    * @param args the command line arguments
    */
   public static void main(String args[]) 
   {
      try
      {
         String name = "Whiteboard";
         Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
         //IWhiteboardServer server = (IWhiteboardServer) registry.lookup(name);
         IWhiteboardServer server = (IWhiteboardServer) Naming.lookup("rmi://127.0.0.1/" + name);
         //IWhiteboardServer server = (IWhiteboardServer) Naming.lookup("//127.0.0.1/" + name);
         WhiteboardClient client = new WhiteboardClient(server);
         server.login(serialVersionUID + "", client);
         
      } catch (Exception e)
      {
         System.err.println("WhiteboardClient Exception:");
         e.printStackTrace();
      }
   }
}
