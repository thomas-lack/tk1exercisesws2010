package tk.ue11;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class BankingGUI extends JFrame implements ActionListener
{
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
	
	public BankingGUI()
	{
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

	    Account1BalanceLabel.setText("€");
	    Account1BalanceLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account1BalanceLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    Account2BalanceLabel.setText("€");
	    Account2BalanceLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account2BalanceLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    Account3BalanceLabel.setText("€");
	    Account3BalanceLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    Account3BalanceLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);

	    BlankLabel.setText("");
	    BlankLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    BlankLabel.setVerticalAlignment(javax.swing.JLabel.CENTER);
	   
	    TotalAmountLabel.setText("Total €");
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
		
	}
	
}
