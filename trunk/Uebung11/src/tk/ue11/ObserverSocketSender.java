package tk.ue11;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class ObserverSocketSender {
	private InetAddress address;
	private int port;
	
	private DatagramSocket socket;
	
	public ObserverSocketSender(InetAddress address, int port) throws SocketException {
		this.address = address;
		this.port = port;
		
		socket = new DatagramSocket();
	}
	
	public void sendStringMessage(String message) throws IOException{
		synchronized (socket) {
			byte[] buffer = new byte[1024];
			buffer = message.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					address, port);
			socket.send(packet);
		}
	}
	
	public void sendAccoundChangeMessage(double value) throws IOException{
		synchronized (socket) {
			byte[] buffer = new byte[1024];
			buffer = ("accountchange;" + value).getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					address, port);
			socket.send(packet);
		}
	}
}
