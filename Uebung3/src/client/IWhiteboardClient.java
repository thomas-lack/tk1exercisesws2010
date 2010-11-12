package client;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWhiteboardClient extends Remote {
	public void receiveLine(Point start, Point end, Color color) throws RemoteException;
}
