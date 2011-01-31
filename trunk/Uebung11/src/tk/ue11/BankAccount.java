package tk.ue11;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import tk.ue11.AccountSocketReceiver.TransactionListener;

public class BankAccount implements Runnable, TransactionListener{
	private Random rand;
	
	private String name;
	private double currentBalance;
	private Snapshot snapshot;
	private boolean doSnapshot = false;
	
	private AccountSocketReceiver receiver;
	private AccountSocketSender ichichan;
	private AccountSocketSender nichan;
	private AccountObserverSender observerSender;
	
	public BankAccount( 
			int ownPort, int observerPort,
			int channel1Port, int channel2Port) throws IOException {
		
		// Use 'account' plus first digit of the port as channel name
		name = "account" + (ownPort % 10);
		String chan1 = "account" + (channel1Port % 10);
		String chan2 = "account" + (channel2Port % 10);
		
		receiver = new AccountSocketReceiver(ownPort, this);
		
		observerSender = new AccountObserverSender(
				InetAddress.getLocalHost(), 
				observerPort);
		
		ichichan = new AccountSocketSender(
				chan1, 
				InetAddress.getLocalHost(), 
				channel1Port, 
				observerSender);
		
		nichan = new AccountSocketSender(
				chan2, 
				InetAddress.getLocalHost(), 
				channel2Port, 
				observerSender);
		
		new Thread(receiver).start();
		new Thread(ichichan).start();
		new Thread(nichan).start();
	}
	
	@Override
	public void run() {
		
	};

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if(6 != args.length){
			System.out.println("usage: BankAccount <name> <observer port> " +
					"<account1 name> <account1 port> " +
					"<account2 name> <account2 port>");
			System.exit(-1);
		}
		else{
			
		}
	}

	@Override
	public void onTransaction(String from, String to, double amount) {
	}

	@Override
	public void onMarker(String from, String to) {
	}

	@Override
	public void onStartSnapshot() {
	}
}
