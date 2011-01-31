package tk.ue11;

import java.net.InetAddress;
import java.util.Random;

import tk.ue11.AccountSocketReceiver.TransactionListener;

public class BankAccount implements Runnable{
	private Random rand;
	private AccountSocketReceiver receiver1;
	private AccountSocketSender sender1;
	
	private AccountSocketReceiver receiver2;
	private AccountSocketSender sender2;
	
	private TransactionListener account1Listener = new TransactionListener() {
		@Override
		public void onTransaction(double amount) {
		}
		
		@Override
		public void onMarker() {
		}
	};
	
	private TransactionListener account2Listener = new TransactionListener() {
		@Override
		public void onTransaction(double amount) {
		}
		
		@Override
		public void onMarker() {
		}
	};
	
	public BankAccount(int account1Port, int account2Port, int observerPort) {
	}
	
	@Override
	public void run() {
		
	};

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
	}
}
