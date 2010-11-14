package server;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import client.IWhiteboardClient;

/**
 * TK1 Exercise 3 - server interface
 * 
 * @author Florian Müller
 */
public interface IWhiteboardServer extends Remote 
{
	public final static String SERVICE_NAME = "Whiteboard"; 
	
	public boolean login(String name, IWhiteboardClient client) throws RemoteException;
	public boolean logout(String name) throws RemoteException;
	public void line(Point start, Point end, Color color) throws RemoteException;
}
