package tk.ue11;

import java.io.IOException;
import java.net.InetAddress;
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
		      int transferAmount = Math.abs(rand.nextInt(tmpBalance));
	         
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
//	   System.out.println(name + " Balance set to " + currentBalance);
	}
	
	public double getCurrentBalance()
	{
	   return currentBalance;
	}
	
	public void addToCurrentBalance(double amount)
	{
//		System.out.println(name + " has " + currentBalance);
	   currentBalance += amount;
//	   System.out.println(name + " Balance added: " + amount + " // new Balance: " + currentBalance);
//	   System.out.println();
	}
	
	public void removeFromCurrentBalance(double amount)
	{
//		System.out.println(name + " has " + currentBalance);
	   currentBalance -= amount;
//	   System.out.println(name + " Balance removed: " + amount + " // new Balance: " + currentBalance);
	}
	
	public AccountSocketReceiver getSocketReceiver()
	{
	   return receiver;
	}
	
	/**
    * Produce a delay between 3s and 6s
    * 
    * (slightly faster than the delay implemented in the AccountSocketSender 
    * class, so that the MessageQueue is filled up by the time) 
    */
   public void randomDelay()
   {
      try 
      {
         int sleepTime = 3000 + Math.abs(rand.nextInt() % 3000);
         Thread.sleep(sleepTime);
      } 
      catch (InterruptedException e) 
      {
         // ignore
      }
   }
	
	@Override
	public void onTransaction(String from, String to, int amount) {
	   if (to.equals(name))
	   {
	      addToCurrentBalance(amount);
	   }
	}

	@Override
	public void onMarker(String from, String to) {
	}

	@Override
	public void onStartSnapshot() {
	   System.out.println(name + " received start snapshot message.");
	}
}
