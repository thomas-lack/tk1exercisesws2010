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

public class WhiteboardClient extends UnicastRemoteObject implements IWhiteboardClient
{
   private static final long serialVersionUID = -227030971339158140L;
   private Color currentColor = Color.BLACK; // standard color
   private IWhiteboardServer server;
   private String clientID = UUID.randomUUID().toString();
   private WhiteboardGUI gui = null;
   
   protected WhiteboardClient(IWhiteboardServer server) throws RemoteException
   {
      super();
      
      // save server object
      this.setServer(server);
   }
   
	@Override
	public void receiveLine(Point start, Point end, Color color) throws RemoteException
	{
		((PaintPanel) gui.getPaintPanel()).receiveLine(start,end,color);
	}
	
	public void sendLine(Point start, Point end, Color color) throws RemoteException
	{
	   server.line(start, end, color);
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

   public Color getCurrentColor()
   {
      return currentColor;
   }
	
   public String getClientID()
   {
      return clientID;
   }
   
   public void setServer(IWhiteboardServer server)
   {
      this.server = server;
   }

   public IWhiteboardServer getServer()
   {
      return server;
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
         if (server.login(client.getClientID(), client))
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
