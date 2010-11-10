package client;

import java.awt.Color;
import java.awt.Point;
import java.rmi.Remote;

public interface IWhiteboardClient extends Remote {
	public void drawLine(Point start, Point end, Color color);
}
