package tk.ue11;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


/**
 * Class to send text-messages direct to the observer
 */
public class AccountObserverSender {
	private InetAddress address;
	private int port;
	
	private DatagramSocket socket;
	
	public AccountObserverSender(InetAddress address, int port) throws SocketException {
		this.address = address;
		this.port = port;
		
		socket = new DatagramSocket();
	}
	
	public void sendStringMessage(String message) throws IOException
	{
		synchronized (socket) 
		{
			byte[] buffer = new byte[1024];
			buffer = message.getBytes();
			DatagramPacket packet = new DatagramPacket(
					buffer, 
					buffer.length,
					address, 
					port);
			socket.send(packet);
		}
	}
}
