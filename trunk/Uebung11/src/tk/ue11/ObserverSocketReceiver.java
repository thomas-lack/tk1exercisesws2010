package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ObserverSocketReceiver implements Runnable{
	
	public static interface MessageListener
	{
		public void onTransaction(String from, String to, String amount);
		public void onMarker(String from, String to);
	}
	
	DatagramSocket socket;
	MessageListener listener;
	
	public ObserverSocketReceiver(int port, MessageListener listener) throws SocketException 
	{
		if(0 >= port || null == listener)
			throw new IllegalArgumentException();
		
		socket = new DatagramSocket(port);
		this.listener = listener;
	}
	
	@Override
	public void run() 
	{
		byte[] buffer = new byte[512];
		
		while (true) 
		{
			synchronized (socket) 
			{
				DatagramPacket packet = new DatagramPacket(buffer, 512);
				try 
				{
					socket.receive(packet);
				} 
				catch (IOException e) 
				{
					System.err.println("ObserverSocketReceiver : Error while try to receive data:" + e.getMessage());
					return;
				}
				
				String[] data = new String(packet.getData()).split(";");
			
				if(data[0].equalsIgnoreCase("transaction"))
				{
				   listener.onTransaction(data[1], data[2], data[3]);
				}
				else if(data[0].equals("marker"))
				{
				   listener.onMarker(data[1], data[2]);
				}
					
			}
		}
	}
}
