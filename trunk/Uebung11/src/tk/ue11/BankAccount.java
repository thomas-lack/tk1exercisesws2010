package tk.ue11;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

import tk.ue11.AccountSocketReceiver.TransactionListener;

public class BankAccount implements Runnable, TransactionListener{
	
   private Random rand; 
	
	private String name, chan1, chan2;
	private double currentBalance;
	private Snapshot snapshot = null;
	private boolean doSnapshot = false, haltMessageQueueing = false;
	private boolean recordChan1 = false, recordChan2 = false;
	private int markerCount = 0;
	
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
		chan1 = "account" + (channel1Port % 10);
		chan2 = "account" + (channel2Port % 10);
		
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
      
      // initialize snapshot
      snapshot = new Snapshot(name, chan1, chan2);
	}
	
	@Override
	public synchronized void run() 
	{
		while (true)
		{
		   if (currentBalance > 0 && !haltMessageQueueing)
	      {
		      randomDelay();
		      
		      int tmpBalance = (int) currentBalance;
		      int transferAmount = Math.abs(rand.nextInt(tmpBalance));
	         
	         if (transferAmount <= currentBalance && transferAmount != 0)
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
    * Produce a delay between 2s and 6s
    * 
    * (slightly faster than the delay implemented in the AccountSocketSender 
    * class, so that the MessageQueue is filled up by the time) 
    */
   public void randomDelay()
   {
      try 
      {
         int sleepTime = 2000 + Math.abs(rand.nextInt() % 4000);
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
	      
      
         if (from.equals(chan1) && recordChan1)
         {
            snapshot.addChannel1Transaction(amount);
         }
         
         if (from.equals(chan2) && recordChan2)
         {
            snapshot.addChannel2Transaction(amount);
         }
	   }
	}

	@Override
	public void onMarker(String from, String to) 
	{
	   if (from.equals(chan1) && recordChan1 && recordChan2)
	   {
	      recordChan1 = false;
	   }
	   else if (from.equals(chan2) && recordChan1 && recordChan2)
	   {
	      recordChan2 = false;
	   }
	   else if (from.equals(chan1) && recordChan1 && !recordChan2)
	   {
	      recordChan1 = false;
	      String out = "";
         out += "state balance: " + ((int) snapshot.getAccountBalance()) + "\u20AC";
         out += ", received from " + chan1 + ": " + ((int) snapshot.getChannel1Transactions()) + "\u20AC";
         out += ", received from " + chan2 + ": " + ((int) snapshot.getChannel2Transactions()) + "\u20AC";
         ichichan.sendSnapshot(name, out);
	   }
	   else if (from.equals(chan2) && !recordChan1 && recordChan2)
	   {
	      recordChan2 = false;
	      String out = "";
	      out += "state balance: " + ((int) snapshot.getAccountBalance()) + "\u20AC";
	      out += ", received from " + chan1 + ": " + ((int) snapshot.getChannel1Transactions()) + "\u20AC";
	      out += ", received from " + chan2 + ": " + ((int) snapshot.getChannel2Transactions()) + "\u20AC";
	      nichan.sendSnapshot(name,  out);
	   }
	   else if (!recordChan1 && !recordChan2)
	   {
	      haltMessageQueueing = true;
	      
	      snapshot.clear();
	      snapshot.setAccountBalance(currentBalance);
	      
	      recordChan1 = true;
	      recordChan2 = true;
	      
	      randomDelay();
	      
	      ichichan.sendMarker(name);
	      nichan.sendMarker(name);
	      
	      haltMessageQueueing = false;
	   }
	}

	@Override
	public void onStartSnapshot() 
	{
	   // simulate a new marker from somewhere else
	   recordChan1 = false;
	   recordChan2 = false;
	   onMarker("userInvokedSnapshot", name);
	}
}
