package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Class to receive messages from another account or the observer 
 */
public class AccountSocketReceiver implements Runnable {
	
	/**
	 * Interface for message callback
	 */
	public static interface TransactionListener{
		public void onTransaction(String from, String to, int amount);
		public void onMarker(String from, String to);
		public void onStartSnapshot();
	}
	
	DatagramSocket socket;
	TransactionListener listener;
	
	public AccountSocketReceiver(int port, TransactionListener listener) throws SocketException {
		if(0 >= port || null == listener)
			throw new IllegalArgumentException();
		
		socket = new DatagramSocket(port);
		this.listener = listener;
	}
	
	@Override
	public void run() {
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
					System.err.println("Error while try to receive data:" + e.getMessage());
					return;
				}
				
				String[] data = new String(packet.getData()).split(";");
				
				if(data[0].equalsIgnoreCase("transaction"))
				{
				   listener.onTransaction(data[1], data[2], (int)Double.parseDouble(data[3]));
				}
					
				else if(data[0].equalsIgnoreCase("marker"))
				{
				   listener.onMarker(data[1], data[2]);
				}
				else if(data[0].equalsIgnoreCase("startsnapshot"))
				{
				   listener.onStartSnapshot();
				}
			}
		}
	}
}
