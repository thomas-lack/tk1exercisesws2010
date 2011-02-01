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
		public void onTransaction(String from, String to, String amount);
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
				
				if(data[0].equalsIgnoreCase("transaction") && 4 == data.length)
				{
				   listener.onTransaction(data[1], data[2], data[3]);
				}
					
				else if(data[0].equals("marker") && 3 == data.length)
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
