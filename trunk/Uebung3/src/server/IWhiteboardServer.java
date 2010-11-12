package server;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import client.IWhiteboardClient;

public interface IWhiteboardServer extends Remote 
{
	public boolean login(String name, IWhiteboardClient client) throws RemoteException;
	public boolean logout(String name) throws RemoteException;
	
	public List<Color> getAvailableColors() throws RemoteException;
	public boolean bindColor(IWhiteboardClient client, Color color) throws RemoteException;
	
	public void line(Point start, Point end, Color color) throws RemoteException;
}
