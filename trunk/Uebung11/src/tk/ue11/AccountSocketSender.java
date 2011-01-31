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
public class AccountSocketSender implements Runnable {
	private InetAddress accountAddress;
	private int accountPort;
	
	private DatagramSocket socket;
	private LinkedBlockingQueue<String> messageQueue;
	private Random rand;
	private ObserverSocketSender observer;
	
	/**
	 * Contructor with a UDP-Socket to send data over it
	 * 
	 * @param socket
	 * @throws SocketException 
	 */
	public AccountSocketSender(
			InetAddress accountAddress, int accountPort, ObserverSocketSender observer) 
			throws SocketException {
		this.accountAddress = accountAddress;
		this.accountPort = accountPort;
		this.observer = observer;
		
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
	public void sendTransaction(double amount){
		synchronized (messageQueue) {
			try {
				messageQueue.put(
						"transaction;" + amount);
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
		byte[] buffer = new byte[1024];
		
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
						
						// send account packet
						buffer = message.getBytes();
						DatagramPacket packet = new DatagramPacket(
								buffer, 
								buffer.length,
								accountAddress,
								accountPort);
						socket.send(packet);
						
						observer.sendStringMessage(message);
												
						randomDelay();
					} catch (IOException e) {
						System.err.println("Error while sending message: " + message);
					}
				}
			}
		}
	}
}
