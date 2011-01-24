package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class to send data over an UDP-Socket 
 * with an artificial delay between each data 
 */
public class FIFODelayedSender implements Runnable {
	
	public static final String MARKER = "marker";

	InetAddress address;
	int port;
	DatagramSocket socket;
	LinkedBlockingQueue<String> messageQueue;
	Random rand;
	
	/**
	 * Contructor with a UDP-Socket to send data over it
	 * 
	 * @param socket
	 * @throws SocketException 
	 */
	public FIFODelayedSender(InetAddress address, int port) throws SocketException {
		this.address = address;
		this.port = port;
		
		
		socket = new DatagramSocket();
		messageQueue = new LinkedBlockingQueue<String>();
		rand = new Random(System.currentTimeMillis());
	}
	
	/**
	 * Produce a delay between 1s and 5s 
	 */
	public void randomDelay(){
		try {
			Thread.sleep(Math.abs((rand.nextInt() + 1000) % 5000));
		} catch (InterruptedException e) {
			// ignore
		}
	}
	
	/**
	 * Send a transaction over the socket
	 * @param amount
	 */
	public void sendTransaction(int amount){
		synchronized (messageQueue) {
			try {
				messageQueue.put("transaction;" + amount);
				messageQueue.notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * Send a marker over the socket
	 */
	public void sendMarker(){
		synchronized (messageQueue) {
			try {
				messageQueue.put("marker");
				messageQueue.notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	@Override
	public void run() {
		byte[] buffer = new byte[512];
		
		while (true) {			
			synchronized (socket) {	
				if(0 == messageQueue.size()){
					synchronized (messageQueue) {
						try {
							messageQueue.wait();
						} catch (InterruptedException e) {
							// ignore
						}
					}
				}
				
				String message = messageQueue.poll();
				
				if(null != message){
					try {
						System.out.println("send: " + message); 
						buffer = message.getBytes();
						DatagramPacket packet = new DatagramPacket(
								buffer, 
								buffer.length,
								address,
								port);
						socket.send(packet);
						randomDelay();
					} catch (IOException e) {
						System.err.println("Error while sending message: " + message);
					}
				}
			}
		}
	}
}
