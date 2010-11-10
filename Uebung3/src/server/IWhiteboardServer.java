package server;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Remote;
import java.util.List;

import client.IWhiteboardClient;

public interface IWhiteboardServer extends Remote {
	public boolean register(String name, IWhiteboardClient client);
	public boolean unregister(String name);
	
	public List<Color> getAvailableColors();
	public boolean bindColor(IWhiteboardClient client, Color color);
	
	public void sendLine(IWhiteboardClient client, Point start, Point end);
}
