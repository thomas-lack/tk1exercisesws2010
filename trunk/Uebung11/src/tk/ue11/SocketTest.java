package tk.ue11;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class SocketTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Random rand = new Random(System.currentTimeMillis());
		
		FIFODelayedSender sender = new FIFODelayedSender(InetAddress.getLocalHost(), 12345);
		SocketReceiver receiver = new SocketReceiver(12345);
		
		new Thread(sender).start();
		new Thread(receiver).start();
		
		for (int i = 0; i < 20; i++) {
			sender.sendTransaction(rand.nextInt() % 10000);
		}
	}
}
