package tk.ue11;

import java.util.LinkedList;
import java.util.List;

public class Snapshot {
	String accountName;
	double accountBalance;
	
	String channel1Name;
	String channel2Name;
	
	List<Double> channel1Transactions;
	List<Double> channel2Transactions;
	
	double currentSnapshot;
	
	public Snapshot(String accountName, String channel1Name, String channel2Name) {
		this.accountName = accountName;
		this.channel1Name = channel1Name;
		this.channel2Name = channel2Name;
		
		channel1Transactions = new LinkedList<Double>();
		channel2Transactions = new LinkedList<Double>();
		
		currentSnapshot = 0;
	}
	
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public double getAccountBalance()
	{
	   return accountBalance;
	}
	
	public void addChannel1Transaction(double amount){
		channel1Transactions.add(amount);
	}
	
	public void addChannel2Transaction(double amount){
		channel2Transactions.add(amount);
	}
	
	public void clear(){
		accountBalance = 0;
		channel1Transactions.clear();
		channel2Transactions.clear();
	}
	
	public double getChannel1Transactions()
	{
	   double ret = 0;
	   
	   for (double t : channel1Transactions)
	   {
	      ret += t;
	   }
	   
	   return ret;
	}
	
	public double getChannel2Transactions()
	{
	   double ret = 0;
	   
	   for (double t : channel2Transactions)
	   {
	      ret += t;
	   }
	   
	   return ret;
	}
}
