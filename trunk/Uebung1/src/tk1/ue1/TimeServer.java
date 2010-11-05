package tk1.ue1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Simple TimeServer for the NT-Protocol
 * 
 * @author Florian Mueller
 */
public class TimeServer extends Thread{
	/**
	 * Timeout when no client connect
	 */
	public static final int SOCKET_TIMEOUT = 30000;
	
	private ServerSocket serverSocket = null;
	
	public TimeServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(SOCKET_TIMEOUT);
		} catch (IOException e) {
			System.out.println("Serversocket creation failed");
			e.printStackTrace();
			serverSocket = null;
		}
	}

	@Override
	public void run() {
		// check that we have a valid socket
		if(null == serverSocket && !serverSocket.isClosed())
			return;
			
		System.out.println("start server");
		
		while(!isInterrupted()){
			try {
				// waiting 4 clients
				Socket clientSocket = serverSocket.accept();
				
				System.out.println("client connected from " + 
						clientSocket.getInetAddress());
				
				// Start ConnectionThread for each client
				new ServerConnectionThread(clientSocket).start();
			} catch(SocketTimeoutException e){
				interrupt();
				System.out.println("Server timed out");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("shutdown server");
		close();
	}
	
	/**
	 * Close Serversocket
	 */
	public void close(){
		try {
			if(null != serverSocket)
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int port = -1;

		if(1 != args.length){
			System.out.println("usage: server <port>");
			System.exit(1);
		}
		
		try{
			port = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e){
			System.out.println(args[0] + " is no valid port");
			System.exit(1);
		}		
		
		new TimeServer(port).start();
	}
}
