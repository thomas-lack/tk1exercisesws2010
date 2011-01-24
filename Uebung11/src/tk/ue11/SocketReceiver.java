package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SocketReceiver implements Runnable {

	DatagramSocket socket;
	
	public SocketReceiver(int port) throws SocketException {
		socket = new DatagramSocket(port);
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
				
				System.out.println("receive: " + new String(packet.getData()));
			}
		}
	}
}
