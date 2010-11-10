package server;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import client.IWhiteboardClient;

public class WhiteboardServer implements IWhiteboardServer{

	@Override
	public boolean bindColor(IWhiteboardClient client, Color color) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Color> getAvailableColors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean register(String name, IWhiteboardClient client) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendLine(IWhiteboardClient client, Point start, Point end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean unregister(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
