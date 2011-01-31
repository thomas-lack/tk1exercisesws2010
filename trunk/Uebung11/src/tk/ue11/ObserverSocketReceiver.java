package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ObserverSocketReceiver implements Runnable{
	
	public static interface ServerListener{
		public void onTransaction(String from, String to, double amount);
		public void onMarker(String from, String to);
	}
	
	public static interface ClientListener{
		public void onStartSnapshot(String initiator);
	}
	
	DatagramSocket socket;
	ServerListener serverListener;
	ClientListener clientListener;
	
	public ObserverSocketReceiver(int port, ServerListener listener) throws SocketException {
		if(0 >= port || null == listener)
			throw new IllegalArgumentException();
		
		socket = new DatagramSocket(port);
		this.serverListener = listener;
		this.clientListener = null;
	}
	
	public ObserverSocketReceiver(int port, ClientListener listener) throws SocketException {
		if(0 >= port || null == listener)
			throw new IllegalArgumentException();
		
		socket = new DatagramSocket(port);
		this.clientListener = listener;
		this.serverListener = null;
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
			
				if(null != clientListener){
					if(data[0].equalsIgnoreCase("startsnapshot") && 2 == data.length)
						clientListener.onStartSnapshot(data[1]);
				}
				else if(null != serverListener){
					if(data[0].equalsIgnoreCase("transaction") && 4 == data.length)
						serverListener.onTransaction(
								data[1],
								data[2],
								Double.parseDouble(data[3]));
					else if(data[0].equals("marker") && 3 == data.length)
						serverListener.onMarker(
								data[1],
								data[2]);
				}
			}
		}
	}
}
