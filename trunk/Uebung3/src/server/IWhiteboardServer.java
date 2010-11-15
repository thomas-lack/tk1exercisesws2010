package server;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import client.IWhiteboardClient;

/**
 * TK1 Exercise 3 - server interface
 * 
 * @author Florian Mueller
 */
public interface IWhiteboardServer extends Remote 
{
	public final static String SERVICE_NAME = "Whiteboard"; 
	
	public boolean login(String name, IWhiteboardClient client) throws RemoteException;
	public boolean logout(String name) throws RemoteException;
	public void line(Point start, Point end, String id) throws RemoteException;
	
	public List<String> getAvailableColor() throws RemoteException;
	public boolean bindColorToClient(String id, String color) throws RemoteException;
}
