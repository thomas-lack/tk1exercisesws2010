package jms_client;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * TK1 Exercise 3 - client interface
 * 
 * @author Florian Müller
 */
public interface IWhiteboardClient extends Remote {
	public void receiveLine(Point start, Point end, Color color) throws RemoteException;
}
