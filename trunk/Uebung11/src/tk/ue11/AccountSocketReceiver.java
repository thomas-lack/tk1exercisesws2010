package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class AccountSocketReceiver implements Runnable {

	public static interface TransactionListener{
		public void onTransaction(double amount);
		public void onMarker();
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
				
				if(data[0].equalsIgnoreCase("transaction") && 2 == data.length)
					listener.onTransaction(Double.parseDouble(data[1]));
				else if(data[0].equals("marker"))
					listener.onMarker();
			}
		}
	}
}
