package tk.ue11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class to send data over an UDP-Socket (between accounts) 
 * with an artificial delay between each data 
 */
public class AccountSocketSender implements Runnable {
	private String to;
	private InetAddress address;
	private int port;
	
	private DatagramSocket socket;
	private LinkedBlockingQueue<String> messageQueue;
	private Random rand;
	private AccountObserverSender observer;
	
	/**
	 * Contructor with a UDP-Socket to send data over it
	 * 
	 * @param socket
	 * @throws SocketException 
	 */
	public AccountSocketSender(
			String to,
			InetAddress accountAddress, 
			int accountPort, 
			AccountObserverSender observer) 
			throws SocketException 
	{
		this.to = to;
	   this.address = accountAddress;
		this.port = accountPort;
		this.observer = observer;
		
		socket = new DatagramSocket();
		messageQueue = new LinkedBlockingQueue<String>();
		rand = new Random(System.currentTimeMillis());
	}
	
	/**
	 * Produce a delay between 6s and 12s 
	 */
	public void randomDelay(){
		try {
			int sleepTime = 6000 + Math.abs(rand.nextInt() % 6000);
//			System.out.println(this.toString() + " sleeps " + sleepTime);
		   Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// ignore
		}
	}
	
	/**
	 * Send a transaction over the socket
	 * @param amount
	 */
	public void sendTransaction(String from, int amount){
		synchronized (messageQueue) {
			try {
				messageQueue.put(
						"transaction;" + from + ";" + to + ";" + amount + ";");
				messageQueue.notify();
				
//				System.out.println("Message posted: " + "transaction;" + from + ";" + to + ";" + amount);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * Send a marker over the socket
	 */
	public void sendMarker(String from){
		synchronized (messageQueue) {
			try {
				messageQueue.put("marker;" + from + ";" + to + ";");
				messageQueue.notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void sendSnapshot(String from, String snapshotMsg)
	{
	   synchronized (messageQueue)
	   {
	      try
         {
            messageQueue.put("snapshot;" + from + ";" + snapshotMsg + ";");
            messageQueue.notify();
         } 
	      catch (InterruptedException e)
         {
            e.printStackTrace();
         }
	      
	   }
	}
	
	@Override
	public void run() 
	{
		byte[] buffer = new byte[1024];
		
		while (true) 
		{			
			synchronized (socket) 
			{	
			   
			   if(0 == messageQueue.size())
			   {
					synchronized (messageQueue) 
					{
						try 
						{
							messageQueue.wait();
						} 
						catch (InterruptedException e) 
						{
							// ignore
						}
					}
				}
				
				String message = messageQueue.poll();
				
				// send observer message
				if(null != message)
				{				   
					try 
					{
						//System.out.println(this.toString() + " generated message: " + message);
					   
						// send account packet
						buffer = message.getBytes();
						DatagramPacket packet = new DatagramPacket(
								buffer, 
								buffer.length,
								address,
								port);
						socket.send(packet);
						
						observer.sendStringMessage(message);
						
						//randomDelay();
					} 
					catch (IOException e) 
					{
						System.err.println("Error while sending message: " + message);
					}
				}
			}
		}
	}
}
