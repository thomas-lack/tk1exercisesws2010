package tk.ue11;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class BankingGUI extends JFrame implements ActionListener
{
	private int TotalMoney;
	int Share1,Share2,Share3 = 0;
	private ChandyLamport clController = null;
	
	private static final long serialVersionUID = -776076003482294953L;
	private javax.swing.JButton Account1SnapBtn;
	private javax.swing.JButton Account2SnapBtn;
	private javax.swing.JButton Account3SnapBtn;
	private javax.swing.JTextArea LogTextField;
	private javax.swing.JLabel Account1NameLabel;
	private javax.swing.JLabel Account2NameLabel;
	private javax.swing.JLabel Account3NameLabel;
	private javax.swing.JLabel Account1BalanceLabel;
	private javax.swing.JLabel Account2BalanceLabel;
	private javax.swing.JLabel Account3BalanceLabel;

	private javax.swing.JLabel BlankLabel;
	private javax.swing.JLabel TotalAmountLabel;
	private javax.swing.JTextField TotalAmount;
	private javax.swing.JButton StartSimBtn;
	
	private JScrollPane LogTextScroll;
	
	public BankingGUI(ChandyLamport cl)
	{
		this.clController = cl;
	   this.setTitle("Chandy Lamport Snapshot");
	   this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	   init();
	}
	
	public void init()
	{
	    /**
	     * INIT OF THE TEXTFIELD AND LABELS
	     */

	    LogTextField = new javax.swing.JTextArea();
	    Account1SnapBtn = new javax.swing.JButton();
	    Account2SnapBtn = new javax.swing.JButton();
	    Account3SnapBtn = new javax.swing.JButton();
	    Account1NameLabel = new javax.swing.JLabel();
	    Account2NameLabel = new javax.swing.JLabel();
	    Account3NameLabel = new javax.swing.JLabel();
	    Account1BalanceLabel = new javax.swing.JLabel();
	    Account2BalanceLabel = new javax.swing.JLabel();
	    Account3BalanceLabel = new javax.swing.JLabel();
	    
	    BlankLabel = new javax.swing.JLabel();
	    TotalAmountLabel = new javax.swing.JLabel();
	    StartSimBtn = new javax.swing.JButton();
	    TotalAmount = new javax.swing.JTextField();
	    
	    LogTextField.setLineWrap(true);
	    LogTextField.setWrapStyleWord(true);
	    LogTextField.setEditable(false);
	    LogTextField.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black)); 
	    LogTextScroll = new javax.swing.JScrollPane(LogTextField);
	    
	    Account1NameLabel.setText("Account 1");
	    Account1NameLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account1NameLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    Account2NameLabel.setText("Account 2");
	    Account2NameLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account2NameLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    Account3NameLabel.setText("Account 3");
	    Account3NameLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account3NameLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    Account1BalanceLabel.setText("0 \u20AC");
	    Account1BalanceLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account1BalanceLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    Account2BalanceLabel.setText("0 \u20AC");
	    Account2BalanceLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account2BalanceLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    Account3BalanceLabel.setText("0 \u20AC");
	    Account3BalanceLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account3BalanceLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    BlankLabel.setText("");
	    BlankLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    BlankLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);
	   
	    TotalAmountLabel.setText("Total \u20AC");
	    TotalAmountLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    TotalAmountLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);
	    
	    
	    /**
	     * INIT OF THE "ACCOUNT 1" SNAPPSHOT BUTTON
	     */
	    Account1SnapBtn.setText("Snapshot");
	    Account1SnapBtn.addActionListener(new java.awt.event.ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	        	  Account1SnapBtnActionPerformed(evt);
	          }
	      });

	    /**
	     * INIT OF THE "ACCOUNT 2" SNAPPSHOT BUTTON
	     */
	    Account2SnapBtn.setText("Snapshot");
	    Account2SnapBtn.addActionListener(new java.awt.event.ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	        	  Account2SnapBtnActionPerformed(evt);
	          }
	      });

	    /**
	     * INIT OF THE "ACCOUNT 3" SNAPPSHOT BUTTON
	     */
	    Account3SnapBtn.setText("Snapshot");
	    Account3SnapBtn.addActionListener(new java.awt.event.ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	        	  Account3SnapBtnActionPerformed(evt);
	          }
	      });
	    
	    /**
	     * INIT OF THE "START SIM" BUTTON
	     */
	    StartSimBtn.setText("Start Sim");
	    StartSimBtn.addActionListener(new java.awt.event.ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	        	  StartSimBtnActionPerformed(evt);
	          }
	      });

      	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
        		layout.createSequentialGroup()
                            .addComponent(LogTextScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup()
                            .addComponent(Account1NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Account1BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE) 
                            .addComponent(Account1SnapBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Account2NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Account2BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE) 
                            .addComponent(Account2SnapBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Account3NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Account3BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE) 
                            .addComponent(Account3SnapBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BlankLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TotalAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(StartSimBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)));

        
        layout.setVerticalGroup(
        		layout.createParallelGroup()
        			.addComponent(LogTextScroll, 20, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup()                    		              
                    		.addComponent(Account1NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup()                    		
                    		.addComponent(Account1BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    	.addComponent(Account1SnapBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                    		
                    		.addComponent(Account2NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                    		
                    		.addComponent(Account2BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    	.addComponent(Account2SnapBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))    
                   	.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                    		
                    		.addComponent(Account3NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                    		
                    		.addComponent(Account3BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        	                .addComponent(Account3SnapBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                	        .addComponent(BlankLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        	.addComponent(TotalAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(StartSimBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))));
            pack();

	}
	
	/**
	 *  FUNCTIONALITY FOR THE SNAPSHOT BUTTON OF ACCOUNT 1
	 */
	
	private void Account1SnapBtnActionPerformed(ActionEvent e) 
	{
		
	}

	/**
	 *  FUNCTIONALITY FOR THE SNAPSHOT BUTTON OF ACCOUNT 2
	 */
	
	private void Account2SnapBtnActionPerformed(ActionEvent e) 
	{
		
	}

	/**
	 *  FUNCTIONALITY FOR THE SNAPSHOT BUTTON OF ACCOUNT 3
	 */
	
	private void Account3SnapBtnActionPerformed(ActionEvent e) 
	{
		
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 *  FUNCTIONALITY FOR THE START "SIMULATION" BUTTON 
	 */
	private void StartSimBtnActionPerformed(ActionEvent e) 
	{
		if(TotalAmount.getText().equals(""))
		{
			showErrorDialog("Total Amount","You must enter a total amount of money that will be randomly shared between the accounts");
		}
		else
		{
			clearLog();
			TotalMoney = Integer.parseInt(TotalAmount.getText());
			generateShares(TotalMoney);
			/**
			 *  TRIGGER SIMULATION START HERE
			 */
			clearLog();
			StartSimBtn.setEnabled(false); // allowing to start over again, is not thread safe / sockets are still bound
			clController.startBankAccounts(Share1, Share2, Share3);
		}
	}
	
	/**
	 * FUNCTION FOR ERROR MESSAGES 
	 * @param title
	 * @param msg
	 */
	
	public void showErrorDialog(String title, String msg)
	{
	  JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	
	/**
	 * FUNCTION THAT ADDS A MESSAGE TO THE LOG WINDOW
	 * @param Msg
	 */
	
	public void printMsg(String Msg)
	{
		LogTextField.append(Msg+"\n");
	}
	
	/**
	 * FUNCTION TO CLEAR THE CONTENT OF THE LOG
	 */
	void clearLog()
	{
		LogTextField.setText("");
	}
	
	/**
	 * FUNCTION THAT RETURNS THE TOTAL MONEY OF THE SYSTEM
	 * @return
	 */
	public int getTotal()
	{
		return TotalMoney;
	}
	
	/**
	 * FUNCTION THAT SETS THE BALANCE OF ACCOUNT 'i' TO THE AMOUNT 'Amount'
	 * @param i
	 * @param Amount
	 */
	public void set_Account(int i, int amount)
	{
		String amountStr = String.valueOf(amount);
		setShare(i, amount);
		
	   switch(i)
		{
			case 1:
			    Account1BalanceLabel.setText(amountStr+" \u20AC");
				break;
			case 2:
			    Account2BalanceLabel.setText(amountStr+" \u20AC");
				break;
			case 3:
			    Account3BalanceLabel.setText(amountStr+" \u20AC");
				break;
				
			default:
				System.out.println("SYSTEM : DEBUG : Invalid Account number. Must be 1,2 oder 3.");				
		}
	}
	/**
	 * FUNCTION SPLITS THE TOTAL AMOUNT UP INTO "REASONABLE" SHARES
	 * @param Total
	 */
	void generateShares(int Total)
	{
		int reserve = (Total/10); 			// the reserve is 10% of the total money per share holder
		int available = Total-(reserve*2); 	// reserve 10% for 2 account ( to ensure every account receives at least 10% of the pot)
		Share1 = (int)((Math.random()*100000)%available);	//Share 1 can be anything up to 80% of the total money
		if(Share1 == available)				// if Share1 should be 80% (which is unlikely) the other share holders											
		{									// receive the reserved amount
			Share2 = reserve;
			Share3 = reserve;
		}
		else
		{
			available = available-Share1;						// available is the money not dedicated to share holder Share1
			Share2 = (int)((Math.random()*100000)%available);	//Share 2 can be anything up to the rest amount of the money
			available = available-Share2;						
			Share3 = available;
			// Now to split up the reserve between the two lowest Accounts
			if(Share1 >= Share2)
			{
				if(Share1 >= Share3)
				{
					// Share1 is the greatest => Share2 and Share3 get the reserve
					Share2= Share2+reserve;
					Share3= Share3+reserve;
				}
				else
				{
					// Share3 is the greatest => Share1 and Share2 get the reserve
					Share1= Share1+reserve;
					Share2= Share2+reserve;
				}
			}
			else
			{
				if(Share2 >= Share3)
				{
					// Share2 is the greatest => Share1 and Share3 get the reserve
					Share1= Share1+reserve;
					Share3= Share3+reserve;
				}
				else
				{
					// Share3 is the greatest => Share1 and Share2 get the reserve
					Share1= Share1+reserve;
					Share2= Share2+reserve;
				}
			}			
		}
		
		/**
		 * TEST OUTPUT
		 */
		set_Account(1,Share1);
		set_Account(2,Share2);
		set_Account(3,Share3);
		
	}
	
	/**
	 * FUNCTION THAT RETURNS ONE OF THE THREE SHARES
	 * @param i
	 * @return
	 */
	public int get_Share(int i)
	{
		switch(i)
		{
			case 1:
			    return Share1;
			case 2:
			    return Share2;
			case 3:
			    return Share3;
				
			default:
				System.out.println("SYSTEM : DEBUG : Invalid Account number. Must be 1,2 oder 3.");
				return 0;
		}
	}
	
	public void setShare(int i, int amount)
	{
	   switch(i)
	   {
	   case 1:
	      Share1 = amount;
	      break;
	   case 2:
	      Share2 = amount;
	      break;
	   case 3:
	      Share3 = amount;
	      break;
	   default:
	      // do nothing
	   }
	}
}
