package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ObserverSocketReceiver implements Runnable{
	
	public static interface ObserverMessageListener{
		public void onTransaction(int fromId, double amount);
		public void onMarker(int fromId);
		public void onAccountChange(int fromId, double ammount);
	}
	
	DatagramSocket socket;
	ObserverMessageListener listener;
	
	public ObserverSocketReceiver(int port, ObserverMessageListener listener) throws SocketException {
		if(0 >= port || null == listener)
			throw new IllegalArgumentException();
		
		socket = new DatagramSocket(port);
		this.listener = listener;
	}
	
	@Override
	public void run() {
		byte[] buffer = new byte[512];
		
		while (true) {
			synchronized (socket) {
				DatagramPacket packet = new DatagramPacket(buffer, 512);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					System.err.println("Error while try to receive data:" + e.getMessage());
					return;
				}
				
				String[] data = new String(packet.getData()).split(";");
				
				if(data[0].equalsIgnoreCase("transaction") && 3 == data.length)
					listener.onTransaction(
							Integer.parseInt(data[1]), 
							Double.parseDouble(data[2]));
				else if(data[0].equals("marker") && 2 == data.length)
					listener.onMarker(Integer.parseInt(data[1]));
				else if(data[0].equals("accountchange") && 3 == data.length)
					listener.onAccountChange(
							Integer.parseInt(data[1]), 
							Double.parseDouble(data[2]));
			}
		}
	}
}
