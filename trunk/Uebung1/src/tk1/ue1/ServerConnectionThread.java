package tk1.ue1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * 
 * @author Florian Mueller
 */
public class ServerConnectionThread extends Thread {	
	private Socket clientSocket;
	private Random random = null;

	/**
	 * Constructor to initialize a client/server-connection 
	 * with the NT-Protocol in a separate Thread
	 * 
	 * @param clientSocket Client socket
	 */
	public ServerConnectionThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.random = new  Random(System.nanoTime());
		
		// make this thread to a daemon
		setDaemon(true);
	}
	
	/**
	 * Handle TCP/IP-Connection in a thread
	 */
	@Override
	public void run() {
		DataInputStream in = null;
		DataOutputStream out = null;

		// is the client socket valid and connected
		if(null == clientSocket && clientSocket.isConnected())
			return;
		
		try {
			// get I/O-Streams
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			disconnect();
			return;
		}
		
		while(!isInterrupted()) {
			try {
				// receive client timestamp
				long t0 = in.readLong(); // read client timestamp		
				sleep(getRandomDelay()); // artificial network-delay
				long t1 = System.currentTimeMillis(); // receiving time
				
				// write answer
				out.writeLong(t0);
				out.writeLong(t1);
				out.writeLong(System.currentTimeMillis()); // sending time
				out.flush();
			} catch (Exception e) {
				System.out.println(clientSocket.getInetAddress() + 
						" disconnected");
				disconnect();
				return;
			}
		}
		
		disconnect();
	}
	
	/**
	 * Disconnect the TCP/IP-Connection to the client
	 */
	public void disconnect(){
		if(null != clientSocket){
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns a random number between 70 and 140
	 * 
	 * @return a random number
	 */
	public long getRandomDelay(){
		return 70L + (Math.abs(random.nextLong()) % 71L);
	}
}
