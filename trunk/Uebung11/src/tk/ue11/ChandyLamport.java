package tk.ue11;

import java.io.IOException;
import java.net.SocketException;

import tk.ue11.ObserverSocketReceiver.MessageListener;

public class ChandyLamport implements MessageListener
{
   private final static int OBSERVER_PORT = 4000;
   private final static int ACCOUNT1_PORT = 4001; // please take care, that account ports end with 1,2 and 3
   private final static int ACCOUNT2_PORT = 4002;
   private final static int ACCOUNT3_PORT = 4003;
   
   private BankingGUI gui = null;
   private BankAccount account1 = null, account2 = null, account3 = null;
   
   public ChandyLamport() throws SocketException
   {
      gui = new BankingGUI(this);
      gui.setVisible(true);
      
      // create & run observer
      ObserverSocketReceiver observer = new ObserverSocketReceiver(OBSERVER_PORT, this);
      new Thread(observer).start();
   }
   
   public void startBankAccounts(int acc1balance, int acc2balance, int acc3balance)
   {
      // create banking accounts
      // own port, observer port, channel1 port, channel2 port
      try
      {
         account1 = new BankAccount(ACCOUNT1_PORT,OBSERVER_PORT,ACCOUNT2_PORT,ACCOUNT3_PORT,acc1balance);
         account2 = new BankAccount(ACCOUNT2_PORT,OBSERVER_PORT,ACCOUNT1_PORT,ACCOUNT3_PORT,acc2balance);
         account3 = new BankAccount(ACCOUNT3_PORT,OBSERVER_PORT,ACCOUNT1_PORT,ACCOUNT2_PORT,acc3balance);
      } catch (IOException e)
      {
         System.err.println("Failed to create new Bank account: ");
         e.printStackTrace();
      }
      
      new Thread(account1).start(); 
      new Thread(account2).start();
      new Thread(account3).start();
   }
   
   @Override
   public void onMarker(String from, String to)
   {
      // TODO Auto-generated method stub
   }

   @Override
   public void onTransaction(String from, String to, String amount)
   {
      // rename gui labels correctly
      int fromAcc = Integer.parseInt(from.substring(7));
      int toAcc = Integer.parseInt(to.substring(7));
      
      int tmpAmount = (int) Double.parseDouble(amount);
      
      int fromAccBalance = gui.get_Share(fromAcc) - tmpAmount;
      int toAccBalance = gui.get_Share(toAcc) + tmpAmount;
      
      String debugMsg1 = "New GUI Balance account" + fromAcc +": "+gui.get_Share(fromAcc)+" -> "+fromAccBalance;
      String debugMsg2 = "New GUI Balance account" + toAcc +": "+gui.get_Share(toAcc)+" -> "+toAccBalance;
            
      gui.set_Account(fromAcc, fromAccBalance);
      gui.set_Account(toAcc, toAccBalance);
      
      // send message notification to gui output textfield
      String msg = "Transfer: " + from + " -> " + to + " (" + tmpAmount + " \u20AC)";
      gui.printMsg(msg);
      System.out.println("\n/////gui <-> observer processing block/////\n" + msg);
      System.out.println(debugMsg1);
      System.out.println(debugMsg2 + "\n/////end of block /////\n");
   }
   
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      try
      {
         new ChandyLamport();
      } 
      catch (SocketException e)
      {
         System.err.println("Initialization Error: " + e.getMessage());
      }
   }
}
