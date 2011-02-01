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
			int ownPort, int observerPort, int channel1Port, 
			int channel2Port, double initialBalance) throws IOException {
		
		rand = new Random(System.currentTimeMillis());
		
	   // Use 'account' plus last digit of the port as channel name
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
      
      // set initial account balance
      setCurrentBalance(initialBalance);
	}
	
	@Override
	public synchronized void run() 
	{
		while (true)
		{
		   randomDelay();
		   
		   if (currentBalance > 0)
	      {
	         int tmpBalance = (int) currentBalance;
		      int transferAmount = rand.nextInt(tmpBalance) + 1;
	         
	         if (transferAmount <= currentBalance)
	         {
	            removeFromCurrentBalance(transferAmount);
	            
	            if (rand.nextBoolean())
	            {
	               ichichan.sendTransaction(name, transferAmount);
	            }
	            else
	            {
	               nichan.sendTransaction(name, transferAmount);
	            }
	         }
	      }
		}
	};
		
	public void setCurrentBalance(double balance)
	{
	   currentBalance = balance;
	   System.out.println(name + " Balance set to " + currentBalance);
	}
	
	public double getCurrentBalance()
	{
	   return currentBalance;
	}
	
	public void addToCurrentBalance(double amount)
	{
	   currentBalance += amount;
	   System.out.println(name + " Balance added: " + amount + " // new Balance: " + currentBalance);
	}
	
	public void removeFromCurrentBalance(double amount)
	{
	   currentBalance -= amount;
	   System.out.println(name + " Balance removed: " + amount + " // new Balance: " + currentBalance);
	}
	
	/**
    * Produce a delay between 1s and 2s 
    */
   public void randomDelay()
   {
      try 
      {
         Thread.sleep(Math.abs((rand.nextInt() + 1000) % 2000));
      } 
      catch (InterruptedException e) 
      {
         // ignore
      }
   }
	
	@Override
	public void onTransaction(String from, String to, String amount) {
	   if (to.equals(name))
	   {
	      addToCurrentBalance(Double.parseDouble(amount));
	   }
	}

	@Override
	public void onMarker(String from, String to) {
	}

	@Override
	public void onStartSnapshot() {
	}
}
